package com.ovoenergy.bootcamp.kafka.common

import com.ovoenergy.comms.dockertestkit.{KafkaKit, ZookeeperKit}
import com.whisk.docker.impl.dockerjava.DockerKitDockerJava
import com.whisk.docker.scalatest.DockerTestKit
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, KafkaAdminClient, NewTopic}
import org.apache.kafka.clients.consumer.{Consumer, ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.scalatest.{Matchers, WordSpec}

import scala.collection.JavaConverters._
import scala.concurrent.{Future, Promise}
import scala.concurrent.duration._

class KafkaConsumerSpec extends WordSpec
  with Matchers
  with DockerTestKit
  with DockerKitDockerJava
  with ZookeeperKit
  with KafkaKit {

  type Key = String
  type Value = String

  val topic = "test-topic-1"

  var producer: Producer[Key,Value] = _
  var consumer: Consumer[Key, Value] = _
  var adminClient: AdminClient = _


  override def beforeAll(): Unit = {
    super.beforeAll()

    producer = new KafkaProducer[Key, Value](
      Map[String, AnyRef](
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaEndpoint,
        ProducerConfig.ACKS_CONFIG->"all",
        ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION->"1",
        ProducerConfig.CLIENT_ID_CONFIG -> "KafkaConsumerSpec"
      ).asJava,
      new StringSerializer,
      new StringSerializer
    )

    consumer = new KafkaConsumer[Key, Value](
      Map[String, AnyRef](
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaEndpoint,
        ConsumerConfig.GROUP_ID_CONFIG -> "my-consumer-group-id",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> "true",
        ConsumerConfig.CLIENT_ID_CONFIG -> "KafkaConsumerSpec"
      ).asJava,
      new StringDeserializer,
      new StringDeserializer
    )

    adminClient = AdminClient.create(
      Map[String, AnyRef](
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG -> kafkaEndpoint,
        AdminClientConfig.CLIENT_ID_CONFIG -> "KafkaConsumerSpec"
      ).asJava
    )
  }

  override def afterAll(): Unit = {
    producer.close()
    consumer.close()
    adminClient.close()
    super.afterAll()
  }

  "consumer" should {
    "consume the produced messages" in {

      adminClient.createTopics(List(new NewTopic(topic, 6, 1)).asJava).all().get()

      Future.sequence((0 to 9).map(i =>
        produceRecord(producer, new ProducerRecord[Key, Value](topic, s"$i", s"test-$i"))
      )).futureValue(timeout(5.seconds))

      consumer.subscribe(Set(topic).asJava)

      val noOfRecordsConsumed = Iterator
        .continually(consumer.poll(150))
        .map { records =>
          records.asScala.foreach(processRecord)
          consumer.commitSync()
          records.count()
        }
        .take(10)
        .sum

      noOfRecordsConsumed shouldBe 10

    }
  }


  def processRecord[K, V](record: ConsumerRecord[K, V]): Unit = {
    note(s"Processed record: ${record.topic()}:${record.partition()}:${record.offset()} - ${record.value}")
  }

  def produceRecord[K,V](producer: Producer[K, V], record: ProducerRecord[K, V]): Future[RecordMetadata] = {
    val p = Promise[RecordMetadata]
    producer.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        Option(exception) match {
          case Some(e) => p.failure(e)
          case None => p.success(metadata)
        }
      }
    })
    p.future
  }

}

package com.ovoenergy.bootcamp.kafka.service.acquisition

import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.duration._

trait MaterializerFixture extends BeforeAndAfterAll { _: Suite with ActorSystemFixture =>

  private val materializerReference: AtomicReference[ActorMaterializer] = new AtomicReference()

  implicit def materializer: Materializer = Option(materializerReference.get()) match {
    case Some(as) => as
    case None => throw new IllegalStateException("Materializer not yet instantiated")
  }

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    materializerReference.set(ActorMaterializer())
  }

  override protected def afterAll(): Unit = {
    materializerReference.get().shutdown()
    super.afterAll()
  }
}

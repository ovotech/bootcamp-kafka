package com.ovoenergy.bootcamp.kafka.service.acquisition

import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Suite}
import scala.concurrent.duration._

trait ActorSystemFixture extends BeforeAndAfterAll { _: Suite with ScalaFutures =>

  private val actorSystemReference: AtomicReference[ActorSystem] = new AtomicReference()

  implicit def actorSystem: ActorSystem = Option(actorSystemReference.get()) match {
    case Some(as) => as
    case None => throw new IllegalStateException("ActorSystem not yet instantiated")
  }

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    actorSystemReference.set(ActorSystem(getClass.getName.replace(".", "_")))
  }

  override protected def afterAll(): Unit = {
    actorSystemReference.get().terminate().futureValue(timeout(scaled(5.seconds)))
    super.afterAll()
  }
}

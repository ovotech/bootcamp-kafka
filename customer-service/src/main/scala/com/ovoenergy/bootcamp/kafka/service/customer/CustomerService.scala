package com.ovoenergy.bootcamp.kafka.service.customer

import java.time.LocalDateTime

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.common.serde._
import com.ovoenergy.bootcamp.kafka.domain.{CreateCustomer, Customer, CustomerId}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.syntax._
import io.circe.{Encoder, Json}

import scala.util.hashing.MurmurHash3

object CustomerService extends HttpApp with ErrorAccumulatingCirceSupport {

  val customerIdMatcher: PathMatcher1[CustomerId] = Segment.map(CustomerId.apply)

  implicit val buildInfoEncoder: Encoder[BuildInfo.type] = Encoder.instance { bi =>
    Json.obj(
      "name" -> BuildInfo.name.asJson,
      "version" -> BuildInfo.version.asJson
    )
  }

  override def routes: Route =
    path("admin" / "ping") {
      complete("pong")
    } ~
    path("admin" / "info") {
      complete(BuildInfo)
    } ~
    pathPrefix("api" / "v1") {
      pathPrefix("customer") {
        (pathEndOrSingleSlash & post & entity(as[CreateCustomer])) { createCustomer =>
          val customerId = CustomerId.unique()
          complete(StatusCodes.Created -> Customer(customerId, createCustomer.name, createCustomer.emailAddress, LocalDateTime.now()))
        } ~
        path(customerIdMatcher) { customerId =>
          get {
            complete("")
          }
        }
      }
    }

  def main(args: Array[String]): Unit = {
    startServer("0.0.0.0", 8080)
  }
}

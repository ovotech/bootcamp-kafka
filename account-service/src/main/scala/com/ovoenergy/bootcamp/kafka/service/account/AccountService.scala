package com.ovoenergy.bootcamp.kafka.service.account

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.domain.{Account, Customer, CustomerId}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.{Encoder, Json}
import io.circe.syntax._
import com.ovoenergy.bootcamp.kafka.common.serde._

object AccountService extends HttpApp with ErrorAccumulatingCirceSupport {

  val customerIdMatcher: PathMatcher1[CustomerId] = Segment.map(CustomerId.apply)

  implicit val buildInfoEncoder: Encoder[BuildInfo.type] = Encoder.instance { bi =>
    Json.obj(
      "name" -> BuildInfo.name.asJson,
      "version" -> BuildInfo.version.asJson
    )
  }

  override protected def routes: Route =
    path("admin" / "ping") {
      complete("pong")
    } ~
    path("admin" / "info") {
      complete(BuildInfo)
    } ~
    pathPrefix("api" / "v1") {
      pathPrefix("account") {
        (pathEndOrSingleSlash & post) {
          complete(StatusCodes.Created -> Account())
        } ~
        path(customerIdMatcher) { customerId =>
          get {
            complete("")
          }
        }
      }
    }
}

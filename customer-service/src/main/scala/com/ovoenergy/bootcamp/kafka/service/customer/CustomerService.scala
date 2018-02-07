package com.ovoenergy.bootcamp.kafka.service.customer

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.common.serde._
import com.ovoenergy.bootcamp.kafka.domain.Customer.CustomerId
import com.ovoenergy.bootcamp.kafka.domain.{CreateCustomer, Customer}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.syntax._
import io.circe.{Encoder, Json}

object CustomerService extends Directives with ErrorAccumulatingCirceSupport {

  type CustomerRepository = ConcurrentHashMap[CustomerId, Customer]

  val customerIdMatcher: PathMatcher1[CustomerId] =
    Segment.map(CustomerId.apply)

  implicit val buildInfoEncoder: Encoder[BuildInfo.type] = Encoder.instance {
    bi =>
      Json.obj(
        "name" -> BuildInfo.name.asJson,
        "version" -> BuildInfo.version.asJson
      )
  }

  def routes(customerRepository: CustomerRepository): Route =
    path("admin" / "ping") {
      complete("pong")
    } ~
      path("admin" / "info") {
        complete(BuildInfo)
      } ~
      pathPrefix("api" / "v1") {
        pathPrefix("customer") {
          (pathEndOrSingleSlash & post & entity(as[CreateCustomer])) {
            createCustomer =>
              val customer = Customer(CustomerId.unique(),
                                      createCustomer.acquisitionId,
                                      createCustomer.name,
                                      createCustomer.emailAddress,
                                      LocalDateTime.now())
              customerRepository.put(customer.id, customer)
              complete(StatusCodes.Created -> customer)
          } ~
            path(customerIdMatcher) { customerId =>
              get {
                Option(customerRepository.get(customerId))
                  .fold(complete(StatusCodes.NotFound))(complete(_))
              }
            }
        }
      }

}

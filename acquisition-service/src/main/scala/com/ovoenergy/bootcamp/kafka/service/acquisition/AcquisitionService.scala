package com.ovoenergy.bootcamp.kafka.service.acquisition

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, StatusCodes, Uri}
import akka.http.scaladsl.server.{HttpApp, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.domain._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.{Encoder, Json}
import io.circe.syntax._
import com.ovoenergy.bootcamp.kafka.common.serde._
import akka.http.scaladsl.client.RequestBuilding._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId
import com.ovoenergy.bootcamp.kafka.domain.PostalAddress.{AddressLine, City, PostCode}

import scala.concurrent.Future

case class Settings(customerServiceEndpoint: Uri, accountServiceEndpoint: Uri)

object AcquisitionService extends HttpApp with ErrorAccumulatingCirceSupport {

  val settings: Settings = ???

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
        pathPrefix("acquisition") {
          (pathEndOrSingleSlash & post) {
            // TODO parse request


            val domicileAddress = PostalAddress(AddressLine(""), None, PostCode("W129FT"), City("London"))
            val billingAddress = domicileAddress

            complete(for {
              customer <- createCustomer(CreateCustomer("", EmailAddress.unsafeFromString("nonexisting@gmail.com")))
              account <- createAccount(customer.id, domicileAddress, billingAddress)
            } yield Acquisition(customer, account))
          }
        }
      }


  def createCustomer(createCustomer: CreateCustomer): Future[Customer] = {
    for {
      response <- Http().singleRequest(Post(settings.customerServiceEndpoint.withPath(Uri.Path.Empty / "api" / "v1" / "customer"), createCustomer))
      customer <- Unmarshal(response).to[Customer]
    } yield customer
  }

  def createAccount(customerId: CustomerId, domicileAddress: PostalAddress, billingaddress: PostalAddress): Future[Account] = {
    // TODO make the call
    Future.successful(Account(AccountId("test"), customerId, domicileAddress, billingaddress))
  }
}
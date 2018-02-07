package com.ovoenergy.bootcamp.kafka.service.acquisition

import java.util.concurrent.ConcurrentHashMap

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, PathMatcher1, Route}
import buildinfo.BuildInfo
import com.ovoenergy.bootcamp.kafka.common.serde._
import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId
import com.ovoenergy.bootcamp.kafka.domain._
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.syntax._
import io.circe.{Encoder, Json}

import scala.concurrent.Future



object AcquisitionService extends Directives with ErrorAccumulatingCirceSupport {

  type AcquisitionRepository = ConcurrentHashMap[AcquisitionId, Acquisition]

  val acquisitionIdMatcher: PathMatcher1[AcquisitionId] =
    Segment.map(AcquisitionId.apply)


  implicit val buildInfoEncoder: Encoder[BuildInfo.type] = Encoder.instance {
    bi =>
      Json.obj(
        "name" -> bi.name.asJson,
        "version" -> bi.version.asJson
      )
  }

  def routes(acquisitionRepository: AcquisitionRepository,
             createCustomer: CreateCustomer => Future[Customer],
             createAccount: CreateAccount => Future[Account]): Route = {
    extractMaterializer { implicit m =>
      extractActorSystem { implicit s =>
        extractExecutionContext { implicit e =>

          path("admin" / "ping") {
            complete("pong")
          } ~
            path("admin" / "info") {
              complete(BuildInfo)
            } ~
            pathPrefix("api" / "v1") {
              pathPrefix("acquisition") {
                (pathEndOrSingleSlash & post) {
                  entity(as[CreateAcquisition]) {
                    createAcquisition: CreateAcquisition =>

                      val acquisitionId = AcquisitionId.unique()
                      val acquisitionFuture = for {
                        customer <- createCustomer(
                          CreateCustomer(
                            acquisitionId,
                            createAcquisition.customerName,
                            createAcquisition.customerEmailAddress))
                        account <- createAccount(
                          CreateAccount(acquisitionId,
                            createAcquisition.tariff,
                            createAcquisition.domicileAddress,
                            createAcquisition.billingAddress))

                      } yield Acquisition(acquisitionId, customer, account)

                      val response = acquisitionFuture.map { acquisition =>
                        acquisitionRepository.put(acquisitionId, acquisition)
                        StatusCodes.Created -> acquisition
                      }

                      complete(response)
                  }
                } ~
                  (path(acquisitionIdMatcher) & get) { acquisitionId =>
                    Option(acquisitionRepository.get(acquisitionId)).fold(complete(StatusCodes.NotFound))(complete(_))
                  }
              }
            }
        }
      }
    }
  }

}

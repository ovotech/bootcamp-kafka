package com.ovoenergy.bootcamp.kafka.service.acquisition

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.ovoenergy.bootcamp.kafka.domain.{CreateCustomer, Customer}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import com.ovoenergy.bootcamp.kafka.common.serde._

import scala.concurrent.{ExecutionContext, Future}

class CustomerServiceClient(customerServiceEndpoint: Uri) extends ErrorAccumulatingCirceSupport {

  def createCustomer(createCustomer: CreateCustomer)(
    implicit mat: Materializer,
    as: ActorSystem,
    ec: ExecutionContext): Future[Customer] = {
    for {
      response <- Http().singleRequest(
        Post(customerServiceEndpoint.withPath(
          Uri.Path.Empty / "api" / "v1" / "customer"),
          createCustomer))
      customer <- Unmarshal(response).to[Customer]
    } yield customer
  }

}

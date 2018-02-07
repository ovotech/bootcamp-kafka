package com.ovoenergy.bootcamp.kafka.service.acquisition

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.ovoenergy.bootcamp.kafka.domain.{Account, CreateAccount}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import com.ovoenergy.bootcamp.kafka.common.serde._

import scala.concurrent.{ExecutionContext, Future}

class AccountServiceClient(accountServiceEndpoint: Uri) extends ErrorAccumulatingCirceSupport {

  def createAccount(createAccount: CreateAccount)(
    implicit mat: Materializer,
    as: ActorSystem,
    ec: ExecutionContext): Future[Account] = {
    for {
      response <- Http().singleRequest(
        Post(accountServiceEndpoint.withPath(
          Uri.Path.Empty / "api" / "v1" / "account"),
          createAccount))
      account <- Unmarshal(response).to[Account]
    } yield account
  }

}

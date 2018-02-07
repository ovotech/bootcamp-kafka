package com.ovoenergy.bootcamp.kafka.service.acquisition

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.stream.{ActorMaterializer, Materializer}
import ciris.syntax._
import ciris.{env, loadConfig, prop}
import com.ovoenergy.bootcamp.kafka.service.acquisition.AcquisitionService.AcquisitionRepository
import com.ovoenergy.bootcamp.kafka.common.config._

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Main extends App {

  val settings: Settings = loadConfig(
    env[Option[String]]("HTTP_HOST").orElse(prop[Option[String]]("http.host")),
    env[Option[Int]]("HTTP_PORT").orElse(prop[Option[Int]]("http.port")),
    env[Uri]("CUSTOMER_SERVICE_ENDPOINT"),
    env[Uri]("ACCOUNT_SERVICE_ENDPOINT")
  )(
    (host, port, customerServiceEndpoint, accountServiceEndpoint) =>
      Settings(host.getOrElse("0.0.0.0"),
        port.getOrElse(8081),
        customerServiceEndpoint,
        accountServiceEndpoint)).orThrow()

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val routes: Route = AcquisitionService.routes(
    new AcquisitionRepository,
    new CustomerServiceClient(settings.customerServiceEndpoint).createCustomer,
    new AccountServiceClient(settings.accountServiceEndpoint).createAccount
  )

  val bindingFuture = Http().bindAndHandle(routes, settings.httpHost, settings.httpPort)

  // TODO handle sigkill instead
  bindingFuture.map { binding =>
    StdIn.readLine()
  }

}

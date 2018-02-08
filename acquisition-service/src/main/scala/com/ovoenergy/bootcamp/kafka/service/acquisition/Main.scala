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
import org.slf4j.LoggerFactory

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration
import scala.io.StdIn
import scala.util.{Failure, Success}

object Main extends App {

  val log = LoggerFactory.getLogger(getClass)

  val settings: Settings = loadConfig(
    env[Option[String]]("HTTP_HOST").orElse(prop[Option[String]]("http.host")),
    env[Option[Int]]("HTTP_PORT").orElse(prop[Option[Int]]("http.port")),
    env[Uri]("CUSTOMER_SERVICE_ENDPOINT").orElse(prop[Uri]("customer-service.endpoint")),
    env[Uri]("ACCOUNT_SERVICE_ENDPOINT").orElse(prop[Uri]("account-service.endpoint"))
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

  bindingFuture.onComplete {
    case Success(binding) =>
      log.info(s"Server online at http://${binding.localAddress.getHostName}:${binding.localAddress.getPort}/")
      sys.addShutdownHook(Await.result(system.terminate(), Duration.Inf))

    case Failure(e) =>
      log.error("Error binding the server", e)
      sys.exit(1)
  }
}

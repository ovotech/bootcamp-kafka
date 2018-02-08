package com.ovoenergy.bootcamp.kafka.service.customer

import akka.http.scaladsl.server.{HttpApp, Route}
import ciris.syntax._
import ciris.{env, loadConfig, prop}
import com.ovoenergy.bootcamp.kafka.service.customer.CustomerService.CustomerRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends HttpApp with App {

  override protected def routes: Route = CustomerService.routes(new CustomerRepository)

  val settings: Settings = loadConfig(
    env[Option[String]]("HTTP_HOST").orElse(prop[Option[String]]("http.host")),
    env[Option[Int]]("HTTP_PORT").orElse(prop[Option[Int]]("http.port"))
  )((host, port) => Settings(host.getOrElse("0.0.0.0"), port.getOrElse(8080))).orThrow()

  startServer(settings.httpHost, settings.httpPort)
}

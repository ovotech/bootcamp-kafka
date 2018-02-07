package com.ovoenergy.bootcamp.kafka.service.account


import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.ovoenergy.bootcamp.kafka.service.account.AccountService.AccountRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Json
import org.scalatest.{Matchers, WordSpec}

class AccountServiceSpec extends WordSpec with Matchers with RequestBuilding with ScalatestRouteTest with FailFastCirceSupport {

  "AccountService" when {
    "receive a Get request to /admin/ping" should {
      "return 200 with Pong body" in {
        Get("/admin/ping") ~> AccountService.routes(new AccountRepository) ~> check {
          status shouldBe StatusCodes.OK
          responseAs[Json] shouldBe Json.fromString("pong")
        }
      }
    }
  }

}


package com.ovoenergy.bootcamp.kafka.service.acquisition

import scala.concurrent.Future

class BootingSpec extends BaseIntegrationSpec {

  "All the containers" should {
    "boot up" in {
      Future.sequence(dockerContainers.map(_.isReady())).futureValue.forall(identity) shouldBe true
    }

    "reply to ping" in {

      pingService(accountServicePublicEndpoint)
      pingService(customerServicePublicEndpoint)
      pingService(acquisitionServicePublicEndpoint)
    }
  }

}

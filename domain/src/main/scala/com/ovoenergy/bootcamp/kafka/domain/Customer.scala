package com.ovoenergy.bootcamp.kafka.domain

import java.net.InetAddress
import java.time.{Instant, LocalDateTime}

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId
import com.ovoenergy.bootcamp.kafka.domain.Customer.CustomerId

import scala.util.hashing.MurmurHash3

case class Customer(id: CustomerId, acquisitionId: AcquisitionId, name: String, emailAddress: EmailAddress, joinedOn: LocalDateTime)

object Customer {

  case class CustomerId(value: String) extends AnyVal

  object CustomerId {

    def unique(): CustomerId = {
      val hash = math.abs(MurmurHash3.productHash(("CustomerId", InetAddress.getLocalHost, Instant.now())))
      CustomerId(s"cus-$hash")
    }

  }


}
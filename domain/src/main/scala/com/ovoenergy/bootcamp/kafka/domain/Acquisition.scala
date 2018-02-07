package com.ovoenergy.bootcamp.kafka.domain

import java.net.InetAddress
import java.time.Instant

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId

import scala.util.hashing.MurmurHash3

case class Acquisition(id: AcquisitionId, customer: Customer, account: Account)

object Acquisition {

  case class AcquisitionId(value: String) extends AnyVal

  object AcquisitionId {

    def unique(): AcquisitionId = {
      val hash = math.abs(MurmurHash3.productHash(("AcquisitionId", InetAddress.getLocalHost, Instant.now())))
      AcquisitionId(s"acq-$hash")
    }
  }


}
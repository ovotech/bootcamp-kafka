package com.ovoenergy.bootcamp.kafka.domain

import java.net.InetAddress
import java.time.Instant

import scala.util.hashing.MurmurHash3

case class CustomerId(value: String) extends AnyVal

object CustomerId {

  def unique(): CustomerId = {
    val hash = math.abs(MurmurHash3.productHash(("CustomerId", InetAddress.getLocalHost, Instant.now())))
    CustomerId(s"cus-$hash")
  }

}

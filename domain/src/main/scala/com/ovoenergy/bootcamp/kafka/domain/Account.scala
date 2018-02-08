package com.ovoenergy.bootcamp.kafka.domain

import java.net.InetAddress
import java.time.Instant

import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId
import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId

import scala.util.hashing.MurmurHash3

case class Account(id: AccountId,
                   acquisitionId: AcquisitionId,
                   tariff: Tariff,
                   domicileAddress: PostalAddress,
                   billingAddress: PostalAddress)

object Account {

  case class AccountId(value: String)

  object AccountId {

    def unique(): AccountId = {
      val hash = math.abs(MurmurHash3.productHash(("AccountId", InetAddress.getLocalHost, Instant.now())))
      AccountId(s"acc-$hash")
    }
  }

}
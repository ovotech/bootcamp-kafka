package com.ovoenergy.bootcamp.kafka.domain

import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId
import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId

case class CreateAccount(acquisitionId: AcquisitionId,
                         tariff: Tariff,
                         domicileAddress: PostalAddress,
                         billingAddress: PostalAddress) {

  def toAccount(id: AccountId): Account =
    Account(id, acquisitionId, tariff, domicileAddress, billingAddress)

}

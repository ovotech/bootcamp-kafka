package com.ovoenergy.bootcamp.kafka.domain

import java.time.LocalDateTime

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId
import com.ovoenergy.bootcamp.kafka.domain.Customer.CustomerId

case class CreateCustomer(acquisitionId: AcquisitionId,
                          name: String,
                          emailAddress: EmailAddress) {

  def toCustomer(id: CustomerId, joinedOn: LocalDateTime): Customer =
    Customer(id, acquisitionId, name, emailAddress, joinedOn)
}

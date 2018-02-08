package com.ovoenergy.bootcamp.kafka.domain

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId

case class CreateAcquisition(customerName: String,
                             customerEmailAddress: EmailAddress,
                             tariff: Tariff,
                             domicileAddress: PostalAddress,
                             billingAddress: PostalAddress) {

  def toAcquisition(id: AcquisitionId): Acquisition =
    Acquisition(id,
                customerName,
                customerEmailAddress,
                tariff,
                domicileAddress,
                billingAddress)
}

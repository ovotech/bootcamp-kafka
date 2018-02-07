package com.ovoenergy.bootcamp.kafka.domain

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId

case class CreateCustomer(acquisitionId: AcquisitionId, name: String, emailAddress: EmailAddress)

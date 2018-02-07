package com.ovoenergy.bootcamp.kafka.domain

case class CreateAcquisition(customerName: String,
                             customerEmailAddress: EmailAddress,
                             tariff: Tariff,
                             domicileAddress: PostalAddress,
                             billingAddress: PostalAddress)

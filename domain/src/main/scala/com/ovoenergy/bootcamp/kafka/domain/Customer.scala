package com.ovoenergy.bootcamp.kafka.domain

import java.time.LocalDateTime

case class Customer(id: CustomerId, name: String, emailAddress: EmailAddress, joinedOn: LocalDateTime)

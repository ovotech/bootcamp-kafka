package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.CreateCustomer
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

trait CreateCustomerInstances { _: AcquisitionIdInstances with EmailAddressInstances =>

  implicit lazy val createCustomerCirceEncoder: Encoder[CreateCustomer] = deriveEncoder[CreateCustomer]

  implicit lazy val createCustomerCirceDecoder: Decoder[CreateCustomer] = deriveDecoder[CreateCustomer]
}

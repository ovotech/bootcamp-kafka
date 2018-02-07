package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.Customer
import io.circe._
import io.circe.generic.semiauto._

trait CustomerInstances { _: AcquisitionIdInstances with CustomerIdInstances with EmailAddressInstances with JavaTimeInstances =>

  implicit lazy val customerCirceEncoder: Encoder[Customer] = deriveEncoder[Customer]

  implicit lazy val customerCirceDecoder: Decoder[Customer] = deriveDecoder[Customer]
}

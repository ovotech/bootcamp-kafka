package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.Customer.CustomerId
import io.circe._

trait CustomerIdInstances {

  implicit lazy val customerIdCirceEncoder: Encoder[CustomerId] = Encoder.encodeString.contramap(_.value)

  implicit lazy val customerIdCirceDecoder: Decoder[CustomerId] = Decoder.decodeString.map(CustomerId.apply)
}

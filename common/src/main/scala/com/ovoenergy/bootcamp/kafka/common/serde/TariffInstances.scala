package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.Tariff
import io.circe._

trait TariffInstances {

  implicit lazy val tariffCirceEncoder: Encoder[Tariff] = Encoder.encodeString.contramap(_.value)

  implicit lazy val tariffCirceDecoder: Decoder[Tariff] = Decoder.decodeString.map(Tariff.apply)
}

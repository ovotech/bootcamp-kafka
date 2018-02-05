package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.{Account, Acquisition}
import io.circe._
import io.circe.generic.semiauto._

trait AcquisitionInstances {_: CustomerInstances with AccountInstances =>

  implicit lazy val acquisitionCirceEncoder: Encoder[Acquisition] = deriveEncoder[Acquisition]

  implicit lazy val acquisitionCirceDecoder: Decoder[Acquisition] = deriveDecoder[Acquisition]
}

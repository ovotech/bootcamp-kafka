package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.Acquisition.AcquisitionId
import io.circe._

trait AcquisitionIdInstances {

  implicit lazy val acquisitionIdCirceEncoder: Encoder[AcquisitionId] = Encoder.encodeString.contramap(_.value)

  implicit lazy val acquisitionIdCirceDecoder: Decoder[AcquisitionId] = Decoder.decodeString.map(AcquisitionId.apply)
}

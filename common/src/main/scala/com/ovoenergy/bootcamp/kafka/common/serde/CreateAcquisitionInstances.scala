package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.{CreateAccount, CreateAcquisition}
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

trait CreateAcquisitionInstances { _ :  EmailAddressInstances with PostalAddressInstances with TariffInstances =>

  implicit lazy val createAcquisitionCirceEncoder: Encoder[CreateAcquisition] = deriveEncoder[CreateAcquisition]

  implicit lazy val createAcquisitionCirceDecoder: Decoder[CreateAcquisition] = deriveDecoder[CreateAcquisition]
}

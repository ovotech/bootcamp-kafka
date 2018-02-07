package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.CreateAccount
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

trait CreateAccountInstances { _ : AcquisitionIdInstances with CustomerIdInstances with PostalAddressInstances =>

  implicit lazy val createAccountCirceEncoder: Encoder[CreateAccount] = deriveEncoder[CreateAccount]

  implicit lazy val createAccountCirceDecoder: Decoder[CreateAccount] = deriveDecoder[CreateAccount]
}

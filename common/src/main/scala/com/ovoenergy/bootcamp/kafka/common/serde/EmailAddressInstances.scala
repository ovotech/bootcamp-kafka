package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.EmailAddress
import io.circe._

trait EmailAddressInstances {

  implicit lazy val emailAddressCirceEncoder: Encoder[EmailAddress] = Encoder.encodeString.contramap(_.value)

  implicit lazy val emailAddressCirceDecoder: Decoder[EmailAddress] = Decoder.decodeString.emap(EmailAddress.fromString)
}

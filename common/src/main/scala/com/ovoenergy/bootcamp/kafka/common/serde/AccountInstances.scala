package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.{Account, Customer}
import io.circe._
import io.circe.generic.semiauto._

trait AccountInstances {

  implicit lazy val accountCirceEncoder: Encoder[Account] = deriveEncoder[Account]

  implicit lazy val accountCirceDecoder: Decoder[Account] = deriveDecoder[Account]
}

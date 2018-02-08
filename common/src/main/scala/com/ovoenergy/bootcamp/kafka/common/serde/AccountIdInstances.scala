package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.Account.AccountId
import io.circe._

trait AccountIdInstances {

  implicit lazy val accountIdCirceEncoder: Encoder[AccountId] = Encoder.encodeString.contramap(_.value)

  implicit lazy val accountIdCirceDecoder: Decoder[AccountId] = Decoder.decodeString.map(AccountId.apply)
}

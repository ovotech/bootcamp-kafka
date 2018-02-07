package com.ovoenergy.bootcamp.kafka.common.serde

import com.ovoenergy.bootcamp.kafka.domain.PostalAddress
import com.ovoenergy.bootcamp.kafka.domain.PostalAddress.{AddressLine, City, PostCode}
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._


trait PostalAddressInstances {

  implicit lazy val addressLineCirceEncoder: Encoder[AddressLine] = Encoder.encodeString.contramap(_.value)

  implicit lazy val addressLineCirceDecoder: Decoder[AddressLine] = Decoder.decodeString.map(AddressLine.apply)

  implicit lazy val cityCirceEncoder: Encoder[City] = Encoder.encodeString.contramap(_.value)

  implicit lazy val cityCirceDecoder: Decoder[City] = Decoder.decodeString.map(City.apply)

  implicit lazy val postCodeEncoder: Encoder[PostCode] = Encoder.encodeString.contramap(_.value)

  implicit lazy val postCodeDecoder: Decoder[PostCode] = Decoder.decodeString.map(PostCode.apply)

  implicit lazy val postalAddressCirceEncoder: Encoder[PostalAddress] = deriveEncoder[PostalAddress]

  implicit lazy val postalAddressCirceDecoder: Decoder[PostalAddress] = deriveDecoder[PostalAddress]
}

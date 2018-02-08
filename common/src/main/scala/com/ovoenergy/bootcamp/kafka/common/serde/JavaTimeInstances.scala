package com.ovoenergy.bootcamp.kafka.common.serde

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneOffset, ZonedDateTime}

import io.circe.{Decoder, Encoder}

import scala.util.Try

trait JavaTimeInstances {

  implicit lazy val zonedDateTimeCirceEncoder: Encoder[ZonedDateTime] =
    Encoder.encodeString
      .contramap(_.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))

  implicit lazy val zonedDateTimeCirceDecoder: Decoder[ZonedDateTime] =
    Decoder.decodeString.emapTry { formatted =>
      Try(ZonedDateTime.parse(formatted, DateTimeFormatter.ISO_ZONED_DATE_TIME))
    }

  implicit lazy val localDateTimeCirceEncoder: Encoder[LocalDateTime] =
    zonedDateTimeCirceEncoder.contramap(_.atZone(ZoneOffset.UTC))

  implicit lazy val localDateTimeCirceDecoder: Decoder[LocalDateTime] =
    zonedDateTimeCirceDecoder.map(_.toLocalDateTime)

  implicit lazy val instantCirceEncoder: Encoder[Instant] =
    zonedDateTimeCirceEncoder.contramap(_.atZone(ZoneOffset.UTC))

  implicit lazy val instantCirceDecoder: Decoder[Instant] =
    zonedDateTimeCirceDecoder.map(_.toInstant)

}

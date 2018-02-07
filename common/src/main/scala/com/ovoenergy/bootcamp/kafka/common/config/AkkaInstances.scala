package com.ovoenergy.bootcamp.kafka.common.config

import akka.http.scaladsl.model.Uri
import ciris.ConfigReader

trait AkkaInstances {

  implicit lazy val uriConfigReader: ConfigReader[Uri] =
    ConfigReader.catchNonFatal("Uri")(Uri.apply)

}

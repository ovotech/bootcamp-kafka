import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"

  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.5"

  object ciris {

    val core = "is.cir" %% "ciris-core" % "0.6.2"
  }

  object http4s {

    private val version = "0.18.0-M8"

    val core = "org.http4s" %% "http4s-core" % version
    val dsl = "org.http4s" %% "http4s-dsl" % version
    val circe = "org.http4s" %% "http4s-circe" % version
    val client = "org.http4s" %% "http4s-client" % version
    val server = "org.http4s" %% "http4s-server" % version
    val blazeServer = "org.http4s" %% "http4s-blaze-server" % version
    val blazeClient = "org.http4s" %% "http4s-blaze-client" % version

  }

  object cats {

    private val version = "1.0.1"

    val kernel = "org.typelevel" %% "cats-kernel" % version
    val core = "org.typelevel" %% "cats-core" % version
    val macros = "org.typelevel" %% "cats-macros" % version
    val testkit = "org.typelevel" %% "cats-testkit" % version
    val free = "org.typelevel" %% "cats-free" % version
  }

  object circe {

    private val version = "0.9.0"

    val core = "io.circe" %% "circe-core" % version
    val literal = "io.circe" %% "circe-literal" % version
    val parser = "io.circe" %% "circe-parser" % version
    val generic = "io.circe" %% "circe-generic" % version
  }

  object log4j {
    private val version = "2.8.2"

    val api = "org.apache.logging.log4j" % "log4j-api" % version
    val core = "org.apache.logging.log4j" % "log4j-core" % version
    val slf4jImpl = "org.apache.logging.log4j" % "log4j-slf4j-impl" % version
  }

  object slf4j {
    private val version = "1.7.25"

    val api = "org.slf4j" % "slf4j-api" % version
  }

  object kafka {
    private val version = "1.0.0"

    val clients = "org.apache.kafka" % "kafka-clients" % version
  }

  object dockerTestkit {

    private val version = "0.9.6"

    val core = "com.whisk" %% "docker-testkit-core" % version
    val scalaTest = "com.whisk" %% "docker-testkit-scalatest" % version
    val implDockerJava = "com.whisk" %% "docker-testkit-impl-docker-java" % version

  }

  val commsDockerTestkit = "com.ovoenergy" %% "comms-docker-testkit" % "1.5"

  object akka {

    private val version = "2.5.9"
    private val httpVersion = "10.0.11"

    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val stream = "com.typesafe.akka" %% "akka-stream" % version
    val parsing = "com.typesafe.akka" %% "akka-parsing" % httpVersion
    val http = "com.typesafe.akka" %% "akka-http" % httpVersion
    val httpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % httpVersion
    val httpCore = "com.typesafe.akka" %% "akka-http-core" % httpVersion
    val httpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.19.0"
  }

  object kafkaSerialization {
    private val version = "0.3.4"

    val core = "com.ovoenergy" %% "kafka-serialization-core" % version
    val circe = "com.ovoenergy" %% "kafka-serialization-circe" % version
    val avro4s = "com.ovoenergy" %% "kafka-serialization-avro4s" % version
  }
}
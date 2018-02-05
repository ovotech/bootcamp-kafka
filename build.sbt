import Dependencies._

lazy val commonSettings = List(
  organization := "com.ovoenergy.bootcamp",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-Ypartial-unification",
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions" // Allow definition of implicit functions called views
  ),
  resolvers ++= Seq(
    Resolver.defaultLocal,
    Resolver.mavenLocal,
    Resolver.bintrayRepo("ovotech", "maven"),
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    "confluent-release" at "http://packages.confluent.io/maven/"
  ),
  libraryDependencies ++= Seq(
    scalaTest % Test
  )
)

lazy val `kafka-workshop` = (project in file("."))
  .aggregate(domain, common, `account-service`, `customer-service`, `acquisition-service`)
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop"
  )

lazy val domain = (project in file("domain"))
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop-domain"
  )
  .settings(
    libraryDependencies ++= Seq(
      cats.kernel,
      cats.core,
      cats.macros
    )
  )

lazy val common = (project in file("common"))
  .dependsOn(domain)
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop-common"
  )
  .settings(
    libraryDependencies ++= Seq(
      circe.core,
      circe.literal,
      circe.parser,
      circe.generic,
      kafka.clients,
      dockerTestkit.core % Test,
      dockerTestkit.scalaTest % Test,
      dockerTestkit.implDockerJava % Test,
      commsDockerTestkit % Test,
      log4j.api % Test,
      log4j.core % Test,
      log4j.slf4jImpl % Test
    )
  )


lazy val `account-service` = (project in file("account-service"))
  .dependsOn(domain, common)
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop-account-service"
  )
  .settings(
    libraryDependencies ++= Seq(
      akka.actor,
      akka.stream,
      akka.parsing,
      akka.http,
      akka.httpCore,
      akka.httpCirce,
      circe.core,
      circe.literal,
      circe.parser,
      circe.generic,
      slf4j.api,
      log4j.api % Runtime,
      log4j.core % Runtime,
      log4j.slf4jImpl % Runtime
    )
  )


lazy val `customer-service` = (project in file("customer-service"))
  .dependsOn(domain, common)
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop-customer-service"
  )
  .settings(
    libraryDependencies ++= Seq(
      akka.actor,
      akka.stream,
      akka.parsing,
      akka.http,
      akka.httpCore,
      akka.httpCirce,
      circe.core,
      circe.literal,
      circe.parser,
      circe.generic,
      slf4j.api,
      log4j.api % Runtime,
      log4j.core % Runtime,
      log4j.slf4jImpl % Runtime
    )
  )


lazy val `acquisition-service` = (project in file("acquisition-service"))
  .dependsOn(domain, common)
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "kafka-workshop-acquisition-service"
  )
  .settings(
    libraryDependencies ++= Seq(
      akka.actor,
      akka.stream,
      akka.parsing,
      akka.http,
      akka.httpCore,
      akka.httpCirce,
      circe.core,
      circe.literal,
      circe.parser,
      circe.generic,
      slf4j.api,
      log4j.api % Runtime,
      log4j.core % Runtime,
      log4j.slf4jImpl % Runtime
    )
  )

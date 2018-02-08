addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.3")
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "0.2")

libraryDependencies += "com.spotify" % "docker-client" % "8.3.1"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.21"

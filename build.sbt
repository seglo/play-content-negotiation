name := "play-content-negotiation"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

val json4sNative = "org.json4s" %% "json4s-native" % "3.4.0"
val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.4.0"

libraryDependencies ++= Seq(
  json4sJackson,
  json4sNative,
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

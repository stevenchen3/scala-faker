import sbt._

object Dependencies {
  // Circe library dependencies
  val circeVersion = "0.11.0"
  val circeDeps = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)

  val jodaTimeDeps = Seq(
    "joda-time" % "joda-time" % "2.10.1"
  )

  val jodaConvertDeps = Seq(
    "org.joda" % "joda-convert" % "2.2.0"
  )

  // Scalatest library dependencies
  val scalatestDeps = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test
  )

  val typesafeConfigDeps = Seq(
    "com.typesafe" % "config" % "1.3.3"
  )

  val typesafeScalaLoggingDeps = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )
}

import scalafix.sbt.ScalafixPlugin.autoImport._
import sbt._

object Dependencies {
  // Circe library dependencies
  lazy val circeVersion = "0.11.0"
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
    "org.scalatest" %% "scalatest" % "3.0.6" % "test"
  )

  val typesafeConfigDeps = Seq(
    "com.typesafe" % "config" % "1.3.3"
  )
}

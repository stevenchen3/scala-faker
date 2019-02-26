ThisBuild / organization := "com.github.stevenchen3"
ThisBuild / organizationName := "Steven Chen Open Source Work"
ThisBuild / organizationHomepage := Some(url("https://github.com/stevenchen3"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/stevenchen3/scala-faker"),
    "scm:git@github.com:stevenchen3/scala-faker.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "stevenchen3",
    name  = "Steven Chen",
    email = "steven.chen.xyz@gmail.com",
    url   = url("https://github.com/stevenchen3")
  )
)

ThisBuild / description := "Scala fake data generator library"
ThisBuild / licenses := List("BSD 3-Clause" -> new URL("https://opensource.org/licenses/BSD-3-Clause"))
ThisBuild / homepage := Some(url("https://github.com/stevenchen3/scala-faker"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ ⇒ false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

ThisBuild / publishMavenStyle := true
ThisBuild / publishArtifact in Test := false
ThisBuild / releaseCrossBuild := true
ThisBuild / useGpg := true

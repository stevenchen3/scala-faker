scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/stevenchen3/scala-faker"),
    "scm:git@github.com:stevenchen3/scala-faker.git"
  )
)

developers in ThisBuild := List(
  Developer(
    id    = "stevenchen3",
    name  = "Steven Chen",
    email = "steven.chen.xyz@gmail.com",
    url   = url("https://github.com/stevenchen3")
  )
)

description in ThisBuild := "Scala fake data generator library"
licenses    in ThisBuild := List("BSD 3-Clause" -> new URL("https://opensource.org/licenses/BSD-3-Clause"))
homepage    in ThisBuild := Some(url("https://github.com/stevenchen3/scala-faker"))

// Remove all additional repository other than Maven Central from POM
pomIncludeRepository in ThisBuild := { _ ⇒ false }
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle in ThisBuild := true
publishArtifact in Test in ThisBuild := false
useGpg in ThisBuild := true

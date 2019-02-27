import Dependencies._

lazy val commonSettings = Seq(
  name := "scala-faker",
  scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8"),
  javacOptions  in Compile ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions   in Test    ++= Seq("-Xms256m", "-Xmx2g", "-Dconfig.resource=test.conf"),
  javaOptions   in run     ++= Seq("-Xms256m", "-Xmx2g", "-XX:+UseParallelGC", "-server"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.typesafeRepo("releases")
  )
)

lazy val publishSettings = Seq(
  organization         := "com.github.stevenchen3",
  organizationName     := "Steven Chen's Open Source Work",
  organizationHomepage := Some(url("https://github.com/stevenchen3")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/stevenchen3/scala-faker"),
      "scm:git@github.com:stevenchen3/scala-faker.git"
    )
  ),
  developers := List(
    Developer(
      id    = "stevenchen3",
      name  = "Steven Chen",
      email = "steven.chen.xyz@gmail.com",
      url   = url("https://github.com/stevenchen3")
    )
  ),
  description := "Scala fake data generator library",
  licenses    := List("BSD 3-Clause" -> new URL("https://opensource.org/licenses/BSD-3-Clause")),
  homepage    := Some(url("https://github.com/stevenchen3/scala-faker")),
  // Remove all additional repository other than Maven Central from POM
  pomIncludeRepository := { _ ⇒ false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle := true,
  publishArtifact in Test := false,
  PgpKeys.useGpg := true
)

lazy val root = Project(id = "scala-faker", base = file("."))
  .settings(crossScalaVersions := Seq("2.11.8", "2.12.8"))
  .settings(commonSettings ++ publishSettings: _*)
  .settings(fork in run  := true)
  .settings(fork in Test := true)
  .settings(coverageEnabled := true)
  .settings(libraryDependencies ++= circeDeps)
  .settings(libraryDependencies ++= jodaTimeDeps)
  .settings(libraryDependencies ++= jodaConvertDeps)
  .settings(libraryDependencies ++= scalatestDeps)
  .settings(libraryDependencies ++= typesafeConfigDeps)

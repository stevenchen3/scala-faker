import Dependencies._
import xerial.sbt.Sonatype._

addCommandAlias("scalafixcheck", "; compile:scalafix --check ; test:scalafix --check")

val projectName   = "scala-faker"
val githubId      = "stevenchen3"
val githubBaseUrl = "https://github.com"
val githubUrl     = s"${githubBaseUrl}/stevenchen3"
val emailAddress  = "steven.chen.xyz@gmail.com"
val fullName      = "Steven Chen"

lazy val commonSettings = Seq(
  name := projectName,
  scalacOptions in Compile ++= Seq(
    "-encoding",
    "UTF-8",
    "-target:jvm-1.8",
    "-Yrangepos",
    "-Ywarn-unused-import"
  ),
  javacOptions  in Compile ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions   in Test    ++= Seq("-Xms256m", "-Xmx2g", "-Dconfig.resource=test.conf"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.typesafeRepo("releases")
  ),
  addCompilerPlugin(scalafixSemanticdb)
)

val licenseName = "BSD-3-Clause"
val licenseUrl  = "https://opensource.org/licenses/BSD-3-Clause"
lazy val headerSettings = Seq(
  organizationName := fullName,
  startYear := Some(2019),
  licenses  += (licenseName, new URL(licenseUrl))
)

lazy val publishSettings = Seq(
  organization         := s"com.github.${githubId}",
  organizationName     := fullName,
  organizationHomepage := Some(url(githubUrl)),
  scmInfo := Some(
    ScmInfo(
      url(s"${githubBaseUrl}/${githubId}/${projectName}"),
      s"scm:git@github.com:${githubId}/${projectName}.git"
    )
  ),
  developers := List(
    Developer(
      id    = githubId,
      name  = fullName,
      email = emailAddress,
      url   = url(githubUrl)
    )
  ),
  description := "Scala fake data generator library",
  licenses    := List(licenseName -> new URL(licenseUrl)),
  homepage    := Some(url(s"${githubBaseUrl}/${githubId}/${projectName}")),
  // Remove all additional repository other than Maven Central from POM
  pomIncludeRepository := { _ ⇒ false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  // Sync with Maven central
  publishMavenStyle := true,
  publishArtifact in Test := false,
  PgpKeys.useGpg := true,
  sonatypeProjectHosting := Some(GitHubHosting(githubId, projectName, emailAddress))
)

lazy val root = Project(id = projectName, base = file("."))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(crossScalaVersions := Seq("2.11.12", "2.12.8"))
  .settings(commonSettings ++ headerSettings ++ publishSettings)
  .settings(fork in run  := true)
  .settings(fork in Test := true)
  .settings(coverageEnabled := true)
  .settings(libraryDependencies ++= circeDeps)
  .settings(libraryDependencies ++= jodaTimeDeps)
  .settings(libraryDependencies ++= jodaConvertDeps)
  .settings(libraryDependencies ++= scalatestDeps)
  .settings(libraryDependencies ++= typesafeConfigDeps)

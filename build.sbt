import Dependencies._

lazy val commonSettings = Seq(
  name := "faker",
  organization := "io.alphash",
  scalaVersion := "2.12.8",
  scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8"),
  javacOptions  in Compile ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions in Test ++= Seq("-Xms256m", "-Xmx2g", "-Dconfig.resource=test.conf"),
  javaOptions in run  ++= Seq("-Xms256m", "-Xmx2g", "-XX:+UseParallelGC", "-server"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.typesafeRepo("releases")
  )
)

lazy val root = Project(id = "faker", base = file("."))
  .settings(commonSettings: _*)
  .settings(fork in run  := true)
  .settings(fork in Test := true)
  .settings(coverageEnabled := true)
  .settings(libraryDependencies ++= circeDeps)
  .settings(libraryDependencies ++= jodaTimeDeps)
  .settings(libraryDependencies ++= jodaConvertDeps)
  .settings(libraryDependencies ++= scalatestDeps)
  .settings(libraryDependencies ++= typesafeConfigDeps)

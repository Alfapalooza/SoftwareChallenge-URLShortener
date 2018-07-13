name := """url-shortener"""
version := "1.0"
scalaVersion := "2.12.6"

lazy val root =
  (project in file(".")).enablePlugins(PlayScala)

val utils =
  Seq(
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "org.apache.commons" % "commons-collections4" % "4.2")

val tests =
  Seq(
    "org.specs2" %% "specs2-core" % "4.3.2" % Test,
    "org.specs2" %% "specs2-junit" % "4.3.2" % Test)

libraryDependencies ++=
  utils ++ tests :+ guice

javacOptions ++=
  Seq(
    "-source", "1.8",
    "-target", "1.8",
    "-Xlint:unchecked",
    "-encoding", "UTF-8")

scalacOptions ++=
  Seq(
    "-unchecked",
    "-deprecation",
    "-Xlint",
    "-Ywarn-dead-code",
    "-language:_",
    "-target:jvm-1.8",
    "-encoding", "UTF-8")

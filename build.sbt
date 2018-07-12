name := """url-shortener"""
version := "1.0"
scalaVersion := "2.12.6"

lazy val root =
  (project in file(".")).enablePlugins(PlayScala)

val utils =
  Seq(
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "org.apache.commons" % "commons-collections4" % "4.2")

libraryDependencies ++=
  utils :+ guice

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

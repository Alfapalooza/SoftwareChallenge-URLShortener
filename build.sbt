name := """play-seed"""
version := "1.0"
scalaVersion := "2.11.11"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

transitiveClassifiers in Global :=
  Seq(
    Artifact.SourceClassifier)

resolvers ++=
  Seq(
    "byrdelibraries" at "https://dl.cloudsmith.io/public/byrde/libraries/maven/",
    "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk",
    Resolver.sonatypeRepo("releases"))

val utils =
  Seq(
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "io.igl" %% "jwt" % "1.2.0",
    "de.svenkubiak" % "jBCrypt" % "0.4.1",
    "org.byrde" % "commons_2.11" % "1.0.77")

val orm =
  Seq(
    "com.typesafe.slick" % "slick-hikaricp_2.11" % "3.2.0",
    "com.typesafe.slick" % "slick_2.11" % "3.2.0")

val redis =
  Seq(
    "org.sedis" %% "sedis" % "1.2.2",
    "redis.clients" % "jedis" % "2.4.2",
    "biz.source_code" % "base64coder" % "2010-12-19",
    "org.byrde" % "play-redis_2.11" % "2.6.1",
    cacheApi)

val postgresql =
  Seq (
    "org.postgresql" % "postgresql" % "9.4.1212")

libraryDependencies ++=
  utils ++ orm ++ postgresql ++ redis :+ guice

unmanagedJars in Compile ++=
  ((baseDirectory.value / "lib") ** "*.jar").classpath

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

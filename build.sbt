import sbt.Package.ManifestAttributes

name := "utms"

version := "1.0.0"

organization := "org.latestbit"

homepage := Some( url( "https://bitbucket.org/latestbit/utms" ) )

licenses := Seq( ( "Apache License v2.0", url( "http://www.apache.org/licenses/LICENSE-2.0.html" ) ) )

scalaVersion := "2.13.1"

crossScalaVersions := Seq( "2.13.1", "2.12.10" )

sbtVersion := "1.3.5"

autoScalaLibrary := false

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.scalactic" %% "scalactic" % "3.1.0" % "test",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature"
)

javacOptions ++= Seq( "-Xlint:deprecation", "-source", "1.8", "-target", "1.8", "-Xlint" )

packageOptions := Seq(
  ManifestAttributes(
    ( "Build-Jdk", System.getProperty( "java.version" ) ),
    (
      "Build-Date",
      new java.text.SimpleDateFormat( "MMddyyyy-hhmm" ).format( java.util.Calendar.getInstance().getTime )
    )
  )
)

retrieveManaged := true

lazy val utmsProject = (project in file( "." ))

import sbt._
import Keys._

object MyBuild extends Build {
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "com.simba",
    scalaVersion := "2.10.3"
  )

  lazy val JenaConsole = Project(
    id = "JenaConsole",
    base = file("."),
    settings = Defaults.defaultSettings ++
    sbtassembly.Plugin.assemblySettings
  )
}

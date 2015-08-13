import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object MyBuild extends Build {
  val scalaV = Option(System.getProperty("scala.version")).getOrElse("2.11.6")
  val jenaV  = "2.12.1"
  val tdbV   = "1.1.1"
  val slf4jV = "1.7.12"
  val log4jV = "1.2.17"

  lazy val commonSettings = Defaults.defaultSettings ++ Seq(
    version      := "0.1-SNAPSHOT",
    organization := "com.simba",
    scalaVersion := scalaV,
    scalacOptions in Compile ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation", "-feature", "-unchecked",
      "-Xlint"),
    libraryDependencies ++= Seq(
      "org.apache.jena" % "jena-core" % jenaV excludeAll(ExclusionRule(organization = "org.slf4j")), 
      "org.apache.jena" % "jena-arq"  % jenaV excludeAll(ExclusionRule(organization = "org.slf4j")), 
      "org.apache.jena" % "jena-tdb"  % tdbV excludeAll(ExclusionRule(organization = "org.slf4j")),
      "org.slf4j" % "slf4j-api"     % slf4jV,
      "org.slf4j" % "slf4j-log4j12" % slf4jV,
      "log4j"     % "log4j"         % log4jV
    )
  )

  lazy val copyDependencies = TaskKey[Unit]("copyDep")

  def copyDepTask = copyDependencies <<= (update, crossTarget, scalaVersion) map {
    (updateReport, out, scalaVer) =>
    updateReport.allFiles foreach { srcPath =>
      val destPath = out / "lib" / srcPath.getName
      IO.copyFile(srcPath, destPath, preserveLastModified=true)
    }
  }

  lazy val buildSettings = commonSettings

  lazy val JenaConsole = Project(
    id = "JenaConsole",
    base = file("."),
    settings = commonSettings ++
    sbtassembly.Plugin.assemblySettings ++
    Seq(
      copyDepTask,
      logLevel in assembly := Level.Warn
    )
  )
}

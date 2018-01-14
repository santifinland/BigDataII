import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.MergeStrategy

object Edu extends Build {

  val EduMergeStrategy: _root_.sbt.Def.Setting[(String) => MergeStrategy] = assemblyMergeStrategy in assembly := {
    case "reference.conf" => MergeStrategy.concat
    case PathList("META-INF", xs @ _*) =>
      //noinspection VariablePatternShadow
      xs map {_.toLowerCase} match {
        case "manifest.mf" :: Nil => MergeStrategy.discard
        case "services" :: xs => MergeStrategy.concat
        case ps @ (x :: xs) if ps.last.endsWith(".sf") => MergeStrategy.discard
        case _ => MergeStrategy.first
      }
    case _ => MergeStrategy.first
  }
  val Organization = "es.sdmt"
  val Version = s"0.2.${Common.revision}"
  val ScalaVersion = "2.11.11"
  val libraries = Seq(
    "joda-time" % "joda-time" % "2.9.7",
    "org.apache.spark" %% "spark-core" % "2.0.2" % "provided, test",
    "org.apache.spark" %% "spark-sql" % "2.0.2" % "provided, test",
    "org.rogach" %% "scallop" % "2.0.0",
    "org.scalactic" %% "scalactic" % "3.0.1" % "test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-log4j12" % "1.7.5"
  )

  def projectSettings(projectName: String) = Seq(
    organization := Organization,
    name := projectName,
    version := Version,
    exportJars := true,
    scalaVersion := ScalaVersion,
    resolvers += Classpaths.typesafeReleases,
    parallelExecution in Test := false
  )

  lazy val edu: Project = Project (
    "edu",
    file("edu"),
    settings = projectSettings("edu") ++ (libraryDependencies ++= libraries)
  )
}

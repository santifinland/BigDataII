import sbt._

object Common {

  private val build = System.getenv("BUILD_NUMBER")
  val revision: String = if (build == null) System.currentTimeMillis.toString else build
  val baseversion: SettingKey[String] = SettingKey[String]("base-version", "The base version to be built")
}

package es.sdmt.edu.core

import org.apache.spark.sql.SparkSession
import org.slf4j.{LoggerFactory, Logger}


object Edu extends Flight {

  lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def AppName = "Edu"
  case class Flights(Year: String, Month: String, DayofMonth: String, DayOfWeek: String, DepTime: String, ArrTime: String, FlightNum: String, ActualElapsedTime: Int, AirTime: Int, ArrDelay: Int, DepDelay: Int, Origin: String, Dest: String, Distance: Int, CarrierDelay: Int, WeatherDelay: Int, SecurityDelay: Int)

  def main(args: Array[String]): Unit = {

    /** Edu program starts here */
    logger.info("Edu. Tecnicas Analíticas con Spark")

    /** Build Spark Session */
    val spark = SparkSession
      .builder()
      .appName("Edu")
      .getOrCreate()

    import spark.implicits._
    val flights = spark.sparkContext.textFile("data/2008.csv")
      .filter(p => !p.contains("NA"))
      .map(_.split(","))
      .filter(p => p(0)!="Year")
      .map(p =>
        Flights(p(0), p(1),p(2),p(3),p(4),p(6),p(9),p(11).toInt,p(13).toInt,p(14).toInt,
          p(15).toInt,p(16),p(17),p(18).toInt,p(24).toInt,p(25).toInt,p(27).toInt))
      .toDF()

    flights.show()

    flights.count()

    flights.createOrReplaceTempView("flights")

    spark.sqlContext.sql("select * from flights").show()

    spark.sqlContext.sql("select Origin,Dest from flights").show()

    spark.sqlContext.sql("select count(0) from flights").show()

    // ¿Que aeropuerto tuvo más retraso en las salidas? ¿y en las llegadas?
    spark.sqlContext.sql("select Origin,Max(DepDelay) as depDel from flights group by Origin order by depDel desc limit 1").show()

    spark.sqlContext.sql("select Dest,Max(ArrDelay) as arrDel from flights group by Dest order by arrDel desc limit 1").show()

    // ¿Qué mes hay más vuelos? Y, ¿día de la semana?
    spark.sqlContext.sql("select month,count(0) as count from flights group by month order by count").show()
    spark.sqlContext.sql("select DayOfWeek,count(0) as count from flights group by DayOfWeek order by count").show()

    // #¿Cúal es el mes con más retraso medio en la salida y llegada?
    spark.sqlContext.sql("select month,avg(DepDelay),avg(ArrDelay) from flights group by month order by month").show()
    spark.sqlContext.sql("select DayOfWeek,avg(DepDelay),avg(ArrDelay) from flights group by DayOfWeek order by DayOfWeek").show()


    // #¿Qué aeropuerto tuvo más retrasos debido a la metereología?
    spark.sqlContext.sql("select Origin,Dest,max(WeatherDelay) as del from flights group by Origin,Dest order by del desc limit 1").show()
  }
}

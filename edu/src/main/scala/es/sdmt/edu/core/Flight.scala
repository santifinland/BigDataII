
package es.sdmt.edu.core

import org.apache.spark.sql.types._


trait Flight {

  private val fields = List(
    StructField("Year", StringType, nullable=true),
    StructField("Month", StringType, nullable=true),
    StructField("DayofMonth", StringType, nullable=true),
    StructField("DayOfWeek", StringType, nullable=true),
    StructField("DepTime", StringType, nullable=true),
    StructField("notused5", StringType, nullable=true),
    StructField("ArrTime", StringType, nullable=true),
    StructField("notused7", StringType, nullable=true),
    StructField("notused8", StringType, nullable=true),
    StructField("FlightNum", StringType, nullable=true),
    StructField("notused10", StringType, nullable=true),
    StructField("ActualElapsedTime", IntegerType, nullable=true),
    StructField("notused12", StringType, nullable=true),
    StructField("AirTime", IntegerType, nullable=true),
    StructField("ArrDelay", IntegerType, nullable=true),
    StructField("DepDelay", IntegerType, nullable=true),
    StructField("Origin", StringType, nullable=true),
    StructField("Dest", StringType, nullable=true),
    StructField("Distance", IntegerType, nullable=true),
    StructField("notused19", StringType, nullable=true),
    StructField("notused20", StringType, nullable=true),
    StructField("notused21", StringType, nullable=true),
    StructField("notused22", StringType, nullable=true),
    StructField("notused23", StringType, nullable=true),
    StructField("CarrierDelay", IntegerType, nullable=true),
    StructField("WeatherDelay", IntegerType, nullable=true),
    StructField("notused26", StringType, nullable=true),
    StructField("SecurityDelay", IntegerType, nullable=true))

  val schema = StructType(fields)
}


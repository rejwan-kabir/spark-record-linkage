name := "spark-record-linkage"

version := "1.0"

scalaVersion := "2.11.8"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.4")

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.1.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.1.0"
)
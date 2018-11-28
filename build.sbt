name := "spark-record-linkage"

version := "1.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.3.1" % "provided",
  "org.apache.spark" % "spark-sql_2.11" % "2.3.1" % "provided"
)

mainClass in assembly := Some("spark.rl.Main")
assemblyJarName in assembly := "spark-rl.jar"

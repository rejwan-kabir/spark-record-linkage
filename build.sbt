name := "spark-record-linkage"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-sql_2.11" % "2.1.0" % "provided"
)

mainClass in assembly := Some("spark.rl.Main")
assemblyJarName in assembly := "spark-rl.jar"
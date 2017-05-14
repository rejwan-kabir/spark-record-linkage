package spark.rl

import org.apache.spark.sql._
import org.apache.spark.sql.functions._

import scala.reflect.io.Path
import scala.util.Try

object Main {
  val spark = SparkSession.builder()
    .appName("A simple Spark Record Linkage")
    .master("local")
    .getOrCreate()

  import spark.implicits._

  case class Listing(title: String, manufacturer: String, currency: String, price: String)

  def main(args: Array[String]): Unit = {
    if (null == args || args.length < 2) {
      println("Usage: <command> <Input-dir-path> <output-dir-path>")
      return
    }
    var inputDir = args(0)
    if (inputDir.endsWith("/")) {
      inputDir = inputDir.substring(0, inputDir.length - 1)
    }
    val outputDir = args(1)
    Try(Path(outputDir).deleteRecursively())
    val products = spark.read.json(inputDir + "/products.txt")
    val listings = spark.read.json(inputDir + "/listings.txt")
    val combined = products.as("p").join(listings.as("l"), $"p.manufacturer" <=> $"l.manufacturer")
      .groupBy($"p.manufacturer", $"p.model", $"p.family", $"l.currency", $"l.price")
      .agg(first($"p.product_name").as("product_name"), first($"l.title").as("title"))
    val without_family = combined.filter("family is null")
    without_family.registerTempTable("without_family")
    val matched_without_family = spark.sql("SELECT * FROM without_family WHERE CONCAT(' ', title, ' ') like CONCAT('% ', model, ' %')")
    val with_family = combined.filter("family is not null")
    with_family.registerTempTable("with_family")
    val matched_with_family = spark.sql("SELECT * FROM with_family WHERE CONCAT(' ', title, ' ') like CONCAT('% ', model, ' %') AND CONCAT(' ', title, ' ') like CONCAT('% ', family, ' %')")
    val all_matched = matched_with_family.unionAll(matched_without_family)
    val pair = all_matched.rdd.map({
      case Row(manufacturer: String, _, _, currency: String, price: String, product_name: String, title: String) =>
        (product_name, List(Listing(title, manufacturer, currency, price)))
    }).foldByKey(List())((acc, cur) => acc ++ cur)
      .toDF("product_name", "listings")
    pair.coalesce(1).write.json(outputDir)
    spark.stop()
  }
}

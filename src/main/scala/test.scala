import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object test {
  val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCsadount")

  def main(args: Array[String]) {
    val ssc = new StreamingContext(conf,Seconds(10))
    val lines=ssc.socketTextStream("192.168.56.101",9999,StorageLevel.MEMORY_AND_DISK_SER_2)
    lines.flatMap(x=>x.split(" ")).map(rec => (rec,1)).reduceByKey(_+_).print()
    ssc.start()
    ssc.awaitTermination()


  }}

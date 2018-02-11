import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.json.JSONObject

object EmotionsCalculate {

def main(args : Array[String]): Unit =
  {
    val conf = new SparkConf().setMaster("local").setAppName("Spark Streaming - PopularHashTags")
    val sc = new SparkContext(conf)
    val outputBasedir=args(0)+"\\Sentiments"
    val inputBaseDir=args(0)+"\\Tweets"
    val fs = FileSystem.get(sc.hadoopConfiguration)
    if(!fs.exists(new Path(inputBaseDir)))
    {
      println("Base Directory Does not exists")
      return
    }

    if(fs.exists(new Path(outputBasedir)))
      fs.delete(new Path(outputBasedir),true)


    var a =""
    val data3 = sc.textFile(inputBaseDir+"\\Tweets-*")
    data3.collect().foreach({
      a+=_ +","
    })

    val tweets = data3.zipWithIndex().map(rec => (rec._2,rec._1))

  a=a.substring(0,a.length-1)

    val sent=new Emotions
    val emot=sent.sentiment(a)
    var l:List[Double]=List()

    for( x <-0 to emot.length()-1 ){
      val sentiment=emot.getJSONObject(x).getDouble("sentiment")
      l=l:+sentiment
    }
    val rdd=sc.parallelize(l).zipWithIndex().map(rec => (rec._2,rec._1))

    val finalEmotionRDD = tweets.join(rdd).
      map(rec => (rec._2._1,rec._2._2))

    finalEmotionRDD.coalesce(1,true).
      saveAsTextFile(outputBasedir)

    finalEmotionRDD.count()

  }


}

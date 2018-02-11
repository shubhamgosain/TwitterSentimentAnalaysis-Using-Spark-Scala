import com.typesafe.config.ConfigFactory
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.{SparkConf, SparkContext}
import Utils._
import org.apache.hadoop.fs.{FileSystem, Path}

object StreamTweets {
  def main(args: Array[String]) {


    val props=ConfigFactory.load()
    val conf = new SparkConf().setMaster(props.getConfig(args(0)).getString("executionMode")).setAppName("Spark Streaming - PopularHashTags")
    val sc = new SparkContext(conf)

    val outputBasedir=args(1)

    val fs = FileSystem.get(sc.hadoopConfiguration)
    if(fs.exists(new Path(outputBasedir)))
      fs.delete(new Path(outputBasedir),true)

    sc.setLogLevel("WARN")
    val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = Array("dxbScm7bjp1vprVnkt0nojzB8","YwVlbgFvx1rcOwga6qqdDiOw0wvfyyud9EQ6miFQUvvHbhAixR","1939755950-ZaovntVKkbP6Ws9scaaJR3sFUD4C7P9dOqDkIjy","yaAxotn7F6eRrWrHs71YReDa2w9V57gFWLqmqnT16e7Wd")
    val filters = args.takeRight(args.length - 2)
    println(filters)
    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    val ssc = new StreamingContext(sc,Seconds(60))


    val stream = TwitterUtils.createStream(ssc, None, filters).filter(_.getLang() == "en")
    stream.print()

    val textAndSentences = stream.
        map(_.getText)

    val textAndMeaningfulSentences= textAndSentences.
        map(_.toLowerCase).
        map(wordsOf(_)).
        map(keepActualWords).
      map(rec => {
        var s = rec.toString()
        s=s.replaceAll(",","").replace("WrappedArray(","").replace("rt","")
        s= s.substring(0,s.length-1)
        "\""+s+"\""
      }).repartition(1).saveAsTextFiles(outputBasedir)


    ssc.start ()
    ssc.awaitTermination ()

  }
}

import java.io._
import java.net.URL
import java.util.Scanner
import javax.net.ssl.HttpsURLConnection

import org.apache.spark.{SparkConf, SparkContext}
import org.json.JSONObject

import scala.util.parsing.json.JSONArray

object read {

  def main(args: Array[String]): Unit = {


    val rawjson ="""{"texts": ["He is a very good person with nice heart which considers himself as a very good person but with a bad attitude","He is a good person", "He is a bad person"]}"""
    val sent=new Emotions
    var ax="\"i really wish you could be wrong about but\",\"well you have to block out the haters if you apologized for what you said sis\""
    val a=sent.sentiment(ax)

  }

}

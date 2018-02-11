import java.io._
import java.net.URL
import java.util.Scanner
import javax.net.ssl.HttpsURLConnection
import org.json.JSONObject

class Emotions(){

def sentiment(raw: String):org.json.JSONArray={
  val url = new URL("https://api.indatalabs.com/v1/text?apikey=b52c3f37-1333-46ff-82e0-37d20a3cd1ca&models=sentiment")

  val con: HttpsURLConnection = url.openConnection().asInstanceOf[HttpsURLConnection]
  val rawjson ="{\"texts\":[" +raw+"]}"
  con.setRequestMethod("POST")
  con.setDoOutput(true)
  con.setDoInput(true)
  con.setRequestProperty("Content-Type", "application/json")
  con.setRequestProperty("Accept", "application/json")

  con.connect()

  val os: OutputStream = con.getOutputStream()
  os.write(rawjson.getBytes("UTF-8"))
  os.flush()
  os.close()

  val in = con.getContent
  val reader = new BufferedReader(new InputStreamReader(con.getInputStream))
  var inputLine:String="x"
  var response =new StringBuffer()
  inputLine=reader.readLine()
  while (inputLine != null) {
  response.append((inputLine))
  inputLine=reader.readLine()
  }
  reader.close()
  val obj:JSONObject=new JSONObject(response.toString)
  val sentiment = obj.getJSONArray("response")



  sentiment
}

}

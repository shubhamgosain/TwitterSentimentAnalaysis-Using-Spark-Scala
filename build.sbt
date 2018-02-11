name := "Twitter"

version := "0.1"

scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.4.1"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.4.1"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.4.1"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "3.0.6"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "3.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-async" % "3.0.6"


libraryDependencies += "org.json" % "json" % "20180130"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"



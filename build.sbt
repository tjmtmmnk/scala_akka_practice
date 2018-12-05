name := "scala_akka_practice"
version := "1.0"

scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint")
libraryDependencies ++= {
  val akkaV = "2.4.2"
  val slickV = "3.1.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
    "com.typesafe.slick" %% "slick" % slickV,
    "com.typesafe.slick" % "slick-hikaricp_2.11" % slickV,
    "mysql" % "mysql-connector-java" % "8.0.11",
    "org.slf4j" % "slf4j-nop" % "1.6.4"

  )
}
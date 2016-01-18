name := """testPro"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

//disable doc generate..
sources in (Compile, doc) := Seq.empty

publishArtifact in (Compile, packageDoc) := false

publishArtifact in packageDoc := false

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

// front-end
libraryDependencies ++= Seq(
  "org.webjars" % "webjars-play_2.11" % "2.4.0-1",
  "org.webjars" % "html5shiv" % "3.7.2",
  "org.webjars" % "respond" % "1.4.2",
  "org.webjars" % "prettify" % "4-Mar-2013",
  "org.apache.commons" % "commons-email" % "1.3.2",
  "commons-codec" % "commons-codec" % "1.9",
  "org.webjars" % "toastr" % "2.1.0",
  "org.webjars" % "font-awesome" % "4.4.0",
  "org.webjars.bower" % "smalot-bootstrap-datetimepicker" % "2.3.1"
)

//backstage
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "1.1.0-M1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0-M1",
  "mysql" % "mysql-connector-java" % "5.1.31"
)

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.1"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

import AssemblyKeys._

assemblySettings

name := "template-scala-parallel-classification"

organization := "org.apache.predictionio"

libraryDependencies ++= Seq(
  "org.apache.predictionio" %% "apache-predictionio-core"  % pioVersion.value % "provided",
  "commons-io"               % "commons-io"                % "2.4",
  "org.apache.spark"        %% "spark-core"                % "1.2.0" % "provided",
  "org.apache.spark"        %% "spark-mllib"               % "1.2.0" % "provided",
  "org.json4s"              %% "json4s-native"             % "3.2.10")

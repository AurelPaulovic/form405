name := "form_405"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:existentials",
    "-language:higherKinds",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint:-unused,_",
    "-Yno-adapted-args",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-explaintypes",
    "-Ywarn-unused:patvars,-imports,-locals,-privates,-implicits",
    "-opt:l:inline",
    "-opt-inline-from:io.ceai.q.**",
    "-opt-warnings"
)

scalacOptions in (Compile, doc) ++= Seq(
    "-no-link-warnings"
)

mainClass in (Compile, run) := Some("com.aurelpaulovic.form405.Main")

mainClass in (Compile, packageBin) := Some("com.aurelpaulovic.form405.Main")

assemblyMergeStrategy in assembly := {
  case "BUILD" => MergeStrategy.discard
  case "META-INF/io.netty.versions.properties" => MergeStrategy.last
  case other => MergeStrategy.defaultMergeStrategy(other)
}

assemblyJarName in assembly := "form405.jar"

val depsLogging = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
)

val depsCats = Seq(
  "org.typelevel" %% "cats-core" % "1.0.1"
)

val depsTests = Seq(
    "org.scalatest" %% "scalatest" % "3.0.4" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

val depsTestsLogging = Seq(
    "org.slf4j" % "jul-to-slf4j" % "1.7.25" % "test"
)

val depsMonix = Seq(
    "io.monix" %% "monix" % "2.3.3"
)

val depsConf = Seq(
    "com.github.scopt" %% "scopt" % "3.7.0"
)

val depsCsv = Seq(
    "com.nrinaudo" %% "kantan.csv" % "0.4.0",
    "com.nrinaudo" %% "kantan.csv-generic" % "0.4.0",
    "com.nrinaudo" %% "kantan.csv-cats" % "0.4.0",
    "com.nrinaudo" %% "kantan.csv-java8" % "0.4.0"
)

val depsXml = Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.1.0"
)

libraryDependencies ++= depsLogging ++ depsCats ++ depsTests ++ depsTestsLogging ++ depsMonix ++ depsConf ++ depsCsv ++ depsXml

test in assembly := {}

autoAPIMappings := true

cancelable in Global := true

lazy val form405 = project.in(file("."))

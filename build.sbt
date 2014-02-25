import com.typesafe.sbt.SbtStartScript
import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "akka-in-action-kv-store"

organization := "xebia"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-encoding", "utf8",
                     "-target:jvm-1.7",
                     "-feature",
                     "-language:implicitConversions",
                     "-language:postfixOps",
                     "-unchecked",
                     "-deprecation",
                     "-Xlog-reflective-calls"
                    )

scalacOptions in Test ++= Seq("-Yrangepos")

mainClass := Some("com.xebia.akka.kv.Main")

resolvers ++= Seq("Sonatype Releases"   at "http://oss.sonatype.org/content/repositories/releases",
                  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Spray Repository"    at "http://repo.spray.io/",
                  "Base64 Repo"         at "http://dl.bintray.com/content/softprops/maven")

libraryDependencies ++= {
  val akkaVersion  = "2.2.3"
  val sprayVersion = "1.2.0"
  Seq(
    "com.typesafe.akka"       %%  "akka-actor"             % akkaVersion,
    "com.typesafe.akka"       %%  "akka-slf4j"             % akkaVersion,
    "io.spray"                %   "spray-can"              % sprayVersion,
    "io.spray"                %   "spray-client"           % sprayVersion,
    "io.spray"                %   "spray-routing"          % sprayVersion,
    "io.spray"                %%  "spray-json"             % "1.2.5",
    "me.lessis"               %%  "base64"                 % "0.1.0",
    "com.github.nscala-time"  %%  "nscala-time"            % "0.8.0",
    "ch.qos.logback"          %   "logback-classic"        % "1.1.1",
    "com.typesafe.akka"       %%  "akka-testkit"           % akkaVersion    % "test",
    "io.spray"                %   "spray-testkit"          % sprayVersion   % "test",
    "org.specs2"              %%  "specs2-core"            % "2.3.8"        % "test"
  )
}

seq(SbtStartScript.startScriptForClassesSettings: _*)

seq(Revolver.settings: _*)

seq(jacoco.settings : _*)

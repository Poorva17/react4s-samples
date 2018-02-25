enablePlugins(ScalaJSPlugin)

name := "react4s-samples"
organization := "com.github.ahnfelt"
version := "0.1-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "com.github.ahnfelt" %%% "react4s" % "0.9.2-SNAPSHOT"
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2"

scalaVersion := "2.12.4"

scalaJSUseMainModuleInitializer := true

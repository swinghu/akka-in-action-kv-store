package com.xebia.akka.kv

import scala.util._
import akka.actor.ActorSystem
import akka.io.IO

import spray.can.Http


object Main extends App {
  // Create the Actorsystem
  implicit val system = ActorSystem("akka-kv-store")

  // Create the API actor
  val api = system.actorOf(ApiActor.props, ApiActor.name)

  // Start Spray Can on the configured port.
  IO(Http) ! Http.Bind(listener = api, interface = "0.0.0.0", port = Settings(system).Http.Port)
}

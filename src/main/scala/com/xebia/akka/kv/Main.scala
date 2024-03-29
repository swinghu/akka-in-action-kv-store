package com.xebia.akka.kv

import akka.actor._
import akka.io.IO

import spray.can.Http


object Main extends App {
  // Create the Actorsystem
  implicit val system = ActorSystem("akka-kv-store")

  // Create the API actor
  val api = system.actorOf(Props[ApiActor], "api-actor")

  // Start Spray Can on the configured port.
  IO(Http) ! Http.Bind(listener = api, interface = "0.0.0.0", port = Settings(system).Http.Port)
}

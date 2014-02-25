package com.xebia.aka.kv

import akka.actor._

import spray.routing._
import spray.http._
import spray.http.StatusCodes._
import spray.httpx.encoding._


object ApiActor {
  def props = Props[ApiActor]
  def name = "api-actor"
}

class ApiActor extends HttpServiceActor
                  with Routes {

  def receive = runRoute(routes)
}




trait Routes extends ApiRoutes {
  def routes = {
    (decompressRequest & compressResponseIfRequested()) {
      apiRoutes
    }
  }
}


/** The complete API. */
trait ApiRoutes extends HttpService {
  def apiRoutes = {
    pathPrefix("api") {
      path("ping") {
        complete(OK, "pong")
      }
    }
  }
}

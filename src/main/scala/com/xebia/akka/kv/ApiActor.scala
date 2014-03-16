package com.xebia.akka.kv

import akka.actor._

import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing._


/** Simple test response to requests for /api/ping */
case class Pong(pong: String)
object Pong {
  /** All you need to make this class JSON serializable using spray-json
      The "Pong.apply" argument can be seen as a reference to the function
      that should be used to instantiate a new instance of Pong. Here we
      simply use the standard apply method common to all case classes. */
  import spray.json.DefaultJsonProtocol._
  implicit val jsonFormat = jsonFormat1(Pong.apply)
}


/** The Spray entry-point actor, bridging testable "routes" to the Akka actor system. */
class ApiActor extends HttpServiceActor with Routes {
  def receive = runRoute(routes)
}


/** Testable Spray routing handler. */
trait Routes extends HttpService with PingRoutes {
  def routes = {
    /** Support for optional GZIP/Deflate compression */
    (decompressRequest & compressResponseIfRequested()) {
      /** The only root URI we care about. All other requests will get a 404. */
      pathPrefix("api") {
        /** Simple test route endpoint for live-checking. */
        pingRoutes
      }
    }
  }
}

trait PingRoutes extends HttpService {
  def pingRoutes = {
    /** Simple test route endpoint for live-checking. */
    path("ping") {
      /** We only respond to GETs on this URI. All other requests will get a 405 (Method Not Allowed). */
      get {
        /** Always produce a 200 response using StatusCodes.OK and the JSON
            representation of a Pong instance JSON serialization is handled through
            type classes and the only one in scope here is the one for JSON
            through importing spray.httpx.SprayJsonSupport._, which detects
            the implicit RootJsonFormat in the Pong companion object. */
        complete(OK, Pong("pong!"))
      }
    }
  }
}

package com.xebia.akka.kv

import akka.actor._

import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing._


/** The Spray entry-point actor, bridging testable "routes" to the Akka actor system. */
class ApiActor extends HttpServiceActor with Routes {
  def receive = runRoute(routes)
}


/** Testable Spray routing handler. */
trait Routes extends HttpService with PingRoutes with BucketRoutes {
  def routes = {
    /** Support for optional GZIP/Deflate compression */
    (decompressRequest & compressResponseIfRequested()) {
      /** The only root URI we care about. All other requests will get a 404. */
      pathPrefix("api") {
        /** Sub-routes under /api, defined in route traits such as PingRoutes and BucketRoutes. */
        pingRoutes ~ bucketRoutes
      }
    }
  }
}

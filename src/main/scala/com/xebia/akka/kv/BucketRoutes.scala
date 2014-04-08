package com.xebia.akka.kv

import akka.actor._

import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing._


trait BucketRoutes extends HttpService {
  def bucketRoutes = {
    /** Everything i the unmatched path under /buckets. */
    pathPrefix("buckets") {
      pathEndOrSingleSlash {
        get {
          complete(OK, "Not implemented yet...")
        }
      } ~
      pathPrefix(Segment) { bucket =>
        pathPrefix("keys") {
          path(Segment) { key =>
            get {
              complete(OK, "Not implemented yet...")
            } ~
            put {
              complete(OK, "Not implemented yet...")
            }
          } ~
          pathEnd {
            post {
              complete(OK, "Not implemented yet...")
            }
          }
        }
      }
    }
  }
}

package com.xebia.akka.kv

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions

import spray.http.ContentTypes._
import spray.http.HttpMethods._
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing._
import spray.testkit.Specs2RouteTest


class PingRoutesSpec extends Specification with Specs2RouteTest with PingRoutes {
  def actorRefFactory = system

  "Our PingRoutes handler" should {
    """Respond to a GET on /ping with a JSON "pong" message.""" in {
      Get("/ping") ~> pingRoutes ~> check {
        status === OK
        contentType === `application/json`
        responseAs[Pong] === Pong("pong!")
      }
    }

    """Reject a PUT on /ping with a MethodRejection(GET).""" in {
      Put("/ping", "") ~> pingRoutes ~> check {
        rejection === MethodRejection(GET)
      }
    }

    """Reject a POST on /ping with a MethodRejection(GET).""" in {
      Post("/ping", "") ~> pingRoutes ~> check {
        rejection === MethodRejection(GET)
      }
    }

    """Reject a DELETE on /ping with a MethodRejection(GET).""" in {
      Delete("/ping") ~> pingRoutes ~> check {
        rejection === MethodRejection(GET)
      }
    }
  }
}

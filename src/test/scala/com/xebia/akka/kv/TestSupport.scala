package com.xebia.akka.kv

import scala.concurrent.Await
import scala.concurrent.duration._

import com.typesafe.config._

import org.specs2.mutable._

import akka.actor._
import akka.testkit._
import akka.util.Timeout

import util._


object TestSupport {

  // ==========================================================================
  // Bridging Specs2 with Akka Testkit
  // ==========================================================================

  def testConfig(testConfigSource: String = ""): Config = {
    val source = testConfigSource
    val config = if (source.isEmpty) ConfigFactory.empty() else ConfigFactory.parseString(source)
    config.withFallback(ConfigFactory.load())
  }

  def actorSystemWithConfig(testConfigSource: String = "", name: String = "Specs2TestkitBridge"): ActorSystem = ActorSystem(name, testConfig(testConfigSource))

  abstract class AkkaTestkitSpecification(actorSystem: ActorSystem = actorSystemWithConfig())
       extends TestKit(actorSystem)
          with ImplicitSender
          with SpecificationLike {
    import org.specs2.specification._

    override def map(fs: => Fragments) = super.map(fs).add(Step(system.shutdown()))
  }


  abstract class AkkaTestkitContext(actorSystem: ActorSystem) extends TestKit(actorSystem) with ImplicitSender with After {
    private var owner = false

    def this() = {
      this(actorSystemWithConfig(name = "Specs2TestkitBridgeContext"))
      owner = true
    }

    def this(testConfigSource: String = "") = {
      this(actorSystemWithConfig(testConfigSource, name = "Specs2TestkitBridgeContext"))
      owner = true
    }

    def after {
      if (owner) system.shutdown()
    }
  }


  // ==========================================================================
  // Convenience methods for testing with Specs2
  // ==========================================================================

  trait Specs2UtilityMethods extends SpecificationLike {

    /** Add verification and extraction methods to the Seq[AnyRef] that is returned by receiveN of the AkkaTestkit */
    implicit class SeqTestOps(seq: Seq[AnyRef]) {
      import scala.reflect.ClassTag
      import org.specs2.execute._

      def mustContainType[S : ClassTag]: Result =  {
        seq must contain { elt: AnyRef =>
          elt must beAnInstanceOf[S]
        }
      }

      def extractType[S: ClassTag]: S = {
        mustContainType[S]
        seq.collectFirst { case elt: S => elt }.get
      }

      def mustContain[S : ClassTag](verify: S => Result): Result = {
        seq must contain { elt: AnyRef =>
          elt must beAnInstanceOf[S]
          verify(elt.asInstanceOf[S])
        }
      }
    }
  }
}

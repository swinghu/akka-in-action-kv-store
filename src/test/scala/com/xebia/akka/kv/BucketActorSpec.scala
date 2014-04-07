package com.xebia.akka.kv

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions

import akka.actor._
import akka.testkit._

import TestSupport._
import KeyValueStoreEvents._
import BucketMessages._


class BucketActorSpec extends Specification with NoTimeConversions {

  "The BucketActor" should {
    "Support storing and then getting a String value using a String key" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props(classOf[BucketActor], "bucket"))

      actor ! BucketPut("key", "value")
      actor ! BucketGet("key")

      expectMsgType[Option[Value]] must beSome(Value("value"))
    }

    "Respond with None when getting a key that does not exists" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props(classOf[BucketActor], "bucket"))

      actor ! BucketGet("key")

      expectMsgType[Option[Value]] must beNone
    }

    "Produce a ValueStored event when a value is stored" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props(classOf[BucketActor], "bucket"))

      system.eventStream.subscribe(testActor, classOf[ValueStored])

      actor ! BucketPut("key", "value")

      expectMsgType[ValueStored] must beEqualTo(ValueStored("bucket", "key", "value"))
    }

    "Support storing multiple values for a key and getting only the last value back" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props(classOf[BucketActor], "bucket"))

      actor ! BucketPut("key", "value1")
      actor ! BucketPut("key", "value2")
      actor ! BucketPut("key", "value3")
      actor ! BucketGet("key")

      expectMsgType[Option[Value]] must beSome(Value("value3"))
    }
  }

}

package com.xebia.akka.kv

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions

import akka.actor._
import akka.testkit._

import TestSupport._
import KeyValueStoreMessages._
import KeyValueStoreEvents._


class KeyValueStoreActorSpec extends Specification with NoTimeConversions {

  "The KeyValueStoreActor" should {
    "Support storing and then getting a String value using a String key" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Put("bucket", "key", "value")
      actor ! Get("bucket", "key")

      expectMsgType[Option[Value]] must beSome(Value("value"))
    }

    "Respond with None when getting a key that does not exists" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Get("bucket", "key")

      expectMsgType[Option[Value]] must beNone
    }

    "Produce a ValueStored event when a value is stored" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      system.eventStream.subscribe(testActor, classOf[ValueStored])

      actor ! Put("bucket", "key", "value")

      expectMsgType[ValueStored] must beEqualTo(ValueStored("bucket", "key", "value"))
    }

    "Support storing multiple values for a key and getting only the last value back" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props(classOf[KeyValueStoreActor]))

      actor ! Put("bucket", "key", "value1")
      actor ! Put("bucket", "key", "value2")
      actor ! Put("bucket", "key", "value3")
      actor ! Get("bucket", "key")

      expectMsgType[Option[Value]] must beSome(Value("value3"))
    }

    "Support putting the same key in different buckets" in new AkkaTestkitContext {
      val actor = system.actorOf(Props(classOf[KeyValueStoreActor]))

      actor ! Put("bucket1", "key", "value1")
      actor ! Put("bucket2", "key", "value2")

      actor ! Get("bucket1", "key")

      expectMsgType[Option[Value]] must beSome(Value("value1"))

      actor ! Get("bucket2", "key")

      expectMsgType[Option[Value]] must beSome(Value("value2"))
    }

    "Be able to produce a set of current buckets" in new AkkaTestkitContext {
      val actor = system.actorOf(Props(classOf[KeyValueStoreActor]))

      actor ! Put("bucket1", "key", "value1")
      actor ! Put("bucket2", "key", "value2")

      actor ! ListBuckets

      expectMsgType[Set[String]] === Set("bucket1", "bucket2")
    }
  }

}

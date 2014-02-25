package com.xebia.akka.kv
package store

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions

import akka.actor._
import akka.testkit._

import TestSupport._
import KeyValueStoreMessages._


class KeyValueStoreActorSpec extends Specification with NoTimeConversions {

  "The KeyValueStoreActor" should {
    "Support storing and then getting a String value using a String key" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Put("key", "value")
      actor ! Get("key")

      val v = expectMsgType[Option[Value]]

      v must beSome(Value("value"))
    }

    "Respond with None when getting a key that does not exists" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Get("key")

      val v = expectMsgType[Option[Value]]

      v must beNone
    }

    "Produce a ValueStored event when a value is stored" in new AkkaTestkitContext() {
      system.eventStream.subscribe(testActor, classOf[ValueStored])

      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Put("key", "value")

      val v = expectMsgType[ValueStored]

      v must beEqualTo(ValueStored("key", "value"))
    }
  }

}

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
    "Be able to store a String value using a String key" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Put("key", "value")
      actor ! Get("key")

      val v = expectMsgType[Option[Value]]

      v must beSome(Value("value"))
    }

    "Be able to get a key that does not exists and receive None" in new AkkaTestkitContext() {
      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Get("key")

      val v = expectMsgType[Option[Value]]

      v must beNone
    }

    "Be able to store a String value using a String key and prduce a stored event" in new AkkaTestkitContext() {
      system.eventStream.subscribe(testActor, classOf[ValueStored])

      val actor = system.actorOf(Props[KeyValueStoreActor])

      actor ! Put("key", "value")

      val v = expectMsgType[ValueStored]

      v must beEqualTo(ValueStored("key", "value"))
    }
  }

}

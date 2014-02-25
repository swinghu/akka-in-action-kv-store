package com.xebia.akka.kv
package store

import akka.actor._


object KeyValueStoreMessages {
  case class Put(key: String, value: String)
  case class Get(key: String)
  case class Value(value: String)

  case class ValueStored(key: String, value: String)
}


class KeyValueStoreActor extends Actor with ActorLogging {
  import KeyValueStoreMessages._
  import context._

  private var data = Map.empty[String, String]

  def receive = {
    case Put(key, value) => {
      data += (key -> value)

      system.eventStream.publish(ValueStored(key, value))
    }

    case Get(key) => sender ! data.get(key).map(Value(_))
  }
}

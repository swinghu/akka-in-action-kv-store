package com.xebia.akka.kv

import akka.actor._


object BucketMessages {
  case class BucketPut(key: String, value: String)
  case class BucketGet(key: String)
}


class BucketActor(bucket: String) extends Actor with ActorLogging {
  import KeyValueStoreEvents._
  import BucketMessages._
  import context._

  private var data = Map.empty[String, String]

  def receive = {
    case BucketPut(key, value) => {
      data += (key -> value)

      system.eventStream.publish(ValueStored(bucket, key, value))
    }

    case BucketGet(key) => sender ! data.get(key).map(Value(_))
  }
}

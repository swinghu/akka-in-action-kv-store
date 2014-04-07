package com.xebia.akka.kv

import akka.actor._


case class Value(value: String)


object KeyValueStoreMessages {
  case class Put(bucket: String, key: String, value: String)
  case class Get(bucket: String, key: String)

  case object ListBuckets
}

object KeyValueStoreEvents {
  case class ValueStored(bucket: String, key: String, value: String)
}


class KeyValueStoreActor extends Actor with ActorLogging {
  import KeyValueStoreMessages._
  import BucketMessages._

  def receive = {
    case Put(bucket, key, value) => getOrCreateBucketActor(bucket).forward(BucketPut(key, value))
    case Get(bucket, key)        => getOrCreateBucketActor(bucket).forward(BucketGet(key))
    case ListBuckets             => sender ! listBuckets
  }

  private def getOrCreateBucketActor(bucket: String) = {
    val name = s"${bucket}-${self.path.name}-bucket"
    context.child(name).getOrElse(context.actorOf(Props(classOf[BucketActor], bucket), name))
  }

  private def listBuckets = context.children
                                   .filter(_.path.name.endsWith("-bucket"))
                                   .map(_.path.name.takeWhile(_ != '-'))
                                   .toSet
}

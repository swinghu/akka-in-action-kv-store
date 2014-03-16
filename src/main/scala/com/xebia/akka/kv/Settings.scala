package com.xebia.akka.kv

import com.typesafe.config.Config
import net.ceedubs.ficus.FicusConfig._

import akka.actor._


/** Simple Akka Extension for accesing configuration in an actorsystem-singleton way. */
class Settings(config: Config) extends Extension {
  object Http {
    val Port = config.as[Int]("akka-kv.http.port")
  }
}

/** Akka Extension plumming and extra apply methods for easy access. */
object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config)

  def apply(implicit context: ActorContext): Settings = apply(context.system)
}

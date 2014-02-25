package com.xebia.aka.kv

import com.typesafe.config.Config

import akka.actor._


class Settings(config: Config) extends Extension {

  object Http {
    val Port = config.getInt("akka-kv.http.port")
  }



  private implicit class EnhancedConfig(config: Config) {
    def getOptionalString(path: String): Option[String] = {
      if (config.hasPath(path)) Some(config.getString(path))
      else None
    }
  }
}

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config)

  def apply(implicit context: ActorContext): Settings = apply(context.system)
}

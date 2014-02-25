package com.xebia.akka


package object kv {

  // ==========================================================================
  // Type Aliases
  // ==========================================================================

  type DateTime = org.joda.time.DateTime
  val DateTime = com.github.nscala_time.time.StaticDateTime

  type MediaType = spray.http.MediaType
  val MediaType = spray.http.MediaType
  val MediaTypes = spray.http.MediaTypes

  type Uri = spray.http.Uri
  val Uri = spray.http.Uri

  /** "global" implicit to force all Json serialization to use compact
      printing instead of the default pretty printing. */
  implicit val defaultJsonPrinter = spray.json.CompactPrinter


  // ==========================================================================
  // Misc
  // ==========================================================================

  trait SystemEvent

  class UnrecoverableActorInitException(message: String) extends RuntimeException(message)
}

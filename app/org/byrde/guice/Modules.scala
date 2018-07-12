package org.byrde.guice

import com.google.inject.Inject

import org.byrde.config.ConfigurationProvider
import org.byrde.persistence.{Persistence, Tables}

import scala.concurrent.ExecutionContext

class Modules @Inject()(val configurationProvider: ConfigurationProvider)(implicit ec: ExecutionContext) {
  lazy val persistence: Persistence = new Persistence(this)
  lazy val tables: Tables = new Tables(this)
}
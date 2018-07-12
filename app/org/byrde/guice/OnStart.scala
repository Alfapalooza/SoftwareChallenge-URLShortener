package org.byrde.guice

import com.google.inject.Inject

import scala.concurrent.ExecutionContext

class OnStart @Inject()(modules: Modules)(implicit ec: ExecutionContext) {
  private def start(): Unit =
    modules.persistence.applySchema()

  start()
}
package org.shortener.loggers.impl

import com.google.inject.Inject

import org.shortener.loggers.{Logger, LoggingInformation}

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}

class ErrorLogger @Inject() (actorSystem: ActorSystem) extends Logger {
  override protected val logger: LoggingAdapter =
    Logging(actorSystem, getClass)

  def error[T](throwable: Throwable, elem: T)(implicit loggingInformation: LoggingInformation[(Throwable, T)]): Unit =
    logger.error(loggingInformation.log(throwable.getMessage, throwable -> elem).toString)
}

package controllers.handler

import javax.inject.Singleton

import com.google.inject.Inject

import org.shortener.loggers.impl.ErrorLogger

import models.exceptions.ServiceResponseException

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._

@Singleton
class ErrorHandler @Inject()(errorLogger: ErrorLogger) extends HttpErrorHandler {
  private val ClientError = -1

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val msg =
      if (message.nonEmpty)
        message
      else
        "Unknown Client Side Exception"

    handleError(request, statusCode, new ServiceResponseException(msg, ClientError, statusCode))
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] =
    handleError(request, 500, exception)

  private def handleError(request: RequestHeader, status: Int, error: Throwable): Future[Result] = {
    errorLogger.error(error, request)
    request.headers.get("Accept") match {
      case Some(header) if header.contains("text/html") || header.contains("application/xml") || header.contains("application/xhtml+xml") =>
        error match {
          case ex =>
            Future.successful(Status(status)(views.html.error(ex.getMessage)))
        }
      case _ =>
        error match {
          case ex: ServiceResponseException =>
            Future.successful(ex.toResult)
          case ex =>
            Future.successful(ServiceResponseException(ex).withStatus(status).toResult)
        }
    }
  }
}
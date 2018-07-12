package controllers.handler

import javax.inject.Singleton

import org.byrde.commons.utils.exception.ServiceResponseException
import org.byrde.log.{ErrorLog, LogContext}

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._

@Singleton
class ErrorHandler extends HttpErrorHandler {
  private val CLIENT_ERROR = -1
  
  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val _message = {
      if (message != "")
        message
      else
        "Unknown Client Side Exception"
    }
    val ex = ServiceResponseException.apply(_message, CLIENT_ERROR, statusCode)
    implicit val lc = new LogContext {
      override protected def rh: RequestHeader = request
    }
    handleError(request, statusCode, ex)
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    implicit val lc = new LogContext {
      override protected def rh: RequestHeader = request
    }
    handleError(request, 500, exception)
  }

  private def handleError(request: RequestHeader, status: Int, error: Throwable)(implicit lc: LogContext) = {
    ErrorLog.logException(error)
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
            Future.successful(ServiceResponseException.apply(ex).toResult)
        }
    }
  }
}
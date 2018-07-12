package org.shortener.loggers

import play.api.libs.json.{JsObject, JsString, Json}
import play.api.mvc.RequestHeader

trait LoggingInformation[-T] {
  def log(elem: T): JsObject

  def apply(elem: T): JsObject =
    log(elem)

  def log(msg: String, elem: T): JsObject =
    log(elem) + ("message" -> JsString(msg))

  def log(msg: JsObject, elem: T): JsObject =
    log(elem) + ("message" -> msg)
}

object LoggingInformation {
  implicit val httpRequestInformation: LoggingInformation[RequestHeader] =
    (value: RequestHeader) =>
      Json.obj(
        "header" -> value.headers.toString,
        "cookies" -> value.cookies.toString,
        "domain" -> value.domain,
        "host" -> value.host,
        "path" -> value.path)

  implicit val exceptionWithHttpRequest: LoggingInformation[(Throwable, RequestHeader)] =
    (elem: (Throwable, RequestHeader)) => {
      val (ex, req) =
        elem._1 -> elem._2

      def serializeException(ex: Throwable): JsObject = {
        def loop(throwable: Throwable): JsObject = {
          val causedBy =
            Option(throwable) match {
              case Some(cause) =>
                Json.obj("causedBy" -> loop(cause.getCause))
              case None =>
                Json.obj()
            }
          Json.obj(
            "class" -> ex.getClass.getName(),
            "message" -> ex.getMessage,
            "stackTrace" -> ex.getStackTrace.map(_.toString)
          ) ++ causedBy
        }

        Json.obj(
          "class" -> ex.getClass.getName(),
          "message" -> ex.getMessage,
          "stackTrace" -> ex.getStackTrace.map(_.toString)
        ) ++ loop(ex.getCause)
      }

      httpRequestInformation(req) ++
        Json.obj(
          "message" -> ex.getMessage,
          "exception" -> serializeException(ex)
        )
    }
}
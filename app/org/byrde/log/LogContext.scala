package org.byrde.log

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.RequestHeader

trait LogContext {
  protected def rh: RequestHeader

  def details: JsObject =
    Json.obj(
      "header" -> rh.headers.toString,
      "cookies" -> rh.cookies.toString,
      "domain" -> rh.domain,
      "host" -> rh.host,
      "path" -> rh.path)
}
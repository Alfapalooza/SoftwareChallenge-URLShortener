package controllers.requests

import java.net.URL

import play.api.data._
import play.api.data.Forms._

object URLRequest {
  val form: Form[URL] =
    Form(single("url" -> of[URL](RequestInputFormatter.URLFormatter)))
}
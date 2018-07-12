package controllers.requests

import play.api.data._
import play.api.data.Forms._

case class Login(username: String, password: String)

object Login {
  val form: Form[Login] =
    Form (mapping("username" -> text, "password" -> text)(Login.apply)(Login.unapply))
}
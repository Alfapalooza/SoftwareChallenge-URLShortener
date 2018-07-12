package controllers.requests

import java.net.URL

import play.api.data.FormError
import play.api.data.format.Formatter
import play.api.data.format.Formats._

object RequestInputFormatter {
  object URLFormatter extends Formatter[URL] {
    override val format =
      Some(("format.url", Nil))

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], URL] =
      parsing(new URL(_), "error.url", Nil)(key, data)

    override def unbind(key: String, value: URL) =
      Map(key -> value.toString)
  }
}

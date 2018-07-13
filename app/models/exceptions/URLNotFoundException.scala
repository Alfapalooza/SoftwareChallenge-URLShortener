package models.exceptions

import models.Token

case class URLNotFoundException(token: Token) extends ServiceResponseException(s"URL for token: $token not found", 404, 404)

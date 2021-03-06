package models.responses

import models.exceptions.ServiceResponseException

// Commons codes are between 1 - 1000;
object CommonsServiceResponseDictionary {
  // OK
  object E0200 extends DefaultServiceResponse {
    override def msg: String = "Ok"
    override def status: Int = 200
    override def code: Int   = 200
  }
  // Client errors (1 - 499)
  object E0400 extends ServiceResponseException("Bad Request", 400, 400)
  object E0401 extends ServiceResponseException("Unauthorized", 401, 401)
  object E0403 extends ServiceResponseException("Forbidden", 403, 403)
  object E0404 extends ServiceResponseException("Not Found", 404, 404)
  object E0415 extends ServiceResponseException("Unsupported Media Type", 415, 415)
  // Server errors (500 - 999)
  object E0500 extends ServiceResponseException("Internal Server Error", 500, 500)
  object E0504 extends ServiceResponseException("Service timeout", 504, 504)
}

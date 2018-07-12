package models.exceptions

import models.responses.CommonsServiceResponseDictionary.E0500
import models.responses.DefaultServiceResponse

import scala.util.control.NoStackTrace

class ServiceResponseException(_msg: String, _code: Int, _status: Int)
    extends Throwable(_msg)
    with DefaultServiceResponse
    with NoStackTrace {
  override def apply(message: String): ServiceResponseException =
    new ServiceResponseException(message, _code, _status)

  def apply(throwable: Throwable): ServiceResponseException =
    new ServiceResponseException(throwable.getMessage, _code, _status)

  override def msg: String =
    _msg

  override def status: Int =
    _status

  override def code: Int =
    _code
}

object ServiceResponseException {
  def apply(throwable: Throwable): ServiceResponseException =
    E0500(throwable)
}

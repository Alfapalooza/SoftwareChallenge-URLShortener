package models.exceptions

import org.byrde.commons.utils.exception.ServiceResponseException

// Exception codes start at 1000
object ExceptionDictionary {
  // Client errors (1000 - 1499)
  // Invalid JWT (1001)
  object InvalidJwtException extends ServiceResponseException("Cannot authenticate user", 1001, 400)
  // Invalid input (1050 - 1099)
  object FieldMissingFromRequestException extends ServiceResponseException("Please fill in all fields", 1000, 400)
  object NoAnswerException extends ServiceResponseException("Please select an answer", 1050, 400)
  object NoFileSubmittedException extends ServiceResponseException("Please submit a file", 1051, 400)
  class WrongFileFormatException(actualFormat: String, expectedFormat: String) extends ServiceResponseException(s"Expected file format: $expectedFormat, actual format: $actualFormat", 1052, 400)
  object WrongUsernameOrPasswordException extends ServiceResponseException("Wrong user name or password", 1053, 401)
}

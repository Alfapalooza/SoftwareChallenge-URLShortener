package models

import models.Alert.{Level, Info, Warning, Error}

case class Alert(message: String, private val level: Level) {
  lazy val readableLevel: String =
    level match {
      case Info =>
        "info"
      case Warning =>
        "warning"
      case Error =>
        "danger"
    }
}

object Alert {
  sealed trait Level

  object Info extends Level
  object Warning extends Level
  object Error extends Level
}

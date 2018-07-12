package org.byrde.persistence

import com.google.inject.Inject

import org.mindrot.jbcrypt.BCrypt

import org.byrde.commons.persistence.sql.slick.dao.BaseDAONoStreamA
import org.byrde.guice.Modules

import models.dto.User

import scala.concurrent.{ExecutionContext, Future}


class Persistence @Inject()(applicationModules: Modules)(implicit ec: ExecutionContext) {
  implicit val tables: Tables = applicationModules.tables

  import tables._
  import tables.profile.api._

  lazy val userDAO = new BaseDAONoStreamA[tables.Users, User](UserTQ) {
    def findByUsernameAndPassword(username: String, password: String): Future[Option[User]] =
      findByFilter(u => u.username === username) map { users =>
        users.headOption.filter(u => BCrypt.checkpw(password, u.password))
      }
  }

  def applySchema(): Unit =
    db.run(Seq(UserTQ.schema).reduceLeft(_ ++ _).create)
}

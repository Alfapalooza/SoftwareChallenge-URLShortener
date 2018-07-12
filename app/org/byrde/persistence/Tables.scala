package org.byrde.persistence

import com.google.inject.Inject

import models.dto.User

import org.byrde.commons.persistence.sql.slick.table.TablesA
import org.byrde.guice.Modules

class Tables @Inject()(applicationModules: Modules) extends TablesA(applicationModules.configurationProvider.jdbcConfiguration) {
  import profile.api._

  class Users(_tableTag: Tag) extends BaseTableA[User](_tableTag, "users") {
    def * = (id, username, password) <> (User.tupled, User.unapply)

    val username: Rep[String] = column[String]("username", O.Length(255, varying = true))
    val password: Rep[String] = column[String]("password", O.Length(255, varying = true))
  }

  lazy val UserTQ = new TableQuery(tag => new Users(tag))
}
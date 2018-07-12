package models.dto

import org.byrde.commons.persistence.sql.slick.sqlbase.BaseEntity

case class User(id: Long, username: String, password: String) extends BaseEntity


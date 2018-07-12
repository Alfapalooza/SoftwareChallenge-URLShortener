package org.byrde.config

import com.google.inject.Inject

import org.byrde.commons.utils.auth.conf.JwtConfig

import play.api.Configuration

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class ConfigurationProvider @Inject()(configuration: Configuration) {
  lazy val jdbcConfiguration: DatabaseConfig[JdbcProfile] =
    DatabaseConfig.forConfig("db")

  lazy val jwtConfiguration: JwtConfig =
    JwtConfig.apply(configuration.get[Configuration]("jwt-config.client"))
}
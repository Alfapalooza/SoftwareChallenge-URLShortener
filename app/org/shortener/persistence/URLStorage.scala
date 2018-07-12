package org.shortener.persistence

import java.net.URL

import models.Token

import scala.concurrent.{ExecutionContext, Future}

trait URLStorage {
  def upsert(url: URL)(implicit ec: ExecutionContext): Future[Token]

  def fetch(token: Token)(implicit ec: ExecutionContext): Future[URL]
}

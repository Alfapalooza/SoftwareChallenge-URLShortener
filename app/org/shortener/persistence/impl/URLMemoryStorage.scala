package org.shortener.persistence.impl

import java.net.URL
import java.util.UUID

import org.apache.commons.collections4.bidimap.DualHashBidiMap
import org.shortener.persistence.URLStorage

import models.Token
import models.exceptions.URLNotFoundException

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}

class URLMemoryStorage extends URLStorage {
  private lazy val urls =
    new DualHashBidiMap[Token, URL]

  override def upsert(url: URL)(implicit ec: ExecutionContext): Future[Token] =
    Future(Option(urls.getKey(url)).getOrElse(handleUpdate(url)))

  override def fetch(token: Token)(implicit ec: ExecutionContext): Future[URL] =
    Future(Option(urls.get(token)).getOrElse(throw URLNotFoundException(token)))

  protected def handleUpdate(url: URL): Token =
    synchronized {
      @tailrec
      def generateAvailableToken: Token = {
        val newToken =
          UUID.randomUUID.toString.split("-").head

        if (Option(urls.get(newToken)).isDefined)
          generateAvailableToken
        else
          newToken
      }

      val newToken =
        generateAvailableToken

      urls
        .put(newToken, url)

      newToken
    }
}

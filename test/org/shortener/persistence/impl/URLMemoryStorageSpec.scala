package org.shortener.persistence.impl

import java.net.URL
import java.util.concurrent.Executors

import org.specs2.mutable.Specification

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class URLMemoryStorageSpec extends Specification {
  implicit val _: ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

  "URLMemoryStorage should" >> {
    "Generate different tokens for different URLs" >> {
      val storage =
        new URLMemoryStorage

      val token1 =
        Await.result(storage.upsert(new URL("https://apple.com")), 1.second)

      val token2 =
        Await.result(storage.upsert(new URL("https://not-apple.com")), 1.second)

      token1 mustNotEqual token2
    }

    "Generate the same token for the same URLs" >> {
      val storage =
        new URLMemoryStorage

      val token1 =
        Await.result(storage.upsert(new URL("https://apple.com")), 1.second)

      val token2 =
        Await.result(storage.upsert(new URL("https://apple.com")), 1.second)

      token1 mustEqual token2
    }
  }
}

package controllers

import javax.inject.{Inject, Singleton}

import org.shortener.persistence.URLStorage
import org.shortener.utils.ThreadPools

import models.Token

import play.api.mvc.{Action, AnyContent, InjectedController}

import scala.concurrent.ExecutionContext

@Singleton
class RedirectController @Inject()(urlStore: URLStorage) extends InjectedController {
  private implicit val ec: ExecutionContext =
    ThreadPools.Global

  def redirect(token: Token): Action[AnyContent] =
    Action.async(urlStore.fetch(token).map(url => Redirect(url.toString)))
}
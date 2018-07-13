package controllers

import javax.inject._

import org.shortener.persistence.URLStorage
import org.shortener.utils.ThreadPools

import controllers.requests.URLRequest
import models.{Alert, Token}

import play.api.Configuration
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(urlStore: URLStorage, configuration: Configuration) extends InjectedController {
  private implicit val ec: ExecutionContext =
    ThreadPools.Global

  private val host: String =
    configuration.get[String]("host")

  def index: Action[AnyContent] =
    Action(parse.empty)(Ok(views.html.index(None)))

  def shorten: Action[AnyContent] =
    Action.async { implicit request =>
      URLRequest
        .form
        .bindFromRequest
        .fold({ binding =>
          val alert =
            Alert(s"Invalid URL", Alert.Error)

          Future.successful(Ok(views.html.index(Some(alert))))
        }, { url =>
          urlStore
            .upsert(url)(ThreadPools.Storage)
            .map { token =>
              Ok(views.html.shortened(token, buildURL(token), url.toString)(None))
            }
        })
    }

  private def buildURL(token: Token): String =
    s"$host/$token"
}

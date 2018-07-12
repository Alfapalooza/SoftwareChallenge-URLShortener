package controllers

import io.igl.jwt.Sub

import javax.inject.{Inject, Singleton}

import org.byrde.commons.utils.auth.JsonWebTokenWrapper
import org.byrde.commons.utils.auth.conf.JwtConfig
import org.byrde.guice.Modules

import controllers.requests.Login
import models.exceptions.ExceptionDictionary.{FieldMissingFromRequestException, WrongUsernameOrPasswordException}

import play.api.cache.Cached
import play.api.mvc._

import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests relating to Authentication.
  */
@Singleton
class AuthenticationController @Inject()(cached: Cached,
                                         parser: BodyParsers.Default,
                                         actorSystem: akka.actor.ActorSystem,
                                         applicationModules: Modules)(implicit mat: Materializer) extends InjectedController {
  private implicit val ec: ExecutionContext =
    actorSystem.dispatchers.lookup("controller-context")

  val jwtConfig: JwtConfig =
    applicationModules.configurationProvider.jwtConfiguration

  /**
    * Create an Action to render an HTML login page.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/login`.
    */
  def login(originOpt: Option[String]): EssentialAction =
    Action {
      Ok(views.html.login(originOpt))
    }

  def authenticate(originOpt: Option[String]): Action[AnyContent] =
    Action.async { implicit request =>
      val redirect =
        originOpt.fold(routes.HomeController.index().absoluteURL(secure = true))(origin => s"https://${request.host}$origin")

      Login.form.bindFromRequest.value.fold(Future.successful(FieldMissingFromRequestException.toResult)) { login =>
        applicationModules.persistence.userDAO.findByUsernameAndPassword(login.username, login.password).map {
          case Some(user) =>
            val claims = Seq(Sub(user.id.toString))
            val salt = request.headers.get("Client-IP").orElse(request.headers.get("X-Forwarded-For")).getOrElse(request.remoteAddress)
            val jwt = JsonWebTokenWrapper(jwtConfig.copy(saltOpt = Some(salt))).encode(claims)
            Redirect(redirect).withSession(jwtConfig.tokenName -> jwt)
          case _ =>
            WrongUsernameOrPasswordException.toResult
        }
      }
    }
}

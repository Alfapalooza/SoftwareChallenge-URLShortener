package controllers

import javax.inject.Inject

import org.byrde.commons.controllers.actions.auth.AuthorizedAction
import org.byrde.commons.utils.auth.conf.JwtConfig
import org.byrde.guice.Modules

import play.api.mvc._
import controllers.Assets.Asset

import scala.concurrent.ExecutionContext

/**
  * Created by martin.allaire on 2017.
  */
class SecuredAssets @Inject()(parser: BodyParsers.Default,
                              assets: Assets,
                              actorSystem: akka.actor.ActorSystem,
                              applicationModules: Modules) extends InjectedController {
  private implicit val ec: ExecutionContext =
    actorSystem.dispatchers.lookup("controller-context")

  val jwtConfig: JwtConfig =
    applicationModules.configurationProvider.jwtConfiguration

  def failSafe(request: Request[_]): Call =
    routes.AuthenticationController.login(Some(request.path))

  def versioned(path: String, file: Asset): Action[AnyContent] =
    authenticated(assets.versioned(path, file))

  private def authenticated(action: Action[AnyContent]) =
    AuthorizedAction(parser, jwtConfig, failSafe).async(parse.default) { implicit request =>
      action(request)
    }
}

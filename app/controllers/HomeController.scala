package controllers

import javax.inject._

import org.byrde.commons.controllers.actions.auth.AuthorizedAction
import org.byrde.commons.utils.auth.conf.JwtConfig
import org.byrde.guice.Modules

import play.api.cache.Cached
import play.api.mvc._

import akka.stream.Materializer

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cached: Cached,
                               parser: BodyParsers.Default,
                               actorSystem : akka.actor.ActorSystem,
                               applicationModules: Modules)(implicit mat: Materializer) extends InjectedController {
  private implicit val ec: ExecutionContext =
    actorSystem.dispatchers.lookup("controller-context")

  val jwtConfig: JwtConfig =
    applicationModules.configurationProvider.jwtConfiguration

  def failSafe(request: Request[_]): Call =
    routes.AuthenticationController.login(Some(request.path))

  /**
    * Create an Action to render an HTML admin landing page.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`, `/index`, `/index.html`.
    */
  def index: Action[AnyContent] =
    AuthorizedAction(parser, jwtConfig, failSafe)(ec) {
      Ok(views.html.index())
    }
}

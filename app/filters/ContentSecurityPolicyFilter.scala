package filters

import com.google.inject.Inject

import play.api.mvc.{Filter, RequestHeader, Result}

import akka.stream.Materializer

import scala.concurrent.Future

class ContentSecurityPolicyFilter @Inject()()(implicit val mat: Materializer) extends Filter {
  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    nextFilter(requestHeader).map { result =>
      result
        .withHeaders(
          "Content-Security-Policy" ->
            "default-src 'none'; style-src https://maxcdn.bootstrapcdn.com 'self';script-src https://code.jquery.com https://maxcdn.bootstrapcdn.com 'self'; font-src https://maxcdn.bootstrapcdn.com 'self'"
        )
    }(mat.executionContext)
  }
}
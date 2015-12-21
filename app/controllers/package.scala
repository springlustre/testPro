import javax.inject.{Inject, Singleton}

import models.Tables.UserRow
import models.User
import org.slf4j.LoggerFactory
import play.api.mvc._

import scala.concurrent.Future

/**
 * Created by Liboren's on 2015/8/31.
 */
package object controllers {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext


  @Singleton
  class LoggingAction @Inject() extends ActionBuilder[Request] {
    val log = LoggerFactory.getLogger(this.getClass)
    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
      log.info(s"access log: ${request.uri} query: ${request.rawQueryString}" )
      block(request)
    }
  }

  class UserRequest[A](val user: UserRow, request: Request[A]) extends WrappedRequest[A](request)



  object SessionKey {
    val userId = "id"
    val timestamp = "ts"
    val ip = "ip"
  }


  @Singleton
  case class ActionUtils @Inject()(
    LoggingAction: LoggingAction
    ) {


  }

}

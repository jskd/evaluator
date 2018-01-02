package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import scala.util.Try

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { request =>

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.index(user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.index())
      }
    }.getOrElse {
      Ok(views.html.index())
      //Unauthorized("Oops, you are not connected")
    }

  }
}

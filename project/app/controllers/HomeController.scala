package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index())
  }
}

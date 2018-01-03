package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._
import models._

/**
 * This controller creates an `Action` to handle HTTP requests to "questions"
 */
@Singleton
class CoursController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def create_new() = Action{
    DBQuestion.applySeeder()
    var nowid = DBQuestion.dbmaps.size
    Ok(views.html.create_cours(nowid))
  }

  def add_cours() = Action{

    /* TODO : Populate DB with new course */
    
    Redirect(routes.HomeController.index())
  }
}

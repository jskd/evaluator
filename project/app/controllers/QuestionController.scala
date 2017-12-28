package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._

import models._

/**
 * This controller creates an `Action` to handle HTTP requests to "questions"
 */
@Singleton
class QuestionController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { 
    DBQuestion.applySeeder()
    Ok(views.html.questionindex(DBQuestion.getAll()))
  }

  def getAll = Action {
    DBQuestion.applySeeder()
    Ok(views.html.questionindex(DBQuestion.getAll()))
  }

  def getById(id:Int) = Action{
    DBQuestion.applySeeder()
    val question = DBQuestion.getById(id)
    Ok(views.html.onequestion(question))
  }

  def delete(id:Int) = Action{
    DBQuestion.delete(id)
    Redirect(routes.QuestionController.index())
  }
}

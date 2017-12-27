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
    Ok("index test")
  }

  def getAll = Action {
    val testQnaire = new Questionnaire(1,0);
    ListDB.applySeeder()
    testQnaire.addQuestion(ListDB.getAll())
    Ok(views.html.questionindex(testQnaire))
  }

  def getById(id:Int) = Action{
    ListDB.applySeeder()
    val question = ListDB.getById(id)
    Ok(views.html.onequestion(question))
  }
}

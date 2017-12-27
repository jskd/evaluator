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
    val qcm1 = new QCMQuestion(1,"What langue do we learn?","A",List("A.scala ","B.java ","C.c# ","D.python ","E.C "))
    var qcm2 = new QCMQuestion(2,"20 - 5 = ?","CD",List("A.12 ","B.10 ","C.5*3 ","D.15 "))
    var fill1 = new FillQuestion(3,"Where are you?","France")
    var ifill2 = new ImageFillQuestion(4,"Combien de relation voit-on sur le diagramme?","4","ad4")

    var ifill3 = new ImageFillQuestionContains(5,"A quoi correspond cette icône?","jointure","ad1")
    var ifill4 = new ImageFillQuestionContains(6,"Que signifie ce diagramme?","nestedloop","ad3")
    var ifill5 = new ImageFillQuestionContains(7,"Qu'évoque cette figure?","sémantique","ad2")
    testQnaire.addQuestion(qcm1)
    testQnaire.addQuestion(qcm2)
    testQnaire.addQuestion(fill1)
    testQnaire.addQuestion(ifill2)
    testQnaire.addQuestion(ifill3)
    testQnaire.addQuestion(ifill4)
    testQnaire.addQuestion(ifill5)
    Ok(views.html.questionindex(testQnaire))
  }
}

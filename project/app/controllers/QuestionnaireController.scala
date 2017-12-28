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
class QuestionnaireController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { 
    DBQuestionnaire.applySeeder()
    DBQuestion.applySeeder()
    var qlist = DBQuestionnaire.getAll()
    for(ql <- qlist){
        ql.questions = Nil
        for(id <- ql.qid){
            if(DBQuestion.has(id))
                ql.addQuestion(DBQuestion.getById(id))
        }
    }
    Ok(views.html.questionnaireindex(qlist))
  }

  def getById(id:Int) = Action{
    DBQuestionnaire.applySeeder()
    DBQuestion.applySeeder()
    var qnaire = DBQuestionnaire.getById(id)
    qnaire.questions = Nil
    for(id <- qnaire.qid){
        if(DBQuestion.has(id))
            qnaire.addQuestion(DBQuestion.getById(id))
    }
    Ok(views.html.onequestionnaire(qnaire))
  }

  def delete(qnid:Int,qid:Int) = Action{
    var qnaire = DBQuestionnaire.getById(qnid)
    var qidlst:List[Int] = Nil
    for(id <- qnaire.qid){
        if(id != qid)
            qidlst = qidlst :+ id
    }
    qnaire.qid = qidlst
    Redirect(routes.QuestionnaireController.index())
  }

  def add = Action {implicit request =>

    val addForm = Form(
      tuple(
        "qnid" -> text,
        "qid" -> text,
      )
    )
    val (qnid, qid) = addForm.bindFromRequest.get
    var qnaire = DBQuestionnaire.getById(qnid.toInt)
    qnaire.addqid(qid.toInt)
    Redirect(routes.QuestionnaireController.index())
  }
}

package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._
import models._
import scala.util.Try

/**
 * This controller creates an `Action` to handle HTTP requests to "questions"
 */
@Singleton
class QuestionnaireController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { request =>
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

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.questionnaireindex(qlist, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.questionnaireindex(qlist, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }

  }

  def getById(id:Int) = Action{ request =>
    DBQuestionnaire.applySeeder()
    DBQuestion.applySeeder()
    var qnaire = DBQuestionnaire.getById(id)
    qnaire.questions = Nil
    for(id <- qnaire.qid){
        if(DBQuestion.has(id))
            qnaire.addQuestion(DBQuestion.getById(id))
    }

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.onequestionnaire(qnaire, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.onequestionnaire(qnaire, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
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

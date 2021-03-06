package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._
import models._
import java.io.File
import scala.util.Try
/**
 * This controller creates an `Action` to handle HTTP requests to "questions"
 */
@Singleton
class QuestionController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { request =>
    DBQuestion.applySeeder()

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.questionindex(DBQuestion.getAll(), user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.questionindex(DBQuestion.getAll(), user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def getAll = Action { request =>
    DBQuestion.applySeeder()

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.questionindex(DBQuestion.getAll(), user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.questionindex(DBQuestion.getAll(), user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def getById(id:Int) = Action{ request =>
    DBQuestion.applySeeder()
    val question = DBQuestion.getById(id)

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.onequestion(question, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.onequestion(question, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def delete(id:Int) = Action{
    DBQuestion.delete(id)
    Redirect(routes.QuestionnaireController.index())
  }

  def addpage = Action { request =>
    DBQuestion.applySeeder()
    var nowid = DBQuestion.dbmaps.size + 1

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.questionaddpage(nowid, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.questionaddpage(nowid, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def addqcm = Action {implicit request =>

    val addForm = Form(
      tuple(
        "qid" -> text,
        "title" -> text,
        "op1" -> text,
        "op2" -> text,
        "op3" -> text,
        "op4" -> text,
        "op5" -> text,
        "op6" -> text,
        "answer" -> text,
      )
    )
    val (qid,title,op1,op2,op3,op4,op5,op6,answer) = addForm.bindFromRequest.get
    val addquestion = new QCMQuestion(qid.toInt,title,answer,List("A."+op1,"B."+op2,"C."+op3,"D."+op4,"E."+op5,"F."+op6))
    DBQuestion.add(qid.toInt,addquestion)
    Redirect(routes.QuestionController.index())
  }

  def addfill = Action {implicit request =>

    val addForm = Form(
      tuple(
        "qid" -> text,
        "title" -> text,
        "answer" -> text,
      )
    )
    val (qid,title,answer) = addForm.bindFromRequest.get
    val addquestion = new FillQuestion(qid.toInt,title,answer)
    DBQuestion.add(qid.toInt,addquestion)
    Redirect(routes.QuestionController.index())
  }

  def addimage = Action(parse.multipartFormData) {implicit request =>

    val addForm = Form(
      tuple(
        "qid" -> text,
        "title" -> text,
        "answer" -> text,
      )
    )
    val (qid,title,answer) = addForm.bindFromRequest.get

    request.body.file("pic").map { picture =>
      var name = "public/images/pic"+answer+qid+".png"
      picture.ref.moveTo(new File(name))
    }.getOrElse {
      Redirect(routes.QuestionController.index())
    }

    val addquestion = new ImageFillQuestion(qid.toInt,title,answer,"pic"+answer+qid)
    DBQuestion.add(qid.toInt,addquestion)
    Redirect(routes.QuestionController.index())
  }
}

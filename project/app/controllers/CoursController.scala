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
class CoursController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def create_new() = Action{ request =>
    DBCours.applySeeder()
    var nowid = DBCours.dbmaps.size + 1


    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.create_cours(nowid, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.create_cours(nowid, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def my_courses() = Action{ request =>
    DBQuestionnaire.applySeeder()
    DBQuestion.applySeeder()
    DBCours.applySeeder()

    var qlist = DBCours.getAll()

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.my_courses(qlist, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.my_courses(qlist, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def add_cours() = Action{implicit request =>
    val addForm = Form(
      tuple(
        "cid" -> text,
        "title" -> text,
      )
    )
    val (cid,title) = addForm.bindFromRequest.get
    val c = new Cours(cid.toInt,title)
    DBCours.add(cid.toInt,c)
    Redirect(routes.HomeController.index())
  }

  def getById(id:Int) = Action{ request =>
    DBQuestionnaire.applySeeder()
    var c = DBCours.getById(id)
    c.qnaire = Nil
    for(id <- c.qnaireid){
        if(DBQuestionnaire.has(id))
            c.addQnaire(DBQuestionnaire.getById(id))
    }

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.onecours(c, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.onecours(c, user))
      }
    }.getOrElse {
      Redirect(routes.HomeController.index())
      //Unauthorized("Oops, you are not connected")
    }
  }

  def add = Action {implicit request =>

    val addForm = Form(
      tuple(
        "cid" -> text,
        "qid" -> text,
      )
    )
    val (cid, qid) = addForm.bindFromRequest.get
    var c = DBCours.getById(cid.toInt)
    c.addqid(qid.toInt)
    Redirect(routes.CoursController.getById(cid.toInt))
  }

  def delete(cid:Int,qid:Int) = Action{
    var c = DBCours.getById(cid)
    var qidlst:List[Int] = Nil
    for(id <- c.qnaireid){
        if(id != qid)
            qidlst = qidlst :+ id
    }
    c.qnaireid = qidlst
    Redirect(routes.CoursController.getById(cid.toInt))
  }
}

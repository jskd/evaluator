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
    DBCours.applySeeder()
    var nowid = DBCours.dbmaps.size + 1
    Ok(views.html.create_cours(nowid))
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

  def getById(id:Int) = Action{
    DBQuestionnaire.applySeeder()
    var c = DBCours.getById(id)
    c.qnaire = Nil
    for(id <- c.qnaireid){
        if(DBQuestionnaire.has(id))
            c.addQnaire(DBQuestionnaire.getById(id))
    }
    Ok(views.html.onecours(c))
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

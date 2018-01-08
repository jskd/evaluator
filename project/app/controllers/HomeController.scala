package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import scala.util.Try
import models._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def index = Action { request =>

    DBQuestionnaire.applySeeder()
    DBQuestion.applySeeder()
    DBCours.applySeeder()
    /**
    var qlist = DBQuestionnaire.getAll()
    for(ql <- qlist){
        ql.questions = Nil
        for(id <- ql.qid){
            if(DBQuestion.has(id))
                ql.addQuestion(DBQuestion.getById(id))
        }
    }
    */
    var qlist = DBCours.getAll()

    request.session.get("connected").map { user =>
      request.session.get("admin").map { admin =>
        Ok(views.html.index(qlist, user, Try(admin.toBoolean).getOrElse(false)))
      }.getOrElse {
        Ok(views.html.index(qlist))
      }
    }.getOrElse {
      Ok(views.html.index(qlist))
      //Unauthorized("Oops, you are not connected")
    }

  }

  def add(qid:Int) = Action {implicit request =>
    Redirect(routes.HomeController.index())
  }

  def delete(qid:Int) = Action{

    Redirect(routes.HomeController.index())
  }
}

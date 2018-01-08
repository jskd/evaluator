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

    // C'EST PAS POSSIBLE DE CONCEVOIR LES CHOSES COMME CA...
    // LES COURS NE SONT PAS DANS LA BDD
    var qlist = DBCours.getAll()

    // Solution provisoir


    // TODO: A suppr
    var user_id = 1
    if(request.session.get("speudo") == "user")
      user_id = 2

    qlist.foreach( question =>

      db.withConnection { conn =>
        val stmt = conn.createStatement
        val rs = stmt.executeQuery("SELECT * FROM inscription WHERE id_user = '"
          + user_id + "' and id_cour = '" + question.id + "';")

        if(rs.next()){
          question.inscrit = true
        }
        else
        {
          question.inscrit = false

        }
      }
)

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

    db.withConnection { conn =>

      // TODO: A suppr
      var user_id = 1
      if(request.session.get("speudo") == "user")
        user_id = 2

      val stmt = conn.createStatement
      val rs = stmt.execute("INSERT INTO inscription (id_user, id_cour) VALUES (  " + user_id +", "+ qid +");")

      Redirect(routes.HomeController.index())
    }
  }

  def delete(qid:Int) = Action{implicit request =>

    db.withConnection { conn =>

      // TODO: A suppr
      var user_id = 1
      if(request.session.get("speudo") == "user")
        user_id = 2

      val stmt = conn.createStatement
      val rs = stmt.execute("DELETE FROM inscription WHERE id_user= '"+user_id+ "' and id_cour ='"+qid+ "';")

      Redirect(routes.HomeController.index())
    }
  }
}

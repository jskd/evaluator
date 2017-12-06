package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  /*
  db.withConnection { conn =>
     val stmt = conn.createStatement
     val rs = stmt.execute("INSERT INTO users (pseudo, password) VALUES ('TEST', 'PASS');")
   }

  var resultat = ""
  db.withConnection { conn =>
    val stmt = conn.createStatement
    val rs = stmt.executeQuery("SELECT * FROM users;")
    rs.next()
    resultat += rs.getString("pseudo")
  }
  */

  def index = Action {
    Ok(views.html.index())
  }
}

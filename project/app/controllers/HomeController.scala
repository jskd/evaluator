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

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  db.withConnection { conn =>
     val stmt = conn.createStatement
     val rs = stmt.execute("INSERT INTO users (pseudo, password) VALUES ('TEST', 'PASS');")
   }

  var resultat = ""
  db.withConnection { conn =>
    val stmt = conn.createStatement
    val rs = stmt.executeQuery("SELECT * FROM users;")

    while (rs.next()) {
      resultat += rs.getString("pseudo")
    }
  }

  def index = Action {
    Ok(views.html.index(resultat))
  }
}

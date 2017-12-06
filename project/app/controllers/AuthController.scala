package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._

@Singleton
class AuthController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {


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

  val userForm = Form(
    tuple(
      "pseudo" -> text,
      "password" -> text
    )
  )

  def signUp = Action {implicit request =>
    val (pseudo, password) = userForm.bindFromRequest.get
    var message = "";

    if(pseudo != "" && password != ""){
      message = "Félicitations " + pseudo + ", vous êtes inscrit !"
    }
    else{
      message = "Erreur lors de l'inscription."
    }

    Ok(views.html.main("Inscription"){views.html.signup(message)})
  }
}

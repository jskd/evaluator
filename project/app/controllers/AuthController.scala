package controllers

import javax.inject._
import play.api.mvc._
import play.api.db._
import play.api.data._
import play.api.data.Forms._

@Singleton
class AuthController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {

  def signUp = Action {implicit request =>
    val inscriptionForm = Form(
      tuple(
        "pseudo" -> text,
        "password" -> text,
        "usertype" -> text
      )
    )

    val (pseudo, password, usertype) = inscriptionForm.bindFromRequest.get
    var admin = 0;
    var message = "";

    if(pseudo != "" && password != ""){
      if(usertype == "admin") admin = 1

      try{
        db.withConnection { conn =>
           val stmt = conn.createStatement
           val rs = stmt.execute("INSERT INTO users (pseudo, password, admin) VALUES ('"
              + pseudo + "', '" + password + "', '" + admin + "');")

           message = "Félicitations " + pseudo + " (" + admin + "), vous êtes inscrit !"
        }
      }catch{
        case e: Exception => message = "Erreur: Le pseudo existe déjà.";
      }
    }
    else{
      message = "Erreur: Vous devez remplir les champs."
    }
    Ok(views.html.main("Inscription"){views.html.signup(message)})
  }

  def signIn = Action {implicit request =>

    val connexionForm = Form(
      tuple(
        "pseudo" -> text,
        "password" -> text,
      )
    )

    val (pseudo, password) = connexionForm.bindFromRequest.get
    var message = "";

    if(pseudo != "" && password != ""){
      var resultat = ""

      db.withConnection { conn =>
        val stmt = conn.createStatement
        val rs = stmt.executeQuery("SELECT * FROM users WHERE pseudo = '"
          + pseudo + "' and password = '" + password + "';")

        if(rs.next()){
          message = "Bonjour " + pseudo + ", vous êtes connecté !"
          val id= rs.getString(1);
          Redirect(routes.HomeController.index()).withSession("id" -> id, "connected" -> pseudo, "admin" -> rs.getBoolean("admin").toString)
          //Ok(views.html.index()).withSession("connected" -> pseudo)
        }
        else{
          message = "L'utilisateur " + pseudo + " n'existe pas."
          Ok(views.html.main("Connexion"){views.html.signin(message)})
        }
      }
    }
    else{
      message = "Erreur: Vous devez remplir les champs."
      Ok(views.html.main("Connexion"){views.html.signin(message)})
    }
  }

  def logout = Action {implicit request =>
    Redirect(routes.HomeController.index()).withSession()
  }
}

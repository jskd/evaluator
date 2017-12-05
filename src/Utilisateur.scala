class ListeUtilisateurs(){
  var listeUsers : List[Utilisateur] = Nil
  def addUser(user:Utilisateur) = listeUsers = listeUsers :+ user
  def checkUSer(user:String) : Boolean = {
    for(elem <- listeUsers if elem.pseudo.equals(user))
      return true
    return false
  }
  def getUserByPseudo(user:String) : Utilisateur = {
    for(elem <- listeUsers if elem.pseudo.equals(user))
      return elem
    return null
  }
}

abstract class Utilisateur(_id:Int, _pseudo:String){
  var id = _id
  var pseudo = _pseudo
  def getPseudo() : String = return this.pseudo
}


class Administrateur(_id:Int, _pseudo:String)
extends Utilisateur(_id,_pseudo){

}

class Client(_id:Int, _pseudo:String)
extends Utilisateur(_id,_pseudo){

}

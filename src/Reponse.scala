abstract class Reponse(_choix:String){
	val reponse = _choix
	def reponseToString():String = reponse
}

class CommonReponse(_choix:String) extends Reponse(_choix){
	// 
}
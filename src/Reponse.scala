abstract class Reponse{
	def reponseToString():String
}

class QCMReponse(choix:String) extends Reponse{
	val reponse = choix
	override def reponseToString():String = reponse
}
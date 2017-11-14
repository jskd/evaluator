abstract class Question(_id:Int,_intitule:String,_answer:String){
	var id = _id
	var intitule = _intitule
	var answer = _answer
	def display = printf(intitule+"\n")
}

trait ImageQ extends Question{
	var image_path: String
	override def display = {
		super.display
		//TODO afficher l'image
		printf("image afficher: "+ image_path+"\n") 
	}
}

trait OptionsQ extends Question{
	var options: List[String] = Nil
	def addOption(o:String) = options = options :+ o
	override def display = {
		super.display
		options.foreach{printf(_)}
		printf("\n")
	}

}

/*
au lieu de retour un boolean, on peut aussi retour un double pour savoir combien de pourcent notre réponse correspond à answer
*/
trait CorrectBoolean{
	def isCorrect(reponse:Reponse):Boolean
}
trait CorrectDouble{
	def isCorrect(reponse:Reponse):Double
}

abstract class Questionnaire(_id:Int,_type:Int){
	var id_q = _id;
	var type_q = _type
	var questions : List[Question]
}

class QCMQuestion(_id:Int,_intitule:String,_answer:String,_opts:List[String]) 
extends Question(_id,_intitule,_answer) 
with OptionsQ
with CorrectBoolean
{
	options = _opts
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}
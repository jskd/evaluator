abstract class Question(_id:Int,_intitule:String,_answer:String){
	var id = _id
	var intitule = _intitule
	var answer = _answer
	def display = printf(intitule+"\n")
	def isCorrect(reponse:Reponse):Boolean
}

trait ImageQ extends Question{
	var image_path:String = "Can't find :("
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




class QCMQuestion(_id:Int,_intitule:String,_answer:String,_opts:List[String]) 
extends Question(_id,_intitule,_answer) 
with OptionsQ
{
	options = _opts
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}

class FillQuestion(_id:Int,_intitule:String,_answer:String)
extends Question(_id,_intitule,_answer) 
{
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}

class ImageFillQuestion(_id:Int,_intitule:String,_answer:String,_path:String)
extends Question(_id,_intitule,_answer) 
with ImageQ
{
	image_path = _path
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}
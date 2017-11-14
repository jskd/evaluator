/*
Pour chaque question: 
un id, une description(intitule), et un answer sont obligatoires
elle peut implémenter trait:
Par exemple on peut avoir des question avec images, avec plusieurs choix etc...
*/

abstract class AbsQuestion(i:Int,description:String,ans:String){
	val id = i
	val intitule = description
	val answer = ans
	def display
}

trait ImageQuestion{
	def displayImage
}
trait OptionQuestion{
	def displayOptions
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
/*
Pour chaque Reponse:
elle doit implémenter reponseToString() pour que des quesitons puissent comparer anwser et response.
*/
abstract class Reponse{
	def reponseToString():String
}

class ChoixReponse(lst:List[String]) extends Reponse{
	val choix = lst
	override def reponseToString() = "choix en cours ..."
}

class FileReponse(path:String) extends Reponse{
	val filePath = path
	override def reponseToString() = "file traitement en cours ..."
}

class CodeResponse(text:String,answer:String,langue:String) extends Reponse{
	val codeText = text 
	val codeAnswer = answer
	override def reponseToString() = "code traitement en cours ..."
}


// Exemples
class MyQCMQuestion(i:Int,description:String,img_path:String,answer:String) 
extends AbsQuestion(i,description,answer) 
with OptionQuestion 
with ImageQuestion 
with CorrectBoolean
{
	override def displayOptions() = printf("options en cours ...")
	override def displayImage() = printf(img_path+" en cours ...")
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse
	override def display() = {
		printf(intitule)
		displayImage()
		displayOptions()
	}
}


object Modelisation_V1{
	def main(args:Array[String]){

	}

}


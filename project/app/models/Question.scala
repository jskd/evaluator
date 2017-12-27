package models

abstract class Question(_id:Int,_intitule:String,_answer:String){
	var id = _id
	var intitule = _intitule
	var answer = _answer
	def display = intitule
	def isCorrect(reponse:Reponse):Boolean
}

trait ImageQ extends Question{
	var image_path:String = "Can't find :("
	override def display = {
		super.display + "<br/>" +"<img src='/assets/images/"+image_path+".png'>"
	}
}

trait OptionsQ extends Question{
	var options: List[String] = Nil
	def addOption(o:String) = options = options :+ o
	override def display = {
        var ops:String = ""
		for(op <- options){
            ops += "<input type='checkbox' name='"+"MYA"+id+"' value='"+op.substring(0,1)+"'>" + op.substring(2)
        }
		super.display +"<br/>"+ ops
	}

}

trait FillQ extends Question{
    override def display = {
        super.display + "<br/>" + "<input type='txt' name='"+"MYA"+id+"'>"
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
with FillQ
{
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}

class ImageFillQuestion(_id:Int,_intitule:String,_answer:String,_path:String)
extends Question(_id,_intitule,_answer) 
with ImageQ
with FillQ
{
	image_path = _path
	override def isCorrect(reponse:Reponse):Boolean = answer equals reponse.reponseToString
}

class ImageFillQuestionContains(_id:Int,_intitule:String,_answer:String,_path:String)
extends Question(_id,_intitule,_answer) 
with ImageQ
with FillQ
{
	image_path = _path
	override def isCorrect(reponse:Reponse):Boolean =reponse.reponseToString contains answer
}
abstract class Question(i:Int,d:String){
	type ANS
	type OPT
	type CHOICES = List[OPT]
	var id:Int = i
	var description:String = d
	var answer: ANS
	var opts: CHOICES = Nil
	def addOpt(o:OPT) = opts = opts :+ o
	def isCorrect(reponse:Reponse):Boolean = reponse.correct(this)
}


abstract class Reponse{
	type YA
	var yourAnswer: YA
	def correct(q:Question):Boolean
}

class QCMQestion(i:Int,d:String,a:String) extends Question(i,d){
	type ANS = String
	type OPT = String
	override var answer = a
}

class QCMReponse(ya:String) extends Reponse{
	type YA = String
	override var yourAnswer = ya
	def correct(q:Question):Boolean = q.answer equals yourAnswer
}


object Modelisation_V0{
	def main(args:Array[String]){
		var qcm1 = new QCMQestion(1,"qcm question 1","AD")
		qcm1 addOpt "A. correct 1"
		qcm1 addOpt "B. la la la"
		qcm1 addOpt "C. wrong"
		qcm1 addOpt "D. correct 2"
		var qcm2 = new QCMQestion(2,"qcm question 2","C")
		qcm2 addOpt "A. ????"
		qcm2 addOpt "B. la la la"
		qcm2 addOpt "C. correct 1"	
		var res1 = new QCMReponse("D");
		var res2 = new QCMReponse("C");
		qcm1.opts.foreach{printf(_)}
		printf("\n")
		printf("Q1: " + (qcm1 isCorrect res1).toString+"\n");
		qcm2.opts.foreach{printf(_)}
		printf("\n")
		printf("Q2: " + (qcm2 isCorrect res2).toString+"\n");
	}

}


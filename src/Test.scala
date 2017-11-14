//QCMQuestion(_id:Int,_intitule:String,_answer:String,_opts:List[String]) 
import scala.io.{ StdIn }

object Test{
	def main(args:Array[String]){
		printf("==========test QCM===========\n")

		val qcm1 = new QCMQuestion(1,"What langue do we learn?","A",List("A.scala ","B.java ","C.c# ","D.python ","E.C "))
		qcm1.display

		val re1 = StdIn.readLine()
		val reponse1 = new QCMReponse(re1)
		if(qcm1 isCorrect reponse1){
			printf("Bingo !!!!\n")
		}else{
			printf(":( :( :(\n")
		}

		var qcm2 = new QCMQuestion(1,"20 - 5 = ?","CD",List("A.12 ","B.10 ","C.5*3 ","D.15 "))
		qcm2.display
		val re2 = StdIn.readLine()
		val reponse2 = new QCMReponse(re2)
		if(qcm2 isCorrect reponse2){
			printf("Bingo !!!!\n")
		}else{
			printf(":( :( :(\n")
		}
		printf("==========Fin QCM===========\n")
	}
}
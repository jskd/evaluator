//QCMQuestion(_id:Int,_intitule:String,_answer:String,_opts:List[String]) 
import scala.io.{ StdIn }

object Test{
	def main(args:Array[String]){
		printf("==========test Q===========\n")
		val testQnaire = new Questionnaire(1,0);
		val qcm1 = new QCMQuestion(1,"What langue do we learn?","A",List("A.scala ","B.java ","C.c# ","D.python ","E.C "))
		var qcm2 = new QCMQuestion(2,"20 - 5 = ?","CD",List("A.12 ","B.10 ","C.5*3 ","D.15 "))
		var fill1 = new FillQuestion(3,"Where are you?","France")
		var ifill2 = new ImageFillQuestion(4,"What's in this photo?","error"," test image_path !")
		testQnaire.addQuestion(qcm1)
		testQnaire.addQuestion(qcm2)
		testQnaire.addQuestion(fill1)
		testQnaire.addQuestion(ifill2)

		var total = testQnaire.questions.size
	
		var bingo = 0
		for(q <- testQnaire.questions){
			q.display
			val res = StdIn.readLine()
			if(q.isCorrect(new CommonReponse(res))){
				printf("Bingo !!!\n")
				bingo+=1
			}else{
				printf(":( Faux :(\n")
			}
		}

		printf("==========Fin Q===========\n")
		printf("bingo/total: "+bingo+"/"+total+"\n")
		printf("accuracy: "+bingo*1.0/total+"\n")
	}
}
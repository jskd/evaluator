package models

class Questionnaire(_id:Int,_type:Int){
	var id_q = _id;
	var type_q = _type
	var questions : List[Question] = Nil
	def addQuestion(q:Question) = questions = questions :+ q
	def addQuestion(q:List[Question]) = questions = questions ::: q
	var qid:List[Int] = Nil
	def addqid(i:Int) = qid = qid :+ i
	def addqid(li:List[Int]) = qid = qid ::: li
}



class Questionnaire(_id:Int,_type:Int){
	var id_q = _id;
	var type_q = _type
	var questions : List[Question] = Nil
	def addQuestion(q:Question) = questions = questions :+ q
}


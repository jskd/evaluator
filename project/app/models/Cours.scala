package models

class Cours(_id:Int,_categorie:String){
	var id = _id
	var categorie = _categorie
	var qnaire : List[Questionnaire] = Nil
	def addQnaire(q:Questionnaire) = qnaire = qnaire :+ q
	def addQnaire(q:List[Questionnaire]) = qnaire = qnaire ::: q
	var qnaireid:List[Int] = Nil
	def addqid(i:Int) = qnaireid = qnaireid :+ i
	def addqid(li:List[Int]) = qnaireid = qnaireid ::: li
}

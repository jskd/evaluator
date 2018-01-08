package models

abstract class DBUtil{
    type T
    var dbmaps:Map[Int,T] = Map()
    def getAll():List[T] = dbmaps.values.toList
    def getById(id:Int):T = dbmaps.apply(id)
    def delete(id:Int):Unit = dbmaps -= (id)
    def add(id:Int,t:T):Unit = dbmaps += (id->t)
    def has(id:Int):Boolean = dbmaps.contains(id)
    //def search():List[T]
}

object DBQuestionnaire extends DBUtil{
    type T = Questionnaire
    def applySeeder():Unit = {
        if(dbmaps.isEmpty){
            var q1 = new Questionnaire(1,1)
            var q2 = new Questionnaire(2,0)
            var q3 = new Questionnaire(3,1)
            q1.addqid(List(1,3,5,7,9,11))
            q2.addqid(List(2,4,6,8,10))
            q3.addqid(List(1,4,6,5))
            add(1,q1)
            add(2,q2)
            add(3,q3)
        }
    }
}

object DBCours extends DBUtil{
    type T = Cours
    def applySeeder():Unit = {
        if(dbmaps.isEmpty){
            var q1 = new Cours(1,"Math")
            var q2 = new Cours(2,"Info")
            var q3 = new Cours(3,"Tonkha")
            q1.addqid(List(1))
            q2.addqid(List(2,1,3))
            q3.addqid(List(2))
            add(1,q1)
            add(2,q2)
            add(3,q3)
        }
    }
}


object DBQuestion extends DBUtil{
    type T = Question
    def applySeeder():Unit = {
        if(dbmaps.isEmpty){
            var qcm1 = new QCMQuestion(1,"What langue do we learn?","A",List("A.scala ","B.java ","C.c# ","D.python ","E.C "))
            var qcm2 = new QCMQuestion(2,"20 - 5 = ?","CD",List("A.12 ","B.10 ","C.5*3 ","D.15 "))
            var fill1 = new FillQuestion(3,"Where are you?","France")
            var ifill2 = new ImageFillQuestion(4,"Combien de relation voit-on sur le diagramme?","4","ad4")
            var ifill3 = new ImageFillQuestionContains(5,"A quoi correspond cette icône?","jointure","ad1")
            var ifill4 = new ImageFillQuestionContains(6,"Que signifie ce diagramme?","nestedloop","ad3")
            var ifill5 = new ImageFillQuestionContains(7,"Qu'évoque cette figure?","sémantique","ad2")
            var fill2 = new FillQuestion(8,"Where are you2?","France")
            var fill3 = new FillQuestion(9,"Where are you3?","France")
            var fill4 = new FillQuestion(10,"Where are you4?","France")
            var fill5 = new FillQuestion(11,"Where are you5?","France")
            var fill6 = new FillQuestion(12,"Where are you6?","France")
            add(1,qcm1)
            add(2,qcm2)
            add(3,fill1)
            add(4,ifill2)
            add(5,ifill3)
            add(6,ifill4)
            add(7,ifill5)
            add(8,fill2)
            add(9,fill3)
            add(10,fill4)
            add(11,fill5)
            add(12,fill6)
        }
    }
}

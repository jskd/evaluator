//QCMQuestion(_id:Int,_intitule:String,_answer:String,_opts:List[String])
import scala.io.{ StdIn }

object Test{

	def main(args:Array[String]){

		val admin1 = new Administrateur(1,"Admin1")
		val user1 = new Client(1,"User1")
		val user2 = new Client(2,"User2")
		var typeUser = 0
		var ps = ""

		var quit = false

		val liste = new ListeUtilisateurs()
		liste.addUser(admin1)
		liste.addUser(user1)
		liste.addUser(user2)

		printf("Welcome to the EMULATOR Application\n")

		printf("Which kind of user are you ?")
		printf("Type 1 for Administrateur, 2 for Client\n")
		var res = StdIn.readLine()

		while(res != "1" && res != "2"){
			printf("Please type 1 for Administrateur, 2 for Client\n")
			res = StdIn.readLine()
		}

		if(res == "1"){
			println("Bonjour veuillez taper votre pseudo Administrateur")
			ps = StdIn.readLine()
			while(liste.checkUSer(ps) == false){
				println("Ce pseudo n'existe pas dans la base")
				println("Veuillez taper à nouveau un pseudo")
				ps = StdIn.readLine()
			}
			typeUser = 1
		}
		else if(res == "2"){
			println("Bonjour veuillez taper votre pseudo Client")
			ps = StdIn.readLine()
			while(liste.checkUSer(ps) == false){
				println("Ce pseudo n'existe pas dans la base")
				println("Veuillez taper à nouveau un pseudo")
				ps = StdIn.readLine()
			}
			typeUser = 2
		}

		var currentUser = liste.getUserByPseudo(ps)

		if(typeUser == 1){
			println("Bonjour, Administrateur " + currentUser.getPseudo())
			while(quit == false){
				println("Menu")
				println("1. Ajouter Question")
				println("2. Liste des Questions")
				println("3. Quitter")
				var res = StdIn.readLine()
				if(res == "3"){
					System.exit(1)
				}

			}
		}
		else if(typeUser == 2){
			println("Bonjour, Client " + currentUser.getPseudo())
			while(quit == false){
				println("Menu")
				println("1. Test QCM")
				println("2. Quitter")
				var res = StdIn.readLine()
				if(res == "2"){
					System.exit(1)
				}
			}
		}


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

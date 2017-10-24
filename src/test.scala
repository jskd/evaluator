object HelloWorld {
	def main (args : Array[String]){
		var x = args.length;
		if(x > 0){
			for( arg <- args){
				println(arg);
			}
		}
		else{
			println("Hello world !");
		}
	}
}

import kotlinx.coroutines.*

fun main(){
    println("Run Blocking Start!")
	  runBlocking {
  	    launch {
    		    delay(5000)
    		    println("Run Blocking!")
  		  }
	  }
	  println("Run Blocking End!")
}

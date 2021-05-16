import kotlinx.coroutines.*

fun main(){
    // Baska Coroutines icerisinden Coroutines yurutme: 
    // Once Coroutine Scope daha sonra Run Blocking calisir.
    runBlocking {
  	  launch {
    	  delay(5000)
    		print("Run Blocking")
  		}
  		coroutineScope {
    		launch {
      			delay(2500)
      			print("Coroutine Scope")
    		}
  		}
	 }
}

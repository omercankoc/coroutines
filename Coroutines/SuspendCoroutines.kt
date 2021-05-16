import kotlinx.coroutines.*

fun main(){
    print("Coroutine Scope Start!")
	  CoroutineScope(Dispatchers.Default).launch {
  		delay(5000)
  		print("Coroutine Scope")
	}
	print("Coroutine Scope End!")
}

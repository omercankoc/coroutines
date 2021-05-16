import kotlinx.coroutines.*

fun main(){
    println("Run Blocking Start!")
    // Kapsadığı işlemler tamamlanıncaya kadar sonraki işlemlerin yürütülmesini engeller.
    runBlocking {
  	launch {
            delay(5000)
            println("Run Blocking!")
        }
    }
    println("Run Blocking End!")
}

import kotlinx.coroutines.*

fun main(){
    // Uygulamanin tumunde calisabilecek bir kapsamda yurutulur.
    println("Global Scope Start!")
    GlobalScope.launch {
        delay(5000)
        println("Global Scope...")
    }
    println("Global Scope End!")
}

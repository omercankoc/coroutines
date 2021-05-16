import kotlinx.coroutines.*

fun main(){
    // Uygulamanin tumunde calisabilecek bir kapsamda yurutulur.
    GlobalScope.launch {
        repeat(10){
            launch {
                println("In the Global Scope!")
            }
        }
    }
}

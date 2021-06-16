## What is Coroutines?

<p>Coroutines is the recommended solution for asynchronous programming on Android. Notable features include:</p>
<ul>
  <li>Lightweight</li>
  <li>Fewer memory leaks</li>
  <li>Built-in cancellation support</li>
  <li>Jetpack integration</li>
</ul>

### Dependency:
```gradle
dependencies {
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
}
```
or 
```kotlin
import kotlinx.coroutines.*
```

### Global Scope : Runs on the entire application.
```kotlin
import kotlinx.coroutines.*

fun main(){
    // It is executed in the scope that can work in the whole application.
    println("Global Scope Start!")
    GlobalScope.launch {
        delay(1000)
        println("Global Scope Running...")
    }
    println("Global Scope End!")
}
```
Now, first "Global Scope Start!" then "Global Scope End!" drops to the display. After 5 seconds, the "Global Scope..." drops to the screen.

### Run Blocking : It prevents the execution of subsequent operations until the operations it covers are completed.
```kotlin
import kotlinx.coroutines.*

fun main(){
    println("Run Blocking Start!")
    // It blocks the execution of subsequent transactions until the transactions it covers are completed.
    runBlocking {
        launch {
            delay(5000)
            println("Run Blocking!")
        }
    }
    println("Run Blocking End!")
}
```

### Coroutine Scope : It acts like a "Global Scope". It does not cover the entire application.
1. Through the "suspend function"
```kotlin
import kotlinx.coroutines.*

fun main(){
    println("Coroutine Scope Start!")
    CoroutineScope(Dispatchers.Default).launch {
        delay(5000)
        println("Coroutine Scope")
    }
    println("Coroutine Scope End!")
}
```
2. Through the "another coroutine"
```kotlin
import kotlinx.coroutines.*

fun main(){
    // Execute Coroutines in other Coroutines.
    // CoroutineScope works first, then RunBlocking.
    runBlocking {
        launch {
            delay(5000)
            println("Run Blocking")
        }
        coroutineScope {
            launch {
                delay(2500)
                println("Coroutine Scope")
            }
        }
    }
}
```

### Dispatchers : 
<p>Dispatchers.Default    -> The default CoroutineDispatcher that is used by all standard builders like launch, async, etc if neither a dispatcher nor any other                                  ContinuationInterceptor is specified in their context.</p>
<p>Dispatchers.IO         -> The CoroutineDispatcher that is designed for offloading blocking IO tasks to a shared pool of threads.</p>
<p>Dispatchers.Main       -> A coroutine dispatcher that is confined to the Main thread operating with UI objects. Usually such dispatchers are single-threaded.</p>
<p>Dispatchers.Unconfined -> A coroutine dispatcher that is not confined to any specific thread. It executes the initial continuation of a coroutine in the current                              call-frame and lets the coroutine resume in whatever thread that is used by the corresponding suspending function, without mandating                                any specific threading policy. Nested coroutines launched in this dispatcher form an event-loop to avoid stack overflows.</p>

```kotlin
import kotlinx.coroutines.*

fun main(){
    
    runBlocking {
        launch(Dispatchers.Main) {
            println("Main Thread : ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO Thread : ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Default Thread : ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined Thread : ${Thread.currentThread().name}")
        }
    }
}
```

### Suspend : Functions that run coroutines in it.
```kotlin
import kotlinx.coroutines.*

fun main(){
    
    GlobalScope.launch {
        suspendCoroutines()
    }

    runBlocking {
        launch {
            delay(2000)
            println("Run Blocking!")
            suspendCoroutines()
        }
    }
}

suspend fun suspendCoroutines(){
    coroutineScope {
        delay(5000)
        println("Suspend Function in Coroutines!")
    }
}  
```
### Async : It enables to perform more than one task asynchronously.
```kotlin
import kotlinx.coroutines.*

fun main(){
    var language = ""
    var platform = ""

    runBlocking {
        val downloadLanguage = async {
            downloadLanguage()
        }
        val downloadPlatform = async {
            downloadPlatform()
        }
        language = downloadLanguage.await() // Waits to load...
        platform = downloadPlatform.await() // Waits to load...
        println("$language $platform")
    }
}

suspend fun downloadLanguage() : String {
    val language = "Kotlin"
    println("Complete Download Language!")
    return language
}

suspend fun downloadPlatform() : String {
    val platform = "Android"
    println("Complete Download Platform!")
    return platform
}
```

### Job : Jobs whose execution can be intervened.
```kotlin
import kotlinx.coroutines.*

fun main(){
    runBlocking {
        val firstJob = launch {
            delay(2000)
            println("FIRST JOB...")
            val secondJob = launch {
                println("SECOND JOB...")
            }
        }
        
        // When finished...
        firstJob.invokeOnCompletion {
            println("Job is Completed!")
        }
        firstJob.cancel()
    }
}
```
### A different Thread on the same Scope :
```kotlin
import kotlinx.coroutines.*

fun main(){
    runBlocking {
        launch(Dispatchers.Default) {
            println("Context : $coroutineContext")
            withContext(Dispatchers.IO){
                println("Context : $coroutineContext")
            }
        }
    }
}
```

### What is Coroutines?
<p>A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive.</p>

<p>Coroutines is the recommended solution for asynchronous programming on Android. Notable features include:</p>
<ul>
  <li>Lightweight</li>
  <li>Fewer memory leaks</li>
  <li>Built-in cancellation support</li>
  <li>Jetpack integration</li>
</ul>

Dependency:
```gradle
dependencies {
  implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
}
```

Global Scope : Runs on the entire application.
```kotlin
println("Global Scope Start!")
GlobalScope.launch {
  delay(5000)
    println("Global Scope...")
  }
println("Global Scope End!")
```
Now, first "Global Scope Start!" then "Global Scope End!" drops to the display. After 5 seconds, the "Global Scope..." drops to the screen.

Run Blocking : It prevents the execution of subsequent operations until the operations it covers are completed.
```kotlin
println("Run Blocking Start!")
runBlocking {
  launch {
    delay(5000)
    println("Run Blocking!")
  }
}
println("Run Blocking End!")
```

Coroutine Scope : It acts like a "Global Scope". It does not cover the entire application.
1. Through the "suspend function"
```kotlin
print("Coroutine Scope Start!")
CoroutineScope(Dispatchers.Default).launch {
  delay(5000)
  print("Coroutine Scope")
}
print("Coroutine Scope End!")
```
2. Through the "another coroutine"
```kotlin
runBlocking {
  launch {
    delay(5000)
    print("Run Blocking")
  }
  coroutineScope {
    launch {
      delay(3000)
      print("Coroutine Scope")
    }
  }
}
```

Dispatchers : 
<p>Dispatchers.Default    -> The default CoroutineDispatcher that is used by all standard builders like launch, async, etc if neither a dispatcher nor any other                                  ContinuationInterceptor is specified in their context.</p>
<p>Dispatchers.IO         -> The CoroutineDispatcher that is designed for offloading blocking IO tasks to a shared pool of threads.</p>
<p>Dispatchers.Main       -> A coroutine dispatcher that is confined to the Main thread operating with UI objects. Usually such dispatchers are single-threaded.</p>
<p>Dispatchers.Unconfined -> A coroutine dispatcher that is not confined to any specific thread. It executes the initial continuation of a coroutine in the current                              call-frame and lets the coroutine resume in whatever thread that is used by the corresponding suspending function, without mandating                                any specific threading policy. Nested coroutines launched in this dispatcher form an event-loop to avoid stack overflows.</p>

```kotlin
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
```

Suspend : Functions that run coroutines in it.
```kotlin
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
  
  suspend fun suspendCoroutines(){
    coroutineScope {
      delay(5000)
      println("Suspend Function in Coroutines!")
    }
  }
}  
```
Async : It enables to perform more than one task asynchronously.
```kotlin
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

Job : 
```kotlin
runBlocking {
  
  val firstJob = launch {
    delay(5000)
    println("FIRST JOB...")
    
    val secondJob = launch {
      delay(5000)
      println("SECOND JOB...")
    }
  }

  // When finished...
  firstJob.invokeOnCompletion {
    println("Job is Completed!")
  }
  firstJob.cancel()
}

A different Thread on the same Scope :
runBlocking {
  launch(Dispatchers.Default) {
    println("Context : $coroutineContext")
    withContext(Dispatchers.IO){
      println("Context : $coroutineContext")
    }
  }
}
```

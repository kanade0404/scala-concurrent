import java.util.concurrent.FutureTask

/**
  * 計算結果が取得可能なRunnable
  * 計算結果を取得するためのgetメソッドがブロックして待つことができるため、
  * このFutureTaskもまたシンクロナイザとして利用することが可能です。
  */
object FutureTask extends App {
  val futureTask = new FutureTask[Int](() => {
    Thread.sleep(1000)
    println("FutureTask finished")
    2525
  })
  new Thread(futureTask).start()
  new Thread(() => {
    val result = futureTask.get()
    println(s"result: $result")
  }).start()
}

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
object BlockingQueueStudy extends App {

  /**
    * 10の容量を持つブロッキングキューを作成。
    * putメソッドを用いた場合、10個を超えるRunnableのインスタンスを入れようとした場合にブロックし続ける。
    */
  val blockingQueue = new ArrayBlockingQueue[Runnable](10)

  /**
    * 要素が0の場合にはtakeメソッドの呼び出しで次の要素が誰かによって挿入されるまで待ち続けることになる
    */
  val finishedCount = new AtomicInteger(0)
  var threads = Seq[Thread]()
  for (i <- 1 to 4) {
    val t = new Thread(() => {
      try while (true) {
        val runnable = blockingQueue.take()
        runnable.run()
      } catch {
        case _: InterruptedException =>
      }
    })
    t.start()
    threads = threads :+ t
  }
  for (i <- 1 to 100) {
    blockingQueue.put(() => {
      Thread.sleep(1000)
      println(s"Runnable: $i finished.")
      finishedCount.incrementAndGet()
    })
  }
  while (finishedCount.get() != 100) Thread.sleep(1000)

  /**
    * スレッドのインスタンスに対してinterruptメソッドを呼び出すとそのスレッドの中でInterrupedExceptionがスローされる
    */
  threads.foreach(_.interrupt())
}

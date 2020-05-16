import java.util.concurrent.Semaphore

/**
  * Semaphore
  * 何かのリソースにアクセスさせる際にその数を制限したい時に用いる並行処理部品
  */
object SemaphoreStudy extends App {
  val semaphore = new Semaphore(3)
  for (i <- 1 to 100) {
    new Thread(() => {
      try {
        // 許可を取得
        semaphore.acquire()
        Thread.sleep(300)
        println(s"Thread finished. $i")
      } finally
      // 許可を返却(必ず呼ばれる必要がある)
      semaphore.release()
    }).start()
  }
}

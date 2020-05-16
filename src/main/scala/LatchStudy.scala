import java.util.concurrent.CountDownLatch

/**
  * 最終ステートに到着するまで堰き止めておくゲートの働きをする
  */
object LatchStudy extends App {
  val latch = new CountDownLatch(3)
  for (i <- 1 to 3)
    new Thread(() => {
      println(s"Finished and countDown! $i")
      // countDownでラッチのカウントを下げ、awaitでカウントが0になるのを待つこと
      latch.countDown()
    }).start()
  new Thread(() => {
    // カウントが0になるまで待つ
    latch.await()
    println("All tasks finished.")
  }).start()
}

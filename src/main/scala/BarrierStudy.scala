import java.util.concurrent.CyclicBarrier

/**
  * 他のスレッドが全員揃ったらスタートできる並行部品
  */
object BarrierStudy extends App {

  /**
    * パーティの数を4に設定されているため、4つのスレッドがawaitメソッドによって待ち状態になった時点でバリアアクションが実行
    * => 揃っていた４つのスレッドだけが実行される
    */
  val barrier = new CyclicBarrier(4, () => println("Barrier Action!"))
  for (i <- 1 to 6) {
    new Thread(() => {
      println(s"Thread started. $i")
      Thread.sleep(300)
      barrier.await()
      Thread.sleep(300)
      println(s"Thread finished. $i")
    }).start()
  }
  Thread.sleep(5000)
  System.exit(0)
}

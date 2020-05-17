import java.util.concurrent.{Callable, Executors}
import java.util.concurrent.atomic.AtomicInteger

/**
  * Executorsというファクトリークラスから特定のルールを持ったスレッドプールを持つ
  * ExecutorServiceを取得し、そのExecutorServiceに実際のタスクを与える。
  * => タスクはRunnableまたはCallableを利用する。
  * => Callable実行を行ったタスクはFutureというインターフェースの形式で受け取ることができる。
  */
object ExecutorServiceStudy extends App {
  // スレッドを10個持つExecutorServiceを作成
  val es = Executors.newFixedThreadPool(10)
  val counter = new AtomicInteger(0)

  /**
    * カウンターをインクリメントして出力したあと、100ミリ秒スリープ
    * => インクリメントした値をタスクの結果として返す
    */
  val futures = for (i <- 1 to 1000) yield {
    es.submit(new Callable[Int] {
      override def call(): Int = {
        val count = counter.incrementAndGet()
        println(count)
        Thread.sleep(100)
        count
      }
    })
  }
  // 1000個のFutureの数をfoldLeftで畳み込んで合計した数をコンソールに出力
  println("sum: " + futures.foldLeft(0)((acc, f) => acc + f.get()))
  // すぐさまシャットダウンを行う
  es.shutdownNow()
}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration._

object PromiseStudy extends App {
  val promiseGetInt: Promise[Int] = Promise[Int]
  // PromiseからFutureを作ることができる
  val futureByPromise: Future[Int] = promiseGetInt.future

  // Promiseが解決された時に実行される処理をFutureを使って書くことが出来る
  val mappedFuture = futureByPromise.map { i =>
    println(s"Success! i: $i")
  }

  // 別スレッドで何か重い処理をして、終わったらPromiseに値を渡す
  Future {
    Thread.sleep(300)
    promiseGetInt.success(1)
  }
  Await.ready(mappedFuture, 5000.millisecond)
}

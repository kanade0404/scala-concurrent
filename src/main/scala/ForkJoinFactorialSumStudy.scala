import java.util.concurrent.{ForkJoinPool, RecursiveTask}

import scala.annotation.tailrec

/**
  * FactorialSumTrialのFork/Joinフレームワークで並列化
  */
object ForkJoinFactorialSumStudy extends App {
  val length = 5000
  val list = (for (i <- 1 to length) yield BigInt(i)).toList
  val pool = new ForkJoinPool()
  class AggregateTask(list: List[BigInt]) extends RecursiveTask[BigInt] {

    /**
      * タスクを分割できるまで分割し、
      * 分割できない場合 => その値を取得
      * 分割できる場合 => 分割してそれらを非同期で実行して結合
      * @return
      */
    override def compute(): BigInt = {
      val n = list.length / 2
      if (n == 0)
        list match {
          case List()  => 0
          case List(n) => factorial(n, BigInt(1))
        } else {

        /**
          * 処理分割できる場合
          * listを分割し、AggregateTaskインスタンスを作成し、
          * fork()メソッドに非同期実行をそれぞれのインスタンスに対して実行。
          * 非同期に計算した結果をjoin()メソッドで受け取り、それぞれの集計結果を足し合わせて、それを計算結果とする。
          */
        val (left, right) = list.splitAt(n)
        val leftTask = new AggregateTask(left)
        val rightTask = new AggregateTask(right)
        leftTask.fork()
        rightTask.fork()
        leftTask.join() + rightTask.join()
      }
    }
    @tailrec
    private[this] def factorial(i: BigInt, acc: BigInt): BigInt =
      if (i == 0) acc else factorial(i - 1, i * acc)
  }
  val start = System.currentTimeMillis()
  val factorialSum = pool.invoke(new AggregateTask(list))
  val time = System.currentTimeMillis() - start
  println(factorialSum)
  println(s"time: $time msec")
}

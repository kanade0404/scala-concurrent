import scala.annotation.tailrec

/**
  * 1から5000までの階乗を足し合わせた数と処理時間を出力
  * 1!+2!+3!+...+5000!
  */
object FactorialSumTrial extends App {
  val length = 5000
  val list = (for (i <- 1 to length) yield BigInt(i)).toList
  @tailrec
  private[this] def factorial(i: BigInt, acc: BigInt): BigInt =
    if (i == 0) acc else factorial(i - 1, i * acc)
  val start = System.currentTimeMillis()
  val factorialSum = list.foldLeft(BigInt(0))((acc, n) => acc + factorial(n, 1))
  val time = System.currentTimeMillis() - start
  println(factorialSum)
  println(s"time: $time msec")
}

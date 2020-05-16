object MemoryVisibility extends App {

  /**
    * volatileアノテーションはJavaコンパイラとランタイムで複数のスレッドから共通される変数と認識される
    * => CPUの各コアのレジスタや他のコアから見えないキャッシュに保存されることなく、
    * どのスレッドから読み込まれても他のスレッドが書き込んだ最新の値を取得できる。
    * <= アトミックな処理を求める同期化には使えない。
    */
//  @volatile var number = 0
//  @volatile var ready = false
  var number = 0
  var ready = false
  private[this] def getNumber: Int = synchronized { number }
  private[this] def getReady: Boolean = synchronized { ready }
  new Thread(() => {
    while (!ready) {
      Thread.`yield`()
    }
    println(number)
  }).start()
  number = 2525
  ready = true
}

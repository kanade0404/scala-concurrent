import scala.collection.mutable.ArrayBuffer

/**
  * 逸出は、本来公開すべきではないフィールドのオブジェクトや変数が公開されてしまうこと。
  */
object Escape extends App {
  for (i <- 1 to 100)
    new Thread(() => {
      println(EscapeVarSeqProvider.next)
      println(EscapeArrayBufferProvider.next)
    }).start()
}

object EscapeVarSeqProvider {
  //var seq: Seq[Int] = Seq() // ESCAPE!(publicになっているので別スレッドからアクセス可能)
  private[this] var seq: Seq[Int] = Seq()
  def next: Seq[Int] = synchronized {
    val nextSeq = seq :+ (seq.size + 1)
    seq = nextSeq
    nextSeq
  }
}

object EscapeArrayBufferProvider {
  private[this] val array: ArrayBuffer[Int] = ArrayBuffer.empty[Int]
  def next: ArrayBuffer[Int] = synchronized {
    array += (array.size + 1)
    // array // ESCAPE!(private[this]でもnextメソッド自身が可変オブジェクトのインスタンスを返してしまっている)
    array.clone()
  }
}

import java.util.concurrent.atomic.AtomicInteger
object OutOfMemmoryWithThread extends App {
  val counter = new AtomicInteger(0)
  while (true) new Thread(() => {
    println(counter.incrementAndGet())
    Thread.sleep(10000)
  }).start()
}

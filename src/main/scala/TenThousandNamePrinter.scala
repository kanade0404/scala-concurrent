import java.util.concurrent.Executors
object TenThousandNamePrinter extends App {
  val es = Executors.newFixedThreadPool(10)
  for (i <- 1 to 10000) {
    es.submit(new Runnable {
      override def run(): Unit = {
        Thread.sleep(100)
        println(Thread.currentThread().getName)
      }
    })
  }
}

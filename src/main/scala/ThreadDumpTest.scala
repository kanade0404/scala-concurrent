import java.time.LocalDateTime
import java.util.concurrent.{Executors, TimeUnit}
object ThreadDumpTest extends App {
  val es = Executors.newScheduledThreadPool(3)
  // 遅延0秒で1秒ごとにずっと実行し続ける
  es.scheduleAtFixedRate(
    () =>
      println(
        s"ThreadName: ${Thread.currentThread().getName} LocalDateTime: ${LocalDateTime
          .now()}"),
    0L,
    1L,
    TimeUnit.SECONDS)
}

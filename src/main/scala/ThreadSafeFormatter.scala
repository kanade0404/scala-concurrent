import java.text.SimpleDateFormat
import java.util.Date
object ThreadSafeFormatter extends App {
  def format(date: Date): String = {
    val sd = new SimpleDateFormat("yyyy'年'MM'月'dd'日'E'曜日'H'時'mm'分'")
    sd.format(date)
  }
  println(format(new Date(System.currentTimeMillis())))
}

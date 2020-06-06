import com.sun.tools.doclets.internal.toolkit.util.DocFinder.Input

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.io
import scala.util.{Failure, Success}

object PromiseStdIn extends App {
  def applyFormStdIn(lineInputProcessor: Int => Unit): Unit =
    lineInputProcessor(io.StdIn.readLine().toInt)
  val promise: Promise[Int] = Promise[Int]
  applyFormStdIn(i => promise.success(i * 7))
  val future: Future[Int] = promise.future

  future onComplete {
    case Success(value)     => println(value)
    case Failure(exception) => exception.printStackTrace()
  }
  Await.result(future, Duration.Inf)
}

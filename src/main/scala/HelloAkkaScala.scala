import akka.actor.{Actor, ActorSystem, Inbox, Props}
import scala.concurrent.duration._

case object Greet
case class WhoToGreet(who: String)
case class Greeting(message: String)

/**
  * WhoToGreetというメッセージを受け取ると返信に使う挨拶文を設定することが出来る
  */
class Greeter extends Actor {
  var greeting = ""

  override def receive: Receive = {
    case WhoToGreet(who) => greeting = s"hello, $who"
    case Greet           => sender ! Greeting(greeting)
  }
}

/**
  * 挨拶をコンソールに出力することを責務としたアクター
  */
class GreetPrinter extends Actor {
  override def receive: Receive = {
    case Greeting(message) => println(message)
  }
}

object HelloAkkaScala extends App {
  // ActorSystemの生成
  val system = ActorSystem("helloAkka")
  // Greeter Actorの生成
  val greeter = system.actorOf(Props[Greeter], "greeter")
  // InboxはGreeterアクターにメッセージを送る
  val inbox = Inbox.create(system)
  greeter ! WhoToGreet("akka")
  inbox.send(greeter, Greet)
  // inboxに5秒間メッセージの受け取りがあるか待ち、動機的にGreeterアクターのGreetingのメッセージを待つ
  val Greeting(message1) = inbox.receive(5.seconds)
  println(s"Greeting: $message1")

  greeter ! WhoToGreet("Lightbend")
  inbox.send(greeter, Greet)
  val Greeting(message2) = inbox.receive(5.seconds)
  println(s"Greeting: $message2")

  val greetPrinter = system.actorOf(Props[GreetPrinter])

  /**
    * メッセージ送信の最初のディレイ
    * メッセージ送信のインターバル
    * メッセージの受け取りてのActorRef
    * メッセージのオブジェクト
    * 実行コンテキスト
    * 送り手のアクター
    */
  system.scheduler.schedule(0.seconds, 1.seconds, greeter, Greet)(
    system.dispatcher,
    greetPrinter)
}

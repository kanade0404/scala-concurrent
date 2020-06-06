import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

import scala.concurrent.Await
import scala.concurrent.duration._

// Actorトレイトをmixinして利用している
class MyActor extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}

object ActorStudy extends App {
  // "actorStudy"という名前のActorSystemを定義
  val system = ActorSystem("actorStudy")
  // Actorを作成し、作成したActorのActorRefというアクターの参照を取得する
  val myActor = system.actorOf(Props[MyActor], "myActor")
  // "test"と"hoge"のメッセージを送る(内部的にはtellメソッドへの転送メソッド)
  myActor ! "test"
  myActor ! "hoge"
  Thread.currentThread().join()
}

import akka.actor.{Actor, ActorSystem, Inbox, Props}

class MessageCounter extends Actor {
  var count = 0

  override def receive: Receive = {
    case _ =>
      count = count + 1
      println(count)
  }
}
object MessageCountActorApp extends App {
  val system = ActorSystem("showMessage")
  val messanger = system.actorOf(Props[MessageCounter], "message")
  for (i <- 1 to 10000)
    messanger ! "test"
  Thread.currentThread().join()
}

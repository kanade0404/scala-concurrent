import java.util.Date

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class PrintLog extends Actor {
  var logger = Logging(context.system, this)

  override def receive: Receive = {
    case message: String =>
      logger.info(message)
      Thread.sleep(1000)
    case _ =>
  }
}

object TooMuchMessageApp extends App {
  val system = ActorSystem("logActor")
  val actor = system.actorOf(Props[PrintLog], "printLog")
  while (true) actor ! new Date().toString
}

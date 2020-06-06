case object Printer1
case object Printer2

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class PrintMessage1 extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Printer1 =>
      log.info("printer1")
      sender() ! Printer2
    case _ =>
  }
}

class PrintMessage2 extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Printer2 =>
      log.info("printer2")
      sender() ! Printer1
    case _ =>
  }
}

object ReplyActorApp extends App {
  val system = ActorSystem("printMessage")
  val printer1 = system.actorOf(Props[PrintMessage1], "printer1")
  val printer2 = system.actorOf(Props[PrintMessage2], "printer2")
  printer1.tell(Printer1, printer2)
}

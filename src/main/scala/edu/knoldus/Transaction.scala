package edu.knoldus

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.Transaction.Phone

/**
  * Created by Neelaksh on 2/8/17.
  */
class Transaction extends Actor with ActorLogging{

  override def receive: Receive = {
    case true =>
      log.info("succesful purchase")
      sender() ! Phone
  }
}

object Transaction{
  case object Phone

}
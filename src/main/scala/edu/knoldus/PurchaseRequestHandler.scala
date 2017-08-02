package edu.knoldus
import akka.actor.{Actor, ActorLogging, ActorRef}
/**
  * Created by Neelaksh on 2/8/17.
  */
class PurchaseRequestHandler(vrh:ActorRef) extends Actor with ActorLogging{

  def receive: Receive = {
    case _:CustomerInfo =>
      log.info("Valid customer info")
      vrh.forward(true)
    case _=>
      log.info("invalid customer info")
      sender() ! "invalid details"
  }
}

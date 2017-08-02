package edu.knoldus

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

/**
  * Created by Neelaksh on 2/8/17.
  */
class AvailabilityHandler extends Actor with ActorLogging {

  var remainingItems:Int = 1

  var router = {
    val routees = Vector.fill(5) {
      val child = context.actorOf(Props[Transaction])
      context watch child
      ActorRefRoutee(child)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive: Receive = {
    case true =>
      if(remainingItems > 0) {
        log.info(s"${remainingItems} phones available, sending request to worker")
        remainingItems=remainingItems -1
        router.route(true, sender())
      }
      else {
        log.info("out of phones")
        sender() ! "sorry! out of phones"
      }

    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Transaction])
      context watch r
      router = router.addRoutee(r)
  }
}

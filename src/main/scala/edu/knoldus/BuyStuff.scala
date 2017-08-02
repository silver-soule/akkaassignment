package edu.knoldus
import akka.pattern.ask
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import scala.concurrent.duration.DurationInt

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by Neelaksh on 2/8/17.
  */
object BuyStuff extends App{
  val minimart = ActorSystem("minimart")
  val props1 = Props[Transaction]
  val props2 = Props[AvailabilityHandler]
  val purchase = minimart.actorOf(props1)

  val validationRequestHandler = minimart.actorOf(props2)
  val purchaseRequestHandler = minimart.actorOf(Props(classOf[PurchaseRequestHandler],validationRequestHandler))
  implicit val timeout = Timeout(1000 seconds)
  val order1 = purchaseRequestHandler ? CustomerInfo("a","b",1,"x")
  val order2 = purchaseRequestHandler ? CustomerInfo("a","b",1,"x")
  order1.foreach(println(_))
  order2.foreach(println(_))
}

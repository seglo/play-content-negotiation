package controllers

import java.util.Date
import javax.inject._

import akka.actor.ActorSystem
import models._
import org.json4s._
import org.json4s.jackson.Serialization
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends Controller with ContentNegotiation {

  def project = Action.async { implicit request => // request & request headers, implicitly required for `negotiate` methods
    val project = Project("test", new Date, Some(Language("Scala", 2.75)), List(
      Team("QA", List(Employee("John Doe", 5), Employee("Mike", 3))),
      Team("Impl", List(Employee("Mark", 4), Employee("Mary", 5), Employee("Nick Noob", 1)))))

    negotiateAsync(project)
  }

}

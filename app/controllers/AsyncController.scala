package controllers

import java.util.Date
import javax.inject._

import akka.actor.ActorSystem
import models._
import org.json4s._
import org.json4s.jackson.Serialization
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends Controller with ContentNegotiation {

  /**
    * The json4s default serialization formats, implicitly required for `negotiate` methods
    * You may choose to use one or more different libraries here, depending on your requirements.  If your library
    * doesn't have a Scala DSL or implicit marshaling helpers, then write your own (i.e. like json4s `Formats` or
    * play-json `Writes[T]` types).
    */
  implicit val formats = Serialization.formats(NoTypeHints)

  def project = negotiateAction { implicit request => Future {
    val project = Project("test", new Date, Some(Language("Scala", 2.75)), List(
      Team("QA", List(Employee("John Doe", 5), Employee("Mike", 3))),
      Team("Impl", List(Employee("Mark", 4), Employee("Mary", 5), Employee("Nick Noob", 1)))))

    Content(project)
  }}
}

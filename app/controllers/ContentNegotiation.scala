package controllers

import org.json4s.{Extraction, _}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import org.json4s._
import org.json4s.Xml.toXml
import org.json4s.native.JsonMethods.{render => jsonRender, _}
import play.api.http.ContentTypes

/**
  * A generalized Content Negotiation example using json4s
  */
trait ContentNegotiation extends AcceptExtractors with Rendering with Results {
  import ExecutionContext.Implicits.global

  def negotiateAction[M](block: Request[AnyContent] => Future[Content[M]])
                        (implicit formats: Formats): Action[AnyContent] = Action.async { implicit request =>
    block(request) map { result =>
      val repr: JValue = serialize(result.model)
      render {
        case Accepts.Xml() => xmlResult(repr, result.status)
        case Accepts.Json() => jsonResult(repr, result.status)
      }
    }
  }

  private def xmlResult(repr: JValue, status: Int)
               (implicit request: Request[AnyContent]) =
    Results.Status(status)(toXml(repr).toString).as(ContentTypes.XML)

  private def jsonResult(repr: JValue, status: Int)
               (implicit request: Request[AnyContent]) =
    Results.Status(status)(compact(jsonRender(repr))).as(ContentTypes.JSON)

  private def serialize[M](model: M)(implicit formats: Formats): JValue =
    Extraction.decompose(model)
}

final case class Content[M](model: M, status: Int = 200)

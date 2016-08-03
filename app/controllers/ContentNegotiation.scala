package controllers

import org.json4s.{Extraction, _}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import org.json4s._
import org.json4s.Xml.toXml
import org.json4s.jackson.Serialization
import org.json4s.native.JsonMethods.{render => jsonRender, _}
import play.api.http.ContentTypes

/**
  * A generalized Content Negotiation example using json4s
  */
trait ContentNegotiation extends AcceptExtractors with Rendering with Results {

  def negotiateAction[M](block: Request[AnyContent] => Future[M])
                        (implicit formats: Formats, codec: Codec, ec: ExecutionContext): Action[AnyContent] = Action.async { implicit request =>
    block(request) map { model =>
      val repr: JValue = serialize(model)
      render {
        case Accepts.Xml() => xmlResult(repr)
        case Accepts.Json() => jsonResult(repr)
      }
    }
  }

  private def xmlResult(repr: JValue)
               (implicit request: RequestHeader, formats: Formats, codec: Codec) =
    Ok(toXml(repr).toString).as(ContentTypes.XML)

  private def jsonResult(repr: JValue)
               (implicit request: RequestHeader, formats: Formats, codec: Codec) =
    Ok(compact(jsonRender(repr))).as(ContentTypes.JSON)

  private def serialize[M](model: M)(implicit formats: Formats): JValue =
    Extraction.decompose(model)
}

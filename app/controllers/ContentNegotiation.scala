package controllers

import org.json4s.{Extraction, _}
import play.api.mvc._

import scala.concurrent.Future
import org.json4s._
import org.json4s.Xml.toXml
import org.json4s.jackson.Serialization
import org.json4s.native.JsonMethods.{render => jsonRender, _}
import play.api.http.ContentTypes

/**
  * A generalized Content Negotiation example using json4s
  */
trait ContentNegotiation extends AcceptExtractors with Rendering with Results {

  /**
    * The json4s default serialization formats, implicitly required for `negotiate` methods
    * You may choose to use one or more different libraries here, depending on your requirements.  If your library
    * doesn't have a Scala DSL or implicit marshaling helpers, then write your own (i.e. like json4s `Formats` or
    * play-json `Writes[T]` types).
    */
  implicit val formats = Serialization.formats(NoTypeHints)

  def negotiate[M](model: M)(implicit request: RequestHeader, formats: Formats, codec: Codec): Result = {
    val repr: JValue = serialize(model)
    render {
      case Accepts.Xml() => xmlResult(repr)
      case Accepts.Json() => jsonResult(repr)
    }
  }

  def negotiateAsync[M](model: M)(implicit request: RequestHeader, formats: Formats, codec: Codec): Future[Result] = {
    val repr: JValue = serialize(model)
    render.async {
      case Accepts.Xml() => Future.successful(xmlResult(repr))
      case Accepts.Json() => Future.successful(jsonResult(repr))
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

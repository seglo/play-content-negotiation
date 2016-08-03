A Play! Content Negotiation example using json4s
================================================

This project demonstrates simple JSON and XML content negotiation given the appropriate `Accept` header.

[json4s](http://json4s.org/) is used to reduce the marshalling boilerplate in the example and because it also supports
XML out of the box.  This is not necessarily a good reason to use json4s.  You may want to choose a different library
(or two, or three) depending on your own your marshalling requirements.

To use, mix-in the [`ContentNegotiation`](app/controllers/ContentNegotiation.scala) trait to access the
`negotiateAction` method to return a Play! `Action`.  This Action will use the standard
[Play! Content Negotiation](https://www.playframework.com/documentation/2.5.x/ScalaContentNegotiation#Content) features
to determine the appropriate marshalling and Content Type of the `Result`.

The `negotiateAction` Action takes in a `Function[Request[AnyContent], Future[Content[M]]]` that returns the model to marshal,
and an implicit marshalling library formatter for the model being marshaled.  The action currently accepts a json4s
formatter (`Formats`), but this can be replaced with one or more different marshaling libraries, depending on your
requirements.

# XML Result

```shell
$ curl -H "Accept: application/xml" http://localhost:9000/project
<name>test</name><startDate>2016-08-02T19:01:37.818Z</startDate><lang><name>Scala</name><version>2.75</version></lang><teams><role>QA</role><members><name>John Doe</name><experience>5</experience></members><members><name>Mike</name><experience>3</experience></members></teams><teams><role>Impl</role><members><name>Mark</name><experience>4</experience></members><members><name>Mary</name><experience>5</experience></members><members><name>Nick Noob</name><experience>1</experience></members></teams>
```

# JSON Result

```shell
$ curl -H "Accept: application/json" http://localhost:9000/project
{"name":"test","startDate":"2016-08-02T19:02:31.562Z","lang":{"name":"Scala","version":2.75},"teams":[{"role":"QA","members":[{"name":"John Doe","experience":5},{"name":"Mike","experience":3}]},{"role":"Impl","members":[{"name":"Mark","experience":4},{"name":"Mary","experience":5},{"name":"Nick Noob","experience":1}]}]}
```

# Content Negotiation & Media Type discovery

Also related to Content Negotiation is to provide a way to inform the user of supported media types in a response when
their provided representation is not available.  [Daniel Westheide](http://danielwestheide.com) has a presentation and
library to extend Play! to support this:

* Relevant [Presentation](http://danielwestheide.com/talks/playbb2015/slides/index.html#/17)
* [`play-content-negotiation`](https://github.com/restfulscala/play-content-negotiation/) GitHub project

# Resources

* json4s
  * http://json4s.org/
* Play! 2.5.x Content Negotiation & media range support
  * https://www.playframework.com/documentation/2.5.x/ScalaContentNegotiation#Content
* HTTP RFC
  * Content Negotiation: https://www.w3.org/Protocols/rfc2616/rfc2616-sec12.html
  * Header Definitions: https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html


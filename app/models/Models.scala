package models

import java.util.Date

case class Project(name: String, startDate: Date, lang: Option[Language], teams: List[Team])
case class Language(name: String, version: Double)
case class Team(role: String, members: List[Employee])
case class Employee(name: String, experience: Int)

/*
val project = Project("test", new Date, Some(Language("Scala", 2.75)), List(
  Team("QA", List(Employee("John Doe", 5), Employee("Mike", 3))),
  Team("Impl", List(Employee("Mark", 4), Employee("Mary", 5), Employee("Nick Noob", 1)))))

JValue representation:

JObject(List((name,JString(test)), (startDate,JString(2016-08-02T17:34:39.879Z)), (lang,JObject(List((name,JString(Scala)), (version,JDouble(2.75))))), (teams,JArray(List(JObject(List((role,JString(QA)), (members,JArray(List(JObject(List((name,JString(John Doe)), (experience,JInt(5)))), JObject(List((name,JString(Mike)), (experience,JInt(3))))))))), JObject(List((role,JString(Impl)), (members,JArray(List(JObject(List((name,JString(Mark)), (experience,JInt(4)))), JObject(List((name,JString(Mary)), (experience,JInt(5)))), JObject(List((name,JString(Nick Noob)), (experience,JInt(1))))))))))))))

Default XML representation:

<name>test</name>
<startDate>2016-08-02T17:15:21.489Z</startDate>
<lang>
    <name>Scala</name>
    <version>2.75</version>
</lang>
<teams>
    <role>QA</role>
    <members>
        <name>John Doe</name>
        <experience>5</experience>
    </members>
    <members>
        <name>Mike</name>
        <experience>3</experience>
    </members>
</teams>
<teams>
    <role>Impl</role>
    <members>
        <name>Mark</name>
        <experience>4</experience>
    </members>
    <members>
        <name>Mary</name>
        <experience>5</experience>
    </members>
    <members>
        <name>Nick Noob</name>
        <experience>1</experience>
    </members>
</teams>

Default JSON representation:

{
  "name":"test",
  "startDate":"2016-08-02T17:19:00.238Z",
  "lang":{
    "name":"Scala",
    "version":2.75
  },
  "teams":[{
    "role":"QA",
    "members":[{
      "name":"John Doe",
      "experience":5
    },{
      "name":"Mike",
      "experience":3
    }]
  },{
    "role":"Impl",
    "members":[{
      "name":"Mark",
      "experience":4
    },{
      "name":"Mary",
      "experience":5
    },{
      "name":"Nick Noob",
      "experience":1
    }]
  }]
}
 */

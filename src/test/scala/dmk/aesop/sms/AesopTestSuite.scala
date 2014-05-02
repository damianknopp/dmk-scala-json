package dmk.aesop.sms

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.scalatest.FunSuite

import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json

//@RunWith(classOf[JUnitRunner]) 
class AesopTestSuite extends FunSuite {
  var sampleAesopInstitutionJson: String = null
  var sampleAesopInstitutionJson2: String = null
  var sampleAesopInstitutionJson3: String = null
  var availJobs: String = null

  @Before def setup = {
    sampleAesopInstitutionJson = dmk.aesop.sms.AesopSampleData.sampleAesopInstitutionJson
    sampleAesopInstitutionJson2 = dmk.aesop.sms.AesopSampleData.sampleAesopInstitutionJson2
    sampleAesopInstitutionJson3 = dmk.aesop.sms.AesopSampleData.sampleAesopInstitutionJson3
    availJobs = dmk.aesop.sms.AesopSampleData.availJobs
  }

  @Test
  def canary = {
    assertTrue(true)
  }

  @Test def simpleParse = {
    val j = """{ "key": "value" }"""
    val root: JsValue = Json.parse(j)
    val v1 = (root \ "key").as[String]
    assertEquals(v1, "value")
  }

  @Test def simpleParseObjects = {
    val root: JsValue = Json.parse(sampleAesopInstitutionJson)
    val v1 = (root \ "Institution")
    val name = (v1 \ "Name").as[String]
    assertEquals(name, "Easton High School")
  }

  @Test def simpleParseObjectWithValidateNoNulls = {
    val root: JsValue = Json.parse(sampleAesopInstitutionJson2)
    val obj: JsResult[AesopInstitution] = root.validate[AesopInstitution]
    val tmp = obj.asOpt.getOrElse("failed to parse")
    assertTrue(tmp != "failed to parse")
    val root2: JsValue = Json.parse(sampleAesopInstitutionJson2)
    val v1 = root2.as[AesopInstitution]
    assertEquals(v1.name, "Easton High School")
    assertEquals(v1.street1, "723 Mecklenburg Avenue")
    assertEquals(v1.street2, None)
    assertEquals(v1.city, "Easton")
    assertEquals(v1.state, "MD")
    assertEquals(v1.zip, "21601")
    assertEquals(v1.phone, "(410) 555-5555")
    assertEquals(v1.showLocationPhoneToSub, true)
  }

  @Test def simpleParseObjectWithValidateNulls = {
    val root: JsValue = Json.parse(sampleAesopInstitutionJson3)
    val obj: JsResult[AesopInstitution] = root.validate[AesopInstitution]
    val tmp = obj.asOpt.getOrElse("failed to parse")
    assertTrue(tmp != "failed to parse")
    val root2: JsValue = Json.parse(sampleAesopInstitutionJson2)
    val v1 = root2.as[AesopInstitution]
    assertEquals(v1.name, "Easton High School")
    assertEquals(v1.street1, "723 Mecklenburg Avenue")
    assertEquals(v1.street2, None)
    assertEquals(v1.street3, None)
    assertEquals(v1.city, "Easton")
    assertEquals(v1.state, "MD")
    assertEquals(v1.zip, "21601")
    assertEquals(v1.phone, "(410) 555-5555")
    assertEquals(v1.showLocationPhoneToSub, true)
  }

  @Test def simpleWritesTest = {
    val institution = new AesopInstitution("Easton High", "723 Mecklenburg Avenue",
        None, None, None, "Easton", "MD", "21601", "(410) 555-5555", true)
    val json = Json.toJson(institution)
    println(institution)
    println(json)
    val root: JsValue = Json.parse(json.toString)
    val v1 = (root \ "State").as[String]
    assertEquals(v1, "MD")
    assertEquals((root \ "Name").as[String], "Easton High")
    assertEquals((root \ "Street2").asOpt[String], None)
  }
  
  @Test def nestedParse = {
    println(availJobs)
    val root: JsValue = Json.parse(availJobs)
    val v1 = (root \ "availJobs").as[AesopAvailJobs]
    assertTrue(v1.list.size == 2)
  }
}
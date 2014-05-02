package dmk.aesop.sms

import java.util.Date

import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.Format
import play.api.libs.json.JsPath
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Json.toJson
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class AesopAvailJobs(fromDb: Boolean, fromCache: Boolean, list: Seq[AesopWorkerOpening]){}

object AesopAvailJobs{

  implicit val aesopAvailJobsFormat: Format[AesopAvailJobs] = (
		  (JsPath \ "fromDb").format[Boolean] and 
		  (JsPath \ "fromCache").format[Boolean] and
		  (JsPath \ "list").format[Seq[AesopWorkerOpening]]
		 )(AesopAvailJobs.apply, unlift(AesopAvailJobs.unapply))

}

/**
 * 
 *      "Id": 55555555,
        "TenantId": 55555,
        "Start": "2014-01-10T07:35:00",
        "EndDate": "2014-01-25T00:00:00",
        "WorkerFirstName": "NoSuchFirst",
        "WorkerLastName": "NoSuchLast",
        "WorkerTitle": "Physical Science/Environmental",
        "WorkerReportLocation": "Main Office",
        "WorkerEmail": "nosuchemail@nosuchdoma.in",
        "WorkerIsVacancy": false,
        "ShowWorkerEmailAddrToSub": true,
        "HasNotes": true,
        "HasAttachments": false,
        "CanCancel": false,
        "SubWouldBePunishedForCancellingJob": false,
        "AwaitingFeedback": false,
        "CanViewFeedback": true,
        "CanViewFeedbackRating": true,
        "FeedbackRatingFromSub": 4,
        "FeedbackRatingFromWorker": null
 * 
 */
/** 
 *  Top level object for vacancies
 *  a worker, can have many opening across a date range
 */
case class AesopWorkerOpening(
		id: Int, tenantId: Int, start: Date, endDate: Date,
		workerFirstName: String, workerLastName: String,
		workerTitle: String, workerReportLocation: Option[String],
		workerEmail: Option[String], workerIsVacancy: Boolean,
		showWorkerEmailAddrToSub: Boolean, hasNotes: Boolean,
		hasAttachments: Boolean, canCancel: Boolean,
		subWouldBePunishedForCancellingJob: Boolean,
		awaitingFeedback: Boolean,
		canViewFeedback: Boolean, canViewFeedbackRating: Boolean,
		feedbackRatingFromSub: Option[Int], feedbackRatingFromWorker: Option[String],
		items: List[AesopSubstituteJob]){}

object AesopWorkerOpening{
	
  implicit val aesopWorkerOpeningFormat: Format[AesopWorkerOpening] = (
		  (JsPath \ "Id").format[Int] and 
		  (JsPath \ "TenantId").format[Int] and
		  (JsPath \ "Start").format[Date] and 
		  (JsPath \ "EndDate").format[Date] and 
		  (JsPath \ "WorkerFirstName").format[String] and 
		  (JsPath \ "WorkerLastName").format[String] and 
		  (JsPath \ "WorkerTitle").format[String] and 
		  (JsPath \ "WorkerReportLocation").formatNullable[String] and 
		  (JsPath \ "WorkerEmail").formatNullable[String] and 
		  (JsPath \ "WorkerIsVacancy").format[Boolean] and 
		  (JsPath \ "ShowWorkerEmailAddrToSub").format[Boolean] and 
		  (JsPath \ "HasNotes").format[Boolean] and 
		  (JsPath \ "HasAttachments").format[Boolean] and 
		  (JsPath \ "CanCancel").format[Boolean] and 
		  (JsPath \ "SubWouldBePunishedForCancellingJob").format[Boolean] and 
		  (JsPath \ "AwaitingFeedback").format[Boolean] and 
		  (JsPath \ "CanViewFeedback").format[Boolean] and 
		  (JsPath \ "CanViewFeedbackRating").format[Boolean] and 
		  (JsPath \ "FeedbackRatingFromSub").formatNullable[Int] and 
  		  (JsPath \ "FeedbackRatingFromWorker").formatNullable[String] and 
  		  (JsPath \ "Items").format[List[AesopSubstituteJob]]
		 )(AesopWorkerOpening.apply, unlift(AesopWorkerOpening.unapply))
}


/**
 *             "Id": 555555555,
            "Start": "2014-01-10T07:35:00",
            "Duration": "07:30:00",
            "End": "2014-01-10T15:05:00",
            "ShiftType": 1,
            "NonPayTime": "00:00:00",
            "Institution": {
            },
            "BreakDuration": "00:01:00",
            "VacancyId": 55555555,
            "CanCancel": false
 * 
 */
case class AesopSubstituteJob(
    id: Int, start: Date, duration: String, end: Date,
    shiftType: Int, nonPayTime: String, breakDuration: String,
    vacancyId: Int, canCancel: Boolean,
    institution: AesopInstitution) {
}

object AesopSubstituteJob{

  implicit val aesopSubstituteJobFormat: Format[AesopSubstituteJob] = (
		  (JsPath \ "Id").format[Int] and
		  (JsPath \ "Start").format[Date] and
		  (JsPath \ "Duration").format[String] and
		  (JsPath \ "End").format[Date] and
		  (JsPath \ "ShiftType").format[Int] and
		  (JsPath \ "NonPayTime").format[String] and
		  (JsPath \ "BreakDuration").format[String] and
		  (JsPath \ "VacancyId").format[Int] and
		  (JsPath \ "CanCancel").format[Boolean] and
		  (JsPath \ "Institution").format[AesopInstitution]		  
      )(AesopSubstituteJob.apply, unlift(AesopSubstituteJob.unapply))

}

/**
 * "Institution": {
                "Name": "Easton High School",
                "Street1": "723 Mecklenburg Avenue",
                "Street2": null,
                "Street3": null,
                "Street4": null,
                "City": "Easton",
                "State": "MD",
                "Zip": "21601    ",
                "Phone": "(410) 822-4180",
                "ShowLocationPhoneToSub": true
            },
 */
case class AesopInstitution(var name: String, var street1: String
			,var street2: Option[String], var street3: Option[String], var street4: Option[String]
			,var city: String, var state: String, var zip: String
			,var phone: String, var showLocationPhoneToSub: Boolean){
}

object AesopInstitution{
//  implicit val aesopInstitutionFormat:Format[AesopInstitution] = Json.format[AesopInstitution]

    implicit val aesopInstitutionFormat : Format[AesopInstitution] = (
  		  (JsPath \ "Name").format[String] and
		  (JsPath \ "Street1").format[String] and
		  (JsPath \ "Street2").formatNullable[String] and
		  (JsPath \ "Street3").formatNullable[String] and
		  (JsPath \ "Street4").formatNullable[String] and
		  (JsPath \ "City").format[String] and
		  (JsPath \ "State").format[String] and
		  (JsPath \ "Zip").format[String] and
		  (JsPath \ "Phone").format[String] and
		  (JsPath \ "ShowLocationPhoneToSub").format[Boolean]
      )(AesopInstitution.apply, unlift(AesopInstitution.unapply))
  
  val aesopInstitutionReadsNoStreet :Reads[AesopInstitution] = (
  		  (JsPath \ "Name").read[String] and
		  (JsPath \ "Street1").read[String] and
		  (JsPath \ "Street2").readNullable[String] and
		  (JsPath \ "Street3").readNullable[String] and
		  (JsPath \ "Street4").readNullable[String] and
		  (JsPath \ "City").read[String] and
		  (JsPath \ "State").read[String] and
		  (JsPath \ "Zip").read[String] and
		  (JsPath \ "Phone").read[String] and
		  (JsPath \ "ShowLocationPhoneToSub").read[Boolean]
      )(AesopInstitution.apply _)

// writes option1
//      implicit val aesopInstitutionWrites: Writes[AesopInstitution] = (
//    	  (JsPath \ "Name").write[String] and
//    	  (JsPath \ "Street1").write[String] and
//		  (JsPath \ "Street2").write[Option[String]] and
//		  (JsPath \ "Street3").write[Option[String]] and
//		  (JsPath \ "Street4").write[Option[String]] and
//		  (JsPath \ "City").write[String] and
//		  (JsPath \ "State").write[String] and
//		  (JsPath \ "Zip").write[String] and
//		  (JsPath \ "Phone").write[String] and
//		  (JsPath \ "ShowLocationPhoneToSub").write[Boolean]
//      )(unlift(AesopInstitution.unapply))
      
      import play.api.libs.json.Json._
      object AesopInstitutionWritesNoStreet4 extends Writes[AesopInstitution] {
    		  def writes(in: AesopInstitution) = Json.obj(
    				  "Name" -> toJson(in.name),
    				  "Street1" -> toJson(in.street1),
    				  "Street2" -> toJson(in.street2),
    				  "Street3" -> toJson(in.street3),
    				  "City" -> toJson(in.city),
    				  "State" -> toJson(in.state),
    				  "Zip" -> toJson(in.zip),
    				  "Phone" -> toJson(in.phone),
    				  "ShowLocationPhoneToSub" -> toJson(in.showLocationPhoneToSub)
    		  )
		}
}
package dmk.aesop.sms;

import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath
import play.api.libs.json.JsString
import play.api.libs.json.Writes

object JsPathValidationWrites {
	implicit val JsPathWrites = Writes[JsPath](p => JsString(p.toString))

	implicit val ValidationErrorWrites = Writes[ValidationError](e => JsString(e.message))
	
//	implicit val jsonValidateErrorWrites : Writes[(JsPath, Seq[ValidationError])] = (
//			(JsPath \ "path").write[JsPath] and
//			(JsPath \ "errors").write[Seq[ValidationError]]
//			tupled
//	)
}
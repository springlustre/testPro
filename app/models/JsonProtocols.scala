package models

import play.api.libs.json.{JsObject, JsValue, Json, Writes}
import models.Tables._
/**
 * User: Huangshanqi
 * Date: 2015/12/4
 * Time: 11:13
 */
trait JsonProtocols extends BaseJsonProtocols{

  override def jsonResult(errorCode: Int, errorMsg: String) = {
    Json.obj("errcode" -> errorCode, "errmsg" -> errorMsg)
  }

  override def jsonResult(errorCode: Int, errorMsg: String, data: JsObject) = {
    Json.obj("errcode" -> errorCode, "errmsg" -> errorMsg) ++ data
  }

  override def successResult(data: JsObject) = success ++ data

  override val success = jsonResult(0, "ok")

  implicit val ConsultantRow: Writes[ConsultantRow] = new Writes[ConsultantRow] {
    override def writes(obj: ConsultantRow): JsValue = {
      Json.obj(
        "id"-> obj.id,
        "userid" ->obj.userid,
        "introduce" ->obj.introduce,
        "proField" ->obj.profield,
        "industry" ->obj.industry
      )
    }
  }

  implicit val UserRow: Writes[UserRow] = new Writes[UserRow] {
    override def writes(obj: UserRow): JsValue = {
      Json.obj(
        "id"-> obj.id,
        "loginname" ->obj.loginname,
        "name" ->obj.name,
        "password" ->obj.password,
        "token" ->obj.token,
        "phone"->obj.phone,
        "email"->obj.email,
        "sex"->obj.sex,
        "birthday"->obj.birthday,
        "pic"->obj.pic,
        "imToken"->obj.imtoken,
        "createTime"->obj.createtime,
        "updateTime"->obj.updatetime,
        "locationX"->obj.locationx,
        "locationY"->obj.locationy
      )
    }
  }


}

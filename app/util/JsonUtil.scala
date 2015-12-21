package util

import play.api.libs.json.{JsObject, Json}

/**
 * Created by Liboren's on 2015/8/31.
 */
object JsonUtil {


  def jsonResult(errorCode: Int, errorMsg: String) = {
    Json.obj("errcode" -> errorCode, "errmsg" -> errorMsg)
  }

  def jsonResult(errorCode: Int, errorMsg: String, data: JsObject) = {
    Json.obj("errcode" -> errorCode, "errmsg" -> errorMsg) ++ data
  }

  def successResult(data: JsObject) = success ++ data

  val success = jsonResult(0, "ok")

  /*开发时调试用 生产环境只需改这一句就能关闭所有的调试打印信息 */
  def debugPrintln(msg: Any) = {
    println(msg)
  }

}

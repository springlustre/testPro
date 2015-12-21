package controllers

/**
 * Created by springlustre on 2015/11/6.
 */

import java.io.File
import javax.inject.{Inject, Singleton}

import models._
import play.api.cache.Cache
import util.{ CodeGenerator}
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory

import play.api.libs.json.{JsUndefined, Json}

import play.api.mvc.{Action, Controller}
import util.JsonUtil._

import scala.concurrent.Future

import util.JsonUtil._

import scala.concurrent.duration._
import play.api.Play.current

@Singleton
class userApp @Inject()(
                         userMod: User,
                         actionUtils: ActionUtils
                         ) extends Controller{

  import actionUtils._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext


  val log = LoggerFactory.getLogger(this.getClass)
  private val authAction = LoggingAction
  private val idCacheKey = "cache.user.id."


  log.info(s"User Controller: ${System.currentTimeMillis()}")


  /**
   * 创建用户
   */
  def register=authAction.async{implicit request=>
    val jsonData=request.body.asJson.get
    val loginname=(jsonData \ "loginname").asOpt[String]
    val name=(jsonData \ "name").as[String]
    val password=(jsonData \ "password").as[String]
    val token=(jsonData \ "token").asOpt[String]
    val phone=(jsonData \ "phone").asOpt[String]
    val email=(jsonData \ "email").asOpt[String]
    val sex=(jsonData \ "sex").asOpt[String]
    val birthday=(jsonData \ "biethday").asOpt[String]
    val birthyear=(jsonData \ "birthyear").asOpt[String]
    val pic=(jsonData \ "pic").asOpt[String]

    userMod.createUser(None,loginname,name,password,token,phone,email,sex,birthday,birthyear,pic).map { res =>
      if (res == 1) {
        Ok(success)
      } else {
        Ok(jsonResult(10000,"failed!"))
      }
    }
  }

  /**
   * 获取单个用户信息
   * */
  def getUserDetail= authAction.async { implicit request =>
    val jsonData=request.body.asJson.get
    val userId=(jsonData \ "loginname").as[Long]
    userMod.getUserById(userId).map { user =>
      Ok(successResult(Json.obj("data" -> Json.obj(
      "userid"->user.get.id,
      "loginname"->user.get.loginname,
      "name"->user.get.name,
        "password"->user.get.password,
        "token"->user.get.token,
        "phone"->user.get.phone,
        "email"->user.get.email,
        "sex"->user.get.sex,
        "birthday"->user.get.birthday,
        "birthyear"->user.get.birthyear,
        "pic"->user.get.pic
      ))))
    }
  }


  /**修改登陆信息
  * */
  def updateLoginInfo=authAction.async{request=>
    val jsonData=request.body.asJson.get
    val userId=(jsonData \ "userId").as[Long]
    val loginname=(jsonData \ "loginname").asOpt[String]
    val name=(jsonData \ "name").as[String]
    val token=(jsonData \ "token").asOpt[String]
    val phone=(jsonData \ "phone").asOpt[String]
    val email=(jsonData \ "email").asOpt[String]

    userMod.updateLoginInfo(userId,loginname,name,token,phone,email).map{res=>
      if(res==1){
        Ok(success)
      }else{
        Ok(jsonResult(10001,"failed"))
      }
    }
  }

  /**修改资料
   * @return
   */
  def modifyUserInfo=authAction.async{request=>
    val jsonData=request.body.asJson.get
    val userId=(jsonData \ "userId").as[Long]
    val sex=(jsonData \ "loginname").asOpt[String]
    val birthday=(jsonData \ "name").asOpt[String]
    val birthyear=(jsonData \ "token").asOpt[String]
    val pic=(jsonData \ "phone").asOpt[String]

    userMod.modefyUserInfo(userId,sex,birthday,birthyear,pic).map{res=>
      if(res==1){
        Ok(success)
      }else{
        Ok(jsonResult(10002,"failed"))
      }
    }
  }



  def oldpassword = authAction.async { implicit request =>
    val jsonData = request.body.asJson.get
    val id=(jsonData \ "id").as[Long]
    val psw = (jsonData \ "oldpassword").as[String]
    // debugPrintln(psw)
    userMod.getUserById(id).map {userRow =>
      val md5Hex = DigestUtils.md5Hex(psw+userRow.get.email)
      // val cc= md5Hex == userRow.get.password
      //debugPrintln("aa"+cc)
      if(userRow.get.password == md5Hex){
        Ok(success)
      }else{
        Ok(jsonResult(1,""))
      }
    }
  }


  /* to change the password * */
//
//  def changepsw = authAction.async { implicit request =>
//
//    val jsonData = request.body.asJson.get
//    val oldpsw = (jsonData \ "oldpassword").as[String]
//    val psw = (jsonData \ "password").as[String]
//    val id=(jsonData \ "id").as[Long]
//    userMod.getUserById(id).flatMap {userRow =>
//      val oldmd5Hex = DigestUtils.md5Hex(oldpsw+userRow.get.email)
//      val md5Hex = DigestUtils.md5Hex(psw+userRow.get.email)
//      userMod.resetPsw(oldmd5Hex, userRow.get.email, md5Hex).map { res =>
//        log.info(s"${userRow.get.email} change password")
//        if (res) {
//          Ok(success)
//        } else {
//          Ok(jsonResult(1,""))
//        }
//      }
//    }
//  }






}



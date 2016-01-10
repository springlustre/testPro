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
import scala.concurrent.duration._
import play.api.Play.current
import util.TimeFormatUtil._
@Singleton
class user @Inject()(
                         userDao: UserDao,
                         actionUtils: ActionUtils
                         ) extends Controller with JsonProtocols{

  import actionUtils._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext


  val log = LoggerFactory.getLogger(this.getClass)



  /**
   * 创建用户
   */
  def register=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val loginname=(jsonData \ "loginname").as[String]
    val password=(jsonData \ "password").as[String]
    val token=(jsonData \ "token").asOpt[String]
    val imToken=(jsonData \ "imToken").asOpt[String]
    val phone=(jsonData \ "phone").as[String]
    val createTime= System.currentTimeMillis()
    userDao.registeUser(loginname,password,phone,token.getOrElse(""),imToken.getOrElse(""),createTime).map{res=>
      if(res>0){
        Ok(successResult(Json.obj("id"->res)))
      }
      else{
        Ok(jsonResult(10002,"注册失败！"))
      }
    }
  }


  /**登陆*/
  def login=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val loginname=(jsonData \ "loginname").as[String]
    val password=(jsonData \ "password").as[String]
    println(loginname+password)
    userDao.login(loginname,password).map{res=>
      if(res.isDefined){
        Ok(successResult(Json.obj("data"->res.get)))
      }else{
        Ok(jsonResult(10003,"登录失败"))
      }
    }
  }

  /**更新位置信息*/
  def updateLocation=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    println(jsonData)
    val userid=(jsonData \ "userid").as[String].toLong
    val locationX=(jsonData \ "locationX").as[Double]
    val locationY=(jsonData \ "locationY").as[Double]
    val time=(jsonData \ "updateTime").as[String]
    val updateTime=toTimeStamp(time)
    println("---------"+locationX+updateTime)
    userDao.updateLocation(userid,locationX,locationY,updateTime).map{res=>
      if(res>0){
        Ok(successResult(Json.obj("data"->"更新位置成功！")))
      }else{
        Ok(jsonResult(10004,"更新位置失败"))
      }
    }
    //Future.successful(Ok(success))
  }

  /**
   * 获取单个用户信息
   * */
  def getUserDetail= Action.async { implicit request =>
    val jsonData=request.body.asJson.get
    val userId=(jsonData \ "loginname").as[Long]
    userDao.getUserById(userId).map { user =>
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
        "pic"->user.get.pic
      ))))
    }
  }

  /**保存信息*/
  def savePersonInfo=Action.async{implicit request=>
    val jsonData=request.body.asJson.get
    val userid=(jsonData \ "userid").as[String].toLong
    val name=(jsonData \ "name").as[String]
    val password=(jsonData \ "password").as[String]
    val phone=(jsonData \ "phone").as[String]
    val sex=(jsonData \ "sex").as[String]
    val birthday=(jsonData \ "birthday").as[String]
    userDao.updatePersonInfo(userid,name,password,phone,sex,birthday).map{res=>
     if(res>0){
       Ok(success)
     }else{
       Ok(jsonResult(10000,"保存失败！"))
     }
    }
  }


  /**get consumer info*/
  def getConsumerInfo=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    println(jsonData)
    val userid=(jsonData \ "userid").as[String].toLong
    userDao.getConsumer(userid).map{res=>
      if(res.isDefined){
        Ok(successResult(Json.obj(
          "id"->res.get.id,
          "userid"->res.get.userid,
          "profession"->res.get.profession,
          "company"->res.get.company,
          "position"->res.get.position,
          "site"->res.get.site,
          "introduce"->res.get.introduce
        )))
      }else{
        Ok(jsonResult(10000,"获取失败！"))
      }
    }
  }
  /**register consumer*/
  def registerConsumer=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    val introduce=(jsonData \ "introduce").as[String]
    val profession=(jsonData \ "profession").as[String]
    val company=(jsonData \ "company").as[String]
    val position=(jsonData \ "position").as[String]
    val place=(jsonData \ "place").as[String]
    println(jsonData)
    userDao.updateConsumer(userid,introduce,profession,company,position,place).map{res=>
      if(res>0L){
        Ok(success)
      }else{
        Ok(jsonResult(10011,"保存失败！"))
      }
    }
  }

  /**修改登陆信息
  * */
//  def updateLoginInfo=Action.async{request=>
//    val jsonData=request.body.asJson.get
//    val userId=(jsonData \ "userId").as[Long]
//    val loginname=(jsonData \ "loginname").asOpt[String]
//    val name=(jsonData \ "name").as[String]
//    val token=(jsonData \ "token").asOpt[String]
//    val phone=(jsonData \ "phone").asOpt[String]
//    val email=(jsonData \ "email").asOpt[String]
//
//    userDao.updateLoginInfo(userId,loginname,name,token,phone,email).map{res=>
//      if(res==1){
//        Ok(success)
//      }else{
//        Ok(jsonResult(10001,"failed"))
//      }
//    }
//  }
//
//  /**修改资料
//   * @return
//   */
//  def modifyUserInfo=Action.async{request=>
//    val jsonData=request.body.asJson.get
//    val userId=(jsonData \ "userId").as[Long]
//    val sex=(jsonData \ "loginname").asOpt[String]
//    val birthday=(jsonData \ "name").asOpt[String]
//    val birthyear=(jsonData \ "token").asOpt[String]
//    val pic=(jsonData \ "phone").asOpt[String]
//
//    userDao.modefyUserInfo(userId,sex,birthday,birthyear,pic).map{res=>
//      if(res==1){
//        Ok(success)
//      }else{
//        Ok(jsonResult(10002,"failed"))
//      }
//    }
//  }



//  def oldpassword = Action.async { implicit request =>
//    val jsonData = request.body.asJson.get
//    val id=(jsonData \ "id").as[Long]
//    val psw = (jsonData \ "oldpassword").as[String]
//    userDao.getUserById(id).map {userRow =>
//      val md5Hex = DigestUtils.md5Hex(psw+userRow.get.email)
//      if(userRow.get.password == md5Hex){
//        Ok(success)
//      }else{
//        Ok(jsonResult(1,""))
//      }
//    }
//  }


  /* to change the password * */
//
//  def changepsw = authAction.async { implicit request =>
//
//    val jsonData = request.body.asJson.get
//    val oldpsw = (jsonData \ "oldpassword").as[String]
//    val psw = (jsonData \ "password").as[String]
//    val id=(jsonData \ "id").as[Long]
//    userDao.getUserById(id).flatMap {userRow =>
//      val oldmd5Hex = DigestUtils.md5Hex(oldpsw+userRow.get.email)
//      val md5Hex = DigestUtils.md5Hex(psw+userRow.get.email)
//      userDao.resetPsw(oldmd5Hex, userRow.get.email, md5Hex).map { res =>
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



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
//    println(loginname+password)
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
//    println(jsonData)
    val userid=(jsonData \ "userid").as[String].toLong
    val locationX=(jsonData \ "locationX").as[Double]
    val locationY=(jsonData \ "locationY").as[Double]
    val time=(jsonData \ "updateTime").as[String]
    val updateTime=toTimeStamp(time)
//    println("---------"+locationX+updateTime)
    userDao.updateLocation(userid,locationX,locationY,updateTime).map{res=>
      if(res>0){
        Ok(successResult(Json.obj("data"->"更新位置成功！")))
      }else{
        Ok(jsonResult(10004,"更新位置失败"))
      }
    }
    //Future.successful(Ok(success))
  }

  /**修改用户头像*/
  def updateUserPhoto=Action.async {implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    val picUrl=(jsonData \ "picUrl").as[String]
    userDao.updateUserPhoto(userid,picUrl).map{res=>
      if(res>0){
        Ok(success)
      }else{
        Ok(jsonResult(10000,"修改头像失败！"))
      }
    }
  }

  def getUserPhoto=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    userDao.getUserPhoto(userid).map{res=>
      if(res.isDefined){
        Ok(successResult(Json.obj(
          "pic"->res.get
        )))
      }else{
        Ok(successResult(Json.obj(
          "pic"->"http://a7d81dff66cf35d21a1c.b0.upaiyun.com/apicloud/fbd830eea7e739b66bb0554f49692613.png"
        )))
      }
    }
  }

  /**
   * 获取单个用户信息
   * */
  def getUserDetail= Action.async { implicit request =>
    val jsonData=Json.parse(request.body.asText.get)
    val userId=(jsonData \ "userid").as[String].toLong
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


  def getUserByImUserid=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val imUserid=(jsonData \ "imUserid").as[String]
    userDao.getUserByImUserid(imUserid).map { user =>
      if(user.isDefined){
        Ok(successResult(Json.obj("data" -> Json.obj(
          "userid"->user.get.id,
          "name"->user.get.name,
          "pic"->user.get.pic,
          "imUserid"->user.get.imuserid
        ))))
      }else{
        Ok(jsonResult(10000,"不存在"))
      }
    }
  }





  /**保存信息*/
  def savePersonInfo=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
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


  /**个人信息页 get consumer info*/
  def getConsumerInfo=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
//    println(jsonData)
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
//    println(jsonData)
    userDao.updateConsumer(userid,introduce,profession,company,position,place).map{res=>
      if(res>0L){
        Ok(success)
      }else{
        Ok(jsonResult(10011,"保存失败！"))
      }
    }
  }






  /**保存图片到pic*/
  def savePic=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
//    val jsonData=Json.obj("userid"->"10","picUrl"->"111111","picNo"->1)
    val userid=(jsonData \ "userid").as[String].toLong
    val picUrl=(jsonData \ "picUrl").as[String]
    val picNo=(jsonData \ "picNo").as[Int]
    if(picNo==1){
      userDao.updateUserPhoto(userid,picUrl).flatMap{r=>
        if(r>1){
          userDao.getPicByUserNo(userid,picNo).flatMap{res=>
            if(res.isDefined){
              userDao.updatePic(res.get,picUrl).map{res=>
                if(res>0){
                  Ok(success)
                }else{
                  Ok(jsonResult(10000,"保存失败!"))
                }
              }
            }else{
              userDao.insertPic(userid,picUrl,picNo).map{res=>
                if(res>0){
                  Ok(success)
                }else{
                  Ok(jsonResult(10000,"保存失败!"))
                }
              }
            }
          }
        }else{
          Future.successful(Ok(jsonResult(10000,"保存失败!")))
        }
      }
    }else {
      userDao.getPicByUserNo(userid, picNo).flatMap { res =>
        if (res.isDefined) {
          userDao.updatePic(res.get, picUrl).map { res =>
            if (res > 0) {
              Ok(success)
            } else {
              Ok(jsonResult(10000, "保存失败!"))
            }
          }
        } else {
          userDao.insertPic(userid, picUrl, picNo).map { res =>
            if (res > 0) {
              Ok(success)
            } else {
              Ok(jsonResult(10000, "保存失败!"))
            }
          }
        }
      }
    }
  }


  def getPic=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
//    println("getpic=========="+jsonData)
    val userid=(jsonData \ "userid").as[String].toLong
    userDao.getPicByUserId(userid).map{seq=>
      seq.map{pic=>
        Json.obj(
          "picId"->pic.id,
          "userid"->pic.userid,
          "picUrl"->pic.url,
          "picNo"->pic.no)
      }
    }.map{data=>
      Ok(successResult(Json.obj("data"->data)))
    }
  }

  /**接受lecture*/
  def acceptLecture=Action.async{implicit request=>
    //val jsonData=Json.obj("userid"->"15","lectureUId"->"13","collectName"->"aaa","type"->"1")//
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    val lectureUId=(jsonData \ "lectureUId").as[String].toLong
    val collectName=(jsonData \ "collectName").as[String]
    val collectType=(jsonData \ "type").as[String].toInt
    var createTime=System.currentTimeMillis()
    userDao.collectLecture(userid,lectureUId,collectType,collectName,createTime).map{
      case res:Int =>
        if(res>0) Ok(successResult(Json.obj("data"->"已收藏")))
        else Ok(jsonResult(10000,"操作失败！"))
      case res:Long =>
        if(res>0L){
          Ok(successResult(Json.obj("data"->"收藏成功！")))
        }else{
         Ok(jsonResult(10000,"操作失败！"))
        }
    }
  }


  def getCollection=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
//    val jsonData=Json.obj("userid"->"15")
    val userid=(jsonData \ "userid").as[String].toLong
    userDao.getCollect(userid).map{res=>
      val data=res.map{collect=>
        Json.obj(
        "collectId"->collect._1.collectId,
        "collectType"->collect._1.collectType,
        "collectName"->collect._2.name,
        "imUserid"->collect._2.imuserid,
        "pic"->collect._2.pic
        )
      }
      Ok(successResult(Json.obj("data"->data)))
    }
  }




  /**chat*/
  def insertChat=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
//    val jsonData=Json.obj("userid"->"15","chatUserid"->"10","chatImUserid"->"test111","lastMsg"->"aaaaa哈哈哈")
    val userid=(jsonData \ "userid").as[String].toLong
    val chatUserid=(jsonData \ "chatUserid").as[String].toLong
    val chatImUserid=(jsonData \ "chatImUserid").as[String]
    val lastMsg=(jsonData \ "lastMsg").as[String]
    val updateTime=System.currentTimeMillis()
    userDao.insertChat(userid,chatUserid,chatImUserid,lastMsg,updateTime).map{
      case res:Int =>
        if(res>0) Ok(successResult(Json.obj("data"->"更新成功")))
        else Ok(jsonResult(10000,"操作失败！"))
      case res:Long =>
        if(res>0L){
          Ok(successResult(Json.obj("data"->"添加成功！")))
        }else{
          Ok(jsonResult(10000,"操作失败！"))
        }
    }
  }

  def getChatList=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    userDao.getChat(userid).map{seq=>
      val data=seq.map{res=>
        Json.obj(
        "chatUserid"->res._1.chatUserid,
        "chatImUsrid"->res._1.chatImUserid,
        "lastMsg"->res._1.lastmsg,
        "userName"->res._2.name,
        "pic"->res._2.pic
        )
      }
      Ok(successResult(Json.obj("data"->data)))
    }
  }

  










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



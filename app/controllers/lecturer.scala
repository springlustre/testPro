package controllers

import javax.inject.{Singleton, Inject}

import models.{UserDao, TrainerDao, JsonProtocols, ConsultantDao}
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by 王春泽 on 2015/12/31.
 */
@Singleton
class lecturer@Inject()(consultantDao:ConsultantDao,trainerDao: TrainerDao,
                        userDao: UserDao ) extends Controller with JsonProtocols{
  val log = LoggerFactory.getLogger(this.getClass)

  /**创建*/
  def creatConsultant=Action.async{implicit request=>
    val jsonData=request.body.asJson.get
    val userid=(jsonData \ "userid").as[Long]
    val introduce=(jsonData \ "introduce").asOpt[String]
    val proField=(jsonData \ "proField").asOpt[String]
    val industry=(jsonData \ "industry").asOpt[String]
    consultantDao.createConsult(userid,introduce.getOrElse(""),proField.getOrElse(""),industry.getOrElse("")).map{res=>
      if(res>0){
        Ok(successResult(Json.obj("data"->res)))
      }else{
        Ok(jsonResult(10001,"保存失败！"))
      }
    }
  }

  /**获取*/
  def getConsultantInfo(userid:Long)=Action.async{implicit request=>
    consultantDao.getByUserId(userid).map{res=>
      if(res.isDefined){
        Ok(successResult(Json.obj("data"->res.get._1)))
      }else{
        Ok(jsonResult(10002,"获取失败！"))
      }
    }
  }

  /**获取所有的讲师*/
  def getAllLecturer=Action.async{implicit request=>
    println("getAllLecturer-------")
    for{con<-consultantDao.getAll
        train<-trainerDao.getAll}yield{
        val consultant=con.map{res=>
          val con=res._1
          val user=res._2
          Json.obj("id"->con.userid,"name"->user.name,"type"->"咨询师","pic"->con.pic,"content"->con.introduce)
        }
       val trainer=train.map{res=>
         val train=res._1
         val user=res._2
         Json.obj("id"->train.userid,"name"->user.name,"type"->"培训师","pic"->train.pic,"content"->train.introduce)
       }
      val a=consultant++trainer
      Ok(successResult(Json.obj("data"->a)))
    }

  }


 /**获取某个lecturer的信息*/
  def getLecturerInfo=Action.async { implicit request =>
   val jsonData=Json.parse(request.body.asText.get)
   val userid=(jsonData \ "userid").as[String].toLong
   val userType=(jsonData \ "type").as[String]
   println("=============getInfo"+userid+userType)
   consultantDao.getPicByUserId(userid).flatMap { seq =>
     val picture=seq.map{pic=> pic.url}

     if (userType == "咨询师") {
       consultantDao.getByUserId(userid).map { res =>
         if (res.isDefined) {
           val con = res.get._1
           val user = res.get._2
           val picUrl=Seq(con.pic)++picture
           Ok(successResult(Json.obj("id" -> con.userid, "name" -> user.name, "type" -> "咨询师", "introduce" -> con.introduce,
             "profield" -> con.profield.split(","), "industry" -> con.industry.split(","),
             "locationX" -> user.locationx, "locationY" -> user.locationy, "pic"->picUrl)))
         } else {
           Ok(jsonResult(10006, "该lecturer不存在！"))
         }
       }
     } else if (userType == "培训师") {
       trainerDao.getByUserId(userid).flatMap { res =>
         if (res.isDefined) {
           val train = res.get._1
           val user = res.get._2
           val picUrl=Seq(train.pic)++picture
           trainerDao.getCourseByUserId(userid).map{seq=>
             val courseSeq=seq.map{course=>
               Json.obj("theme"->course.theme,"target"->course.target,
                 "outline"->course.outline.split("#"))
             }
             Ok(successResult(Json.obj("id" -> train.userid, "name" -> user.name, "type" -> "培训师", "introduce" -> train.introduce,
               "locationX" -> user.locationx, "locationY" -> user.locationy,"pic"->picUrl,
               "course"->courseSeq)))
           }
         } else {
           Future.successful(Ok(jsonResult(10006, "该lecturer不存在！")))
         }
       }
     } else {
       Future.successful(Ok(jsonResult(10005, "获取信息失败！")))
     }
   }
 }



  /**注册信息*/
  def registerConsultant=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
//    val usertype=(jsonData \ "type").as[String]
    val introduce=(jsonData \ "introduce").as[String]
    val proField=(jsonData \ "profield").as[String]
    val industry=(jsonData \ "industry").as[String]
    consultantDao.createConsult(userid,introduce,proField,industry).map{res=>
      if(res>0){
        Ok(successResult(Json.obj("data"->res)))
      }else{
        Ok(jsonResult(10010,"注册失败！"))
      }
    }
  }

  //培训师
  def registerTrainer=Action.async{implicit request=>
    val jsonData=Json.parse(request.body.asText.get)
    //{"introduce":"啊啊啊啊啊","userid":"15","courseInfo":[["吧","哈哈","1#2#3#4#5#6#"]]}
    println(jsonData)
    val userid=(jsonData \ "userid").as[String].toLong
    val introduce=(jsonData \ "introduce").as[String]
   // val courseInfo=(jsonData \ "courseInfo").as[List]
    Future.successful(Ok(success))
  }

  /**客户*/
  def registerConsumer=Action.async{implicit  request=>
    val jsonData=Json.parse(request.body.asText.get)
    val userid=(jsonData \ "userid").as[String].toLong
    val introduce=(jsonData \ "introduce").as[String]
    val profession=(jsonData \ "profession").as[String]
    val company=(jsonData \ "company").as[String]
    val position=(jsonData \ "position").as[String]
    val place=(jsonData \ "place").as[String]
    println(jsonData)
    userDao.updateConsumer(userid,introduce,profession,company,position,place).map{res=>
      if(res>0){
        Ok(success)
      }else{
        Ok(jsonResult(10011,"保存失败！"))
      }
    }
  }




}

package controllers

import javax.inject.{Singleton, Inject}

import models.{TrainerDao, JsonProtocols, ConsultantDao}
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by 王春泽 on 2015/12/31.
 */
@Singleton
class lecturer@Inject()(consultantDao:ConsultantDao,trainerDao: TrainerDao) extends Controller with JsonProtocols{
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
//  def registerLecturer=Action.async{implicit request=>
//    val jsonData=Json.parse(request.body.asText.get)
//  }




}

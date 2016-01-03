package controllers

import javax.inject.{Singleton, Inject}

import models.{TrainerDao, JsonProtocols, ConsultantDao}
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
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
        Ok(successResult(Json.obj("data"->res)))
      }else{
        Ok(jsonResult(10002,"获取失败！"))
      }
    }
  }

  /**获取所有的讲师*/
  def getAllLecturer=Action.async{implicit request=>
//    val jsonData=Json.parse(request.body.asText.get)
//    println(jsonData)
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
         Json.obj("id"->train.userid,"name"->user.name,"type"->"咨询师","pic"->train.pic,"content"->train.introduce)
       }
      val a=consultant++trainer
      Ok(successResult(Json.obj("data"->a)))
    }



  }



}

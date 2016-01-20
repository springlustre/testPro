package models


import javax.inject.Inject

import com.google.inject.Singleton
import play.api.Logger
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
 * Created by 王春泽 on 2015/12/31.
 */
@Singleton
class TrainerDao @Inject()(protected val dbConfigProvider:DatabaseConfigProvider
                               )extends HasDatabaseConfigProvider[JdbcProfile]{

  import slick.driver.MySQLDriver.api._

  private[this] val consultant=Tables.Consultant
  private[this] val trainer=Tables.Trainer
  private[this] val user=Tables.User
  private[this] val course=Tables.Course
  private[this] val log = Logger(this.getClass)

  /**创建*/
  def createTrainer(userid:Long,introduce:String)={
    db.run(trainer.map(t=>(t.userid,t.introduce)).returning(
      trainer.map(_.id))+=(userid,introduce)).mapTo[Long]
  }

  /**得到所有*/
  def getAll={
    db.run(trainer.join(user).on(_.userid===_.id).result)
  }

  /**根据距离获取*/
  def getByLocation(locationX:Double,locationY:Double,distance:Int)={
    db.run(consultant.join(user).on(_.userid===_.id).filter(t=>
      ((t._2.locationx-locationX)*(t._2.locationx-locationX)+(t._2.locationy-locationY)*(t._2.locationy-locationY))<
        distance*distance.toDouble).result)
  }

  /**根据用户id查询*/
  def getByUserId(userid:Long)={
    db.run(trainer.filter(_.userid===userid).join(user).on(_.userid===_.id).result.headOption)
  }
  /**获得所有课程*/
  def getCourseByUserId(userid:Long)={
    db.run(course.filter(_.userid===userid).result)
  }

  /**更新*/
  def updateTrainer(id:Long,userid:Long,introduce:String)={
    db.run(trainer.filter(_.id===id).map(t=>t.introduce).update(introduce))
  }

  /**添加更新课程*/
  def updateCourse(userid:Long,trainerId:Long,theme:String,target:String,outline:String)={
    db.run(course.map(t=>(t.userid,t.trainerid,t.theme,t.target,t.outline)).returning(
      course.map(_.id))+=(userid,trainerId,theme,target,outline)
      ).mapTo[Long]
  }

}

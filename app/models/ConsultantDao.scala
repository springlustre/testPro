package models

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.Logger
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import util.simpleUtil._

/**
 * Created by 王春泽 on 2015/12/24.
 */
@Singleton
class ConsultantDao @Inject()(protected val dbConfigProvider:DatabaseConfigProvider
                              )extends HasDatabaseConfigProvider[JdbcProfile]{

  import slick.driver.MySQLDriver.api._

  private[this] val consultant=Tables.Consultant
  private[this] val user=Tables.User
  private[this] val pic=Tables.Pic
  private[this] val log = Logger(this.getClass)
  private[this] val label=Tables.Label

  /**创建*/
  def createConsult(userid:Long,introduce:String,proField:String,industry:String)={
    db.run(consultant.map(t=>(t.userid,t.introduce,t.profield,t.industry)).returning(
    consultant.map(_.id))+=(userid,introduce,proField,industry)).mapTo[Long]
  }

  /**得到所有*/
  def getAll={
    db.run(consultant.join(user).on(_.userid===_.id).result)
  }


  /**根据距离获取*/
  def getByLocation(locationX:Double,locationY:Double,distance:Int)={
    db.run(consultant.join(user).on(_.userid===_.id).result)
  }

  /**根据用户id查询*/
  def getByUserId(userid:Long)={
    db.run(consultant.filter(_.userid===userid).join(user).on(_.userid===_.id).result.headOption)
  }

  /**获取图片*/
  def getPicByUserId(userid:Long)={
    db.run(pic.filter(_.userid===userid).result)
  }

 /**更新*/
  def updateConsult(id:Long,userid:Long,introduce:String,proField:String,industry:String)={
    db.run(consultant.filter(_.id===id).map(t=>(t.introduce,t.profield,t.industry)).update((introduce,
      proField,industry)))
  }


  def getLabelPro={
    db.run(label.filter(_.`type`===1).result)
  }

  def getLabelIndustry={
    db.run(label.filter(_.`type`===2).result)
  }



}

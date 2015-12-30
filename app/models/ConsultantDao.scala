package models

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.Logger
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

/**
 * Created by 王春泽 on 2015/12/24.
 */
@Singleton
class ConsultantDao @Inject()(protected val dbConfigProvider:DatabaseConfigProvider
                              )extends HasDatabaseConfigProvider[JdbcProfile]{

  import slick.driver.MySQLDriver.api._

  private[this] val consultant=Tables.Consultant
  private[this] val log = Logger(this.getClass)

  /**创建*/
  def createConsult(userid:Long,introduce:String,proField:String,industry:String)={
    db.run(consultant.map(t=>(t.userid,t.introduce,t.profield,t.industry)).returning(
    consultant.map(_.id))+=(userid,introduce,proField,industry)).mapTo[Long]
  }

  /**得到所有*/
  def getAll={
    db.run(consultant.result)
  }

  /**根据用户id查询*/
  def getByUserId(userid:Long)={
    db.run(consultant.filter(_.userid===userid).result)
  }


  def updateConsult(id:Long,userid:Long,introduce:String,proField:String,industry:String)={
    db.run(consultant.filter(_.id===id))
  }

}

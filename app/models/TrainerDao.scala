package models


import javax.inject.Inject

import com.google.inject.Singleton
import play.api.Logger
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
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

  /**根据用户id查询*/
  def getByUserId(userid:Long)={
    db.run(trainer.filter(_.userid===userid).result.headOption)
  }

  /**更新*/
  def updateTrainer(userid:Long,introduce:String)={
    db.run(trainer.filter(_.userid===userid).map(t=>t.introduce).update(introduce))
  }

}

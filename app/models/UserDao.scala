package models


import javax.inject.{Singleton, Inject}
import models.Tables.UserRow
import org.slf4j.LoggerFactory
import play.api.cache.Cache
import play.api.Play.current
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
/**
 * Created by springlustre on 2015/11/6.
 */

@Singleton
class UserDao @Inject()(
                      protected val dbConfigProvider: DatabaseConfigProvider
                      ) extends HasDatabaseConfigProvider[JdbcProfile]{

  private[this] val log = LoggerFactory.getLogger(this.getClass)
  private[this] val user = Tables.User
  private[this] val consumer=Tables.Consumer
  private val idCacheKey = "cache.user.id."
  private val emailCacheKey = "cache.user.email."

  val FIRST_ADMIN_ID = 10000L
  val adminEmail = "admin@admin"
  val adminPassword = "1qaz@WSX"
  val stateEnable = 1
  val stateDisable = 0
  val typeMember = 1
  val typeCoordinator = 2
  val typeAdmin = 3
  val typeSuperAdmin = 4

  import slick.driver.MySQLDriver.api._

  //
  /**创建*/
  def registeUser(loginname:String,password:String,phone:String,token:String,imToken:String,createTime:Long)={
    db.run(user.map(m=>(m.loginname,m.password,m.phone,m.token,m.imtoken,m.createtime)).returning(user.map(_.id))+=
      (loginname,password,phone,token,imToken,createTime)).mapTo[Long]
  }

  def login(loginname:String,password:String)={
    db.run(user.filter(t=>((t.loginname===loginname)&&(t.password===password))||((t.phone===loginname)&&(t.password===password))).result.headOption)
  }

  //更新信息
  def updateLocation(userid:Long,locationX:Double,locationY:Double,updateTime:Long)={
    db.run(user.filter(_.id===userid).map(t=>(t.locationx,t.locationy,t.updatetime)).update(
    locationX,locationY,updateTime
    ))
  }

  //更新信息
  def updatePersonInfo(userid:Long,name:String,password:String,phone:String,sex:String,birthday:String)={
    db.run(user.filter(_.id===userid).map(t=>(t.name,t.password,t.phone,t.sex,t.birthday)).update(
      name,password,phone,sex,birthday
    ))
  }

//  def createUser(userid:Option[Long] = None,loginname:Option[String]=None,name:String,password:String,token:Option[String]=None,
//                 phone:Option[String]=None,email:Option[String]=None,sex:Option[String]=None,birthday:Option[String]=None,
//                 birthyear:Option[String]=None,pic:Option[String]=None)={
//    if(userid.isDefined) {
//      db.run(user.map(m => (m.id,m.loginname, m.name, m.password, m.token, m.phone, m.email, m.sex,m.birthday,m.birthyear,m.pic)).
//        forceInsert(userid.get,loginname,name,password,token,phone,email,sex,birthday,birthyear,pic)
//      ).onFailure{ case e => log.error("create user failed." + e.getMessage)}
//      Future.successful(userid.get)
//    }
//
//    else{
//      log.info("Create user by email=" + email)
//      db.run(user.map(m => (m.loginname, m.name, m.password, m.token, m.phone, m.email, m.sex,m.birthday,m.birthyear,m.pic)).
//        returning(user.map(_.id)) +=(loginname,name,password,token,phone,email,sex,birthday,birthyear,pic)
//      ).mapTo[Long]
//    }
//  }

  def getUserById(id: Long) = {
    db.run(user.filter(_.id === id).result.headOption)
  }

  def getUserByPhone(phone:String)={
    db.run(user.filter(_.phone===phone).result.headOption)
  }

  def getUserByEmail(email:String)={
    db.run(user.filter(_.email===email).result.headOption)
  }

  def getUserByLoginname(loginname:String)={
    db.run(user.filter(_.loginname===loginname).result.headOption)
  }


//  def updateLoginInfo(userid:Long,loginname:Option[String]=None,name:String,token:Option[String]=None,
//                 phone:Option[String]=None,email:Option[String]=None)={
//    db.run(user.filter(_.id===userid).map(m=>(m.loginname, m.name, m.token, m.phone, m.email)).update(
//      loginname,name,token,phone,email))
//  }

//  def modefyUserInfo(userid:Long,sex:Option[String]=None,birthday:Option[String],
//                     birthyear:Option[String],pic:Option[String])={
//  db.run(user.filter(_.id===userid).map(m=>(m.sex,m.birthday,m.birthyear,m.pic)).update(
//    sex,birthday,birthyear,pic))
//  }

  def getConsumer(userid:Long)={
    db.run(consumer.filter(_.userid===userid).result.headOption)
  }

  def updateConsumer(userid:Long,introduce:String,profession:String,company:String,position:String,place:String)={
    db.run(consumer.filter(_.userid===userid).result.headOption).flatMap{res=>
      if(res.isDefined){
        db.run(consumer.filter(_.userid===userid).map(t=>(t.introduce,t.profession,t.company,t.position,t.site)).update(
          (introduce,profession,company,position,place)
        )).mapTo[Int]
      }else{
        db.run(consumer.map(t=>(t.userid,t.introduce,t.profession,t.company,t.position,t.site)).returning(consumer.map(_.id))+=(
          userid,introduce,profession,company,position,place)).mapTo[Int]
      }
    }
  }








}

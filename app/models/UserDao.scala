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
  private[this] val pic=Tables.Pic
  private[this] val collect=Tables.Collection
  private[this] val chat=Tables.Chat
  private[this] val price=Tables.Price
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
  def registeUser(loginname:String,password:String,phone:String,token:String,imToken:String,createTime:Long,imUserid:String)={
    db.run(user.map(m=>(m.loginname,m.password,m.phone,m.token,m.imtoken,m.createtime,m.imuserid)).returning(user.map(_.id))+=
      (loginname,password,phone,token,imToken,createTime,imUserid)).mapTo[Long]
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

  //头像
  def updateUserPhoto(userid:Long,url:String)={
    db.run(user.filter(_.id===userid).map(_.pic).update(url))
  }
  def getUserPhoto(userid:Long)={
    db.run(user.filter(_.id===userid).map(_.pic).result.headOption)
  }

  /**pic操作*/
  def deletePic(picId:Long)={
    db.run(pic.filter(_.id===picId).delete)
  }

  def insertPic(userid:Long,url:String,no:Int)={
    db.run(pic.map(t=>(t.userid,t.url,t.no)).returning(pic.map(_.id))+=(userid,url,no)).mapTo[Long]
  }


  def getPicByUserId(userid:Long)={
    db.run(pic.filter(_.userid===userid).result)
  }

  def getPicByUserNo(userid:Long,no:Int)={
    db.run(pic.filter(t=>(t.userid===userid)&&(t.no===no)).map(_.id).result.headOption)
  }

  def updatePic(id:Long,url:String)={
    db.run(pic.filter(_.id===id).map(_.url).update(url))
  }


  /**user获取*/
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

  def getUserByImUserid(imUserid:String)={
    db.run(user.filter(_.imuserid===imUserid).result.headOption)
  }



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

/**collect*/
  def collectLecture(userid:Long,collectId:Long,collectType:Int,collectName:String,
                      createTime:Long)={
    db.run(collect.filter(t=>(t.userid===userid&&t.collectId===collectId&&t.collectType===collectType)).
        map(_.id).result.headOption).flatMap{res=>
      if(res.isDefined){
        db.run(collect.filter(_.id===res.get).map(_.createtime).update(createTime))
      }else{
      db.run(collect.map(t=>(t.userid,t.collectId,t.collectType,t.collectName,t.createtime)).returning(
        collect.map(_.id))+=(userid,collectId,collectType,collectName,createTime))
      }
    }
  }

  def getCollect(userid:Long)={
    db.run(collect.filter(_.userid===userid).join(user).on(_.collectId===_.id).result)
  }


  /**chat*/
  def insertChat(userid:Long,chatUserid:Long,chatImUserid:String,lastMsg:String,updateTime:Long)={
    db.run(chat.filter(t=>(t.userid===userid)&&(t.chatUserid===chatUserid)).result.headOption).flatMap{res=>
      if(res.isDefined){
        db.run(chat.filter(_.id===res.get.id).map(t=>(t.lastmsg,t.updateTime)).update(lastMsg,updateTime))
      }else{
        db.run(chat.map(t=>(t.userid,t.chatUserid,t.chatImUserid,t.lastmsg,t.updateTime)).returning(chat.map(_.id))+=
          (userid,chatUserid,chatImUserid,lastMsg,updateTime))//.mapTo[Long]
      }
    }
  }

  def getChat(userid:Long)={
    db.run(chat.filter(_.userid===userid).sortBy(_.updateTime.desc).join(user).on(_.chatUserid===_.id).result)
  }


  /*price*/
  def getPrice(id:Long)={
    db.run(price.filter(_.userid===id).result.headOption)
  }





}

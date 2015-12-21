//package util
//
//import java.io.File
//import javax.inject.{Inject, Singleton}
//
//import org.apache.commons.mail.{DefaultAuthenticator, EmailAttachment, HtmlEmail}
//import org.slf4j.LoggerFactory
//import play.api.Play
//import play.api.Play.current
//import play.api.libs.concurrent.Akka
//
//import scala.concurrent.duration._
//
//@Singleton
//class CodeSender @Inject()(
//                            userMod: User,
//                            taskMod:Task,
//                            actionUtils: ActionUtils
//                            ) {
//  import play.api.libs.concurrent.Execution.Implicits.defaultContext
//  private val log = LoggerFactory.getLogger(this.getClass)
//
//  private val recipient = "report@neotel.com.cn"
//
//  protected def delayedExecution(block: => Unit): Unit = Akka.system.scheduler.scheduleOnce(1.seconds)(block)
//
///**发送重置密码**/
//  def sendCode(code: String, user: UserRow) = {
//    delayedExecution {
//      log.info(s"${user.email} want reset password")
//      val mailer: HtmlEmail = new HtmlEmail()
//      mailer.setHostName("smtp.exmail.qq.com")
//      mailer.setAuthenticator(new DefaultAuthenticator("rhea@neotel.com.cn", "1qaz@WSX"))
//      mailer.setSubject("Password Reset")
//      mailer.addTo(user.email)
//      mailer.setFrom("<rhea@neotel.com.cn>","Rhea Admin")
//      mailer.setHtmlMsg(views.html.email_template(code, user.id, Play.configuration.getString("deloyhost").get).toString)
//      mailer.send()
//
//    }
//  }
//
///**新添加用户发送欢迎邮件**/
//  def sendWelcome(name:String,password:String,role:String,group:String,mail:String)={
//    delayedExecution{
//      val mailer: HtmlEmail = new HtmlEmail()
//      mailer.setHostName("smtp.exmail.qq.com")
//      mailer.setAuthenticator(new DefaultAuthenticator("rhea@neotel.com.cn", "1qaz@WSX"))
//      mailer.setSubject("恭喜你成为Rhea的一员！")
//      mailer.addTo(mail)
//      mailer.setFrom("<rhea@neotel.com.cn>","Welcome to Rhea!")
//      mailer.setHtmlMsg(views.html.email_welcome(name,mail,password,role,group).toString)
//      mailer.send()
//    }
//  }
//
//  /**发送周报**/
//  def sendReport(user: UserRow, otherjob: String, helpcontent:String,fileList:List[File]=Nil,filenameList:List[String]=Nil)= {
//    delayedExecution {
//      log.info(s"${user.email} send report")
//      taskMod.weeklyReport(user).map { r =>
////        import java.util._
////        val sdf = new java.text.SimpleDateFormat("yyyy.MM")
////        val titleCalendar = Calendar.getInstance()
////        titleCalendar.setFirstDayOfWeek(Calendar.MONDAY)
////        titleCalendar.set(Calendar.HOUR_OF_DAY, 0)
////        titleCalendar.set(Calendar.MINUTE, 0)
////        titleCalendar.set(Calendar.SECOND, 0)
////        titleCalendar.set(Calendar.MILLISECOND, 0)
////
////        titleCalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
////        val fridayThisWeek = titleCalendar.getTimeInMillis
////        titleCalendar.set(Calendar.DAY_OF_MONTH , 1)
////        val firstDay = titleCalendar.getTimeInMillis
////
////        val week = (fridayThisWeek - firstDay)/(7*24*3600*1000) + 1
////        val month = sdf.format(new Date(fridayThisWeek))
//
//        val mailer: HtmlEmail = new HtmlEmail()
//        mailer.setHostName("smtp.exmail.qq.com")
//        mailer.setAuthenticator(new DefaultAuthenticator("rhea@neotel.com.cn", "1qaz@WSX"))
//        mailer.setSubject(user.email)
//
//        if (fileList.nonEmpty) {
//          for (i <- fileList.indices) {
//            val attachment: EmailAttachment = new EmailAttachment()
//            attachment.setPath(fileList(i).getPath)
//            attachment.setDisposition(EmailAttachment.ATTACHMENT)
//            attachment.setName(filenameList(i))
//            mailer.attach(attachment)
//          }
//        }
//        mailer.setFrom("<rhea@neotel.com.cn>",user.name)
//        mailer.addTo(recipient)
//        mailer.setHtmlMsg(views.html.copypage(user, r._1.toMap, r._2.toMap, r._3.toMap, r._4.toMap, helpcontent, otherjob).toString)
//        mailer.send()
//
//        log.info(user.name+":send weekly report success!")
//
//      }
//
//    }
//  }
//
//
//
//}
//
//

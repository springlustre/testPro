package util

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import com.twitter.util.{Time, TimeFormat}
import org.slf4j.LoggerFactory

/**
 * Created by chenlingpeng on 2014/10/28.
 */

object TimeFormatUtil {
  val log = LoggerFactory.getLogger(this.getClass)

  def toMilliseconds(date: String, format: String = "yyyy-MM-dd HH:mm:ss"): Long = {
    new TimeFormat(format).parse(date).inMilliseconds - 8 * 60 * 60 * 1000
  }

  def toLocalDate(date: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String = {
    new TimeFormat(format).format(Time.fromMilliseconds(date + 8 * 60 * 60 * 1000))
  }

  def toReadableFormat(date: Long): String = {
    if(date == 0)
      ""
    else {
      val delta = System.currentTimeMillis() - date
      delta match {
        case rightNow if rightNow < 1000 =>
          "刚刚"
        case seconds if seconds < 60 * 1000 =>
          seconds / 1000 + "秒前"
        case minutes if minutes < 60 * 60 * 1000 =>
          minutes / (60 * 1000) + "分钟前"
        case hours if hours < 24 * 60 * 60 * 1000 =>
          hours / (60 * 60 * 1000) + "小时前"
        case days =>
          days / (24 * 60 * 60 * 1000) + "天前"
      }
    }
  }

  def firstDayOfWeek = {
//    val calendar = Calendar.getInstance()
////    calendar.setFirstDayOfWeek(Calendar.MONDAY)
//    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
////    calendar.set(Calendar.HOUR, 1)
//    calendar.set(Calendar.MINUTE,0)
//    calendar.set(Calendar.SECOND,0)
//    calendar.set(Calendar.MILLISECOND,0)
////    calendar.add(Calendar.HOUR,8)
//    val format = new TimeFormat("yyyy-MM-dd HH:mm:ss").format(Time.fromMilliseconds(calendar.getTimeInMillis))
//    log.info("@@"+format)
//    calendar.getTimeInMillis-4 * 60 * 60 * 1000

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    calendar.set(Calendar.HOUR, -4)
    calendar.set(Calendar.MINUTE,0)
    calendar.set(Calendar.SECOND,0)
    calendar.set(Calendar.MILLISECOND,0)
    calendar.getTimeInMillis
  }

  def fridayDayOfWeek = {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
    calendar.set(Calendar.HOUR, 5)
    calendar.set(Calendar.MINUTE,30)
    calendar.set(Calendar.SECOND,0)
    calendar.set(Calendar.MILLISECOND,0)
    calendar.getTimeInMillis
  }

  def sundayDayOfWeek = {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    calendar.add(Calendar.WEEK_OF_MONTH,1)
    calendar.set(Calendar.HOUR, -1)
    calendar.set(Calendar.MINUTE,30)
    calendar.set(Calendar.SECOND,0)
    calendar.set(Calendar.MILLISECOND,0)
    calendar.getTimeInMillis
  }

  /**得到时间戳的年份*/
  def yearOfTimeStamp(timeStampMs:Long)={
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.format(data).split("-")(0)
  }

  /**得到时间戳的月份*/
  def monthOfTimeStamp(timeStampMs:Long)={
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.format(data).split("-")(1)
  }

  /**得到时间戳的天*/
  def dayOfTimeStamp(timeStampMs:Long)={
    //判断时间戳是否为毫秒 毫秒为13位
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    format.format(data).split("-")(2)
  }

  /**格式化转时间戳*/
  def toTimeStamp(time:String)={
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date=format.parse(time)
    date.getTime
  }

}

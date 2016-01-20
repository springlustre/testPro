package util

import play.api.libs.json.Json

/**
 * Created by 王春泽 on 2016/1/20.
 */
object simpleUtil {
  def getDistatce(lat1:Double,lon1:Double,lat2:Double,lon2:Double)={
    if(lat1!=0 && lon1!=0 && lat2!=0 && lon2!=0){
      val R =  6378.137
      val radLat1 = lat1* Math.PI / 180
      val radLat2 = lat2* Math.PI / 180
      val a = radLat1 - radLat2
      val b = lon1* Math.PI / 180 - lon2* Math.PI / 180
      val s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)))
      BigDecimal.decimal(s * R).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    }else{
      BigDecimal.decimal(0).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    }
  }

  def main(args: Array[String]) {
//    println(getDistatce(39.969108,117.362791,39.869108,117.562791))
  }
}

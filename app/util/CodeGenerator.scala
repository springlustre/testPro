package util

/**
 * Created by caoshuai on 2015/9/18.
 */

import scala.util.Random


object CodeGenerator {

  val MAX_NUMBER = 999999

  def newCode: Int = math.abs(Random.nextInt() % MAX_NUMBER)

}

package util

/**
 * User: Taoz
 * Date: 3/25/2015
 * Time: 10:31 PM
 */
object GenerateSlickCode {



  def genRheaTables = {
    val slickDriver = "slick.driver.MySQLDriver"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://139.129.25.229:3306/appDB?characterEncoding=utf-8"
    val outputFolder = "app"
    val pkg = "models"
    val user = "root"
    val password = "wang897618476A"

    slick.codegen.SourceCodeGenerator.main(
      Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, password)
    )
  }


  def main(args: Array[String]) {
    genRheaTables
    println("DONE.")
  }

}

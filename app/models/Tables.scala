package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param loginname Database column loginname SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param password Database column password SqlType(VARCHAR), Length(255,true)
   *  @param token Database column token SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param phone Database column phone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param email Database column email SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param sex Database column sex SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param birthday Database column birthday SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param birthyear Database column birthyear SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param pic Database column pic SqlType(VARCHAR), Length(255,true), Default(None) */
  case class UserRow(id: Long, loginname: Option[String] = None, name: String, password: String, token: Option[String] = None, phone: Option[String] = None, email: Option[String] = None, sex: Option[String] = None, birthday: Option[String] = None, birthyear: Option[String] = None, pic: Option[String] = None)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Long], <<?[String], <<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (id, loginname, name, password, token, phone, email, sex, birthday, birthyear, pic) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), loginname, Rep.Some(name), Rep.Some(password), token, phone, email, sex, birthday, birthyear, pic).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column loginname SqlType(VARCHAR), Length(255,true), Default(None) */
    val loginname: Rep[Option[String]] = column[Option[String]]("loginname", O.Length(255,varying=true), O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column token SqlType(VARCHAR), Length(255,true), Default(None) */
    val token: Rep[Option[String]] = column[Option[String]]("token", O.Length(255,varying=true), O.Default(None))
    /** Database column phone SqlType(VARCHAR), Length(255,true), Default(None) */
    val phone: Rep[Option[String]] = column[Option[String]]("phone", O.Length(255,varying=true), O.Default(None))
    /** Database column email SqlType(VARCHAR), Length(255,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(255,varying=true), O.Default(None))
    /** Database column sex SqlType(VARCHAR), Length(255,true), Default(None) */
    val sex: Rep[Option[String]] = column[Option[String]]("sex", O.Length(255,varying=true), O.Default(None))
    /** Database column birthday SqlType(VARCHAR), Length(255,true), Default(None) */
    val birthday: Rep[Option[String]] = column[Option[String]]("birthday", O.Length(255,varying=true), O.Default(None))
    /** Database column birthyear SqlType(VARCHAR), Length(255,true), Default(None) */
    val birthyear: Rep[Option[String]] = column[Option[String]]("birthyear", O.Length(255,varying=true), O.Default(None))
    /** Database column pic SqlType(VARCHAR), Length(255,true), Default(None) */
    val pic: Rep[Option[String]] = column[Option[String]]("pic", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}

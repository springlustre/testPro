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
  lazy val schema = Array(Consultant.schema, Consumer.schema, Course.schema, History.schema, Trainer.schema, User.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Consultant
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param introduce Database column introduce SqlType(VARCHAR), Length(2000,true), Default()
   *  @param profield Database column proField SqlType(VARCHAR), Length(400,true), Default()
   *  @param industry Database column industry SqlType(VARCHAR), Length(400,true), Default() */
  case class ConsultantRow(id: Long, userid: Long, introduce: String = "", profield: String = "", industry: String = "")
  /** GetResult implicit for fetching ConsultantRow objects using plain SQL queries */
  implicit def GetResultConsultantRow(implicit e0: GR[Long], e1: GR[String]): GR[ConsultantRow] = GR{
    prs => import prs._
    ConsultantRow.tupled((<<[Long], <<[Long], <<[String], <<[String], <<[String]))
  }
  /** Table description of table consultant. Objects of this class serve as prototypes for rows in queries. */
  class Consultant(_tableTag: Tag) extends Table[ConsultantRow](_tableTag, "consultant") {
    def * = (id, userid, introduce, profield, industry) <> (ConsultantRow.tupled, ConsultantRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(introduce), Rep.Some(profield), Rep.Some(industry)).shaped.<>({r=>import r._; _1.map(_=> ConsultantRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column introduce SqlType(VARCHAR), Length(2000,true), Default() */
    val introduce: Rep[String] = column[String]("introduce", O.Length(2000,varying=true), O.Default(""))
    /** Database column proField SqlType(VARCHAR), Length(400,true), Default() */
    val profield: Rep[String] = column[String]("proField", O.Length(400,varying=true), O.Default(""))
    /** Database column industry SqlType(VARCHAR), Length(400,true), Default() */
    val industry: Rep[String] = column[String]("industry", O.Length(400,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Consultant */
  lazy val Consultant = new TableQuery(tag => new Consultant(tag))

  /** Entity class storing rows of table Consumer
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param profession Database column profession SqlType(VARCHAR), Length(30,true), Default()
   *  @param company Database column company SqlType(VARCHAR), Length(50,true), Default()
   *  @param position Database column position SqlType(VARCHAR), Length(50,true), Default()
   *  @param site Database column site SqlType(VARCHAR), Length(100,true), Default()
   *  @param introduce Database column introduce SqlType(VARCHAR), Length(1000,true), Default() */
  case class ConsumerRow(id: Long, userid: Long, profession: String = "", company: String = "", position: String = "", site: String = "", introduce: String = "")
  /** GetResult implicit for fetching ConsumerRow objects using plain SQL queries */
  implicit def GetResultConsumerRow(implicit e0: GR[Long], e1: GR[String]): GR[ConsumerRow] = GR{
    prs => import prs._
    ConsumerRow.tupled((<<[Long], <<[Long], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table consumer. Objects of this class serve as prototypes for rows in queries. */
  class Consumer(_tableTag: Tag) extends Table[ConsumerRow](_tableTag, "consumer") {
    def * = (id, userid, profession, company, position, site, introduce) <> (ConsumerRow.tupled, ConsumerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(profession), Rep.Some(company), Rep.Some(position), Rep.Some(site), Rep.Some(introduce)).shaped.<>({r=>import r._; _1.map(_=> ConsumerRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column profession SqlType(VARCHAR), Length(30,true), Default() */
    val profession: Rep[String] = column[String]("profession", O.Length(30,varying=true), O.Default(""))
    /** Database column company SqlType(VARCHAR), Length(50,true), Default() */
    val company: Rep[String] = column[String]("company", O.Length(50,varying=true), O.Default(""))
    /** Database column position SqlType(VARCHAR), Length(50,true), Default() */
    val position: Rep[String] = column[String]("position", O.Length(50,varying=true), O.Default(""))
    /** Database column site SqlType(VARCHAR), Length(100,true), Default() */
    val site: Rep[String] = column[String]("site", O.Length(100,varying=true), O.Default(""))
    /** Database column introduce SqlType(VARCHAR), Length(1000,true), Default() */
    val introduce: Rep[String] = column[String]("introduce", O.Length(1000,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Consumer */
  lazy val Consumer = new TableQuery(tag => new Consumer(tag))

  /** Entity class storing rows of table Course
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param trainerid Database column trainerid SqlType(BIGINT)
   *  @param theme Database column theme SqlType(VARCHAR), Length(100,true), Default()
   *  @param target Database column target SqlType(VARCHAR), Length(300,true), Default()
   *  @param outline Database column outline SqlType(VARCHAR), Length(2000,true), Default() */
  case class CourseRow(id: Long, userid: Long, trainerid: Long, theme: String = "", target: String = "", outline: String = "")
  /** GetResult implicit for fetching CourseRow objects using plain SQL queries */
  implicit def GetResultCourseRow(implicit e0: GR[Long], e1: GR[String]): GR[CourseRow] = GR{
    prs => import prs._
    CourseRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[String], <<[String]))
  }
  /** Table description of table course. Objects of this class serve as prototypes for rows in queries. */
  class Course(_tableTag: Tag) extends Table[CourseRow](_tableTag, "course") {
    def * = (id, userid, trainerid, theme, target, outline) <> (CourseRow.tupled, CourseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(trainerid), Rep.Some(theme), Rep.Some(target), Rep.Some(outline)).shaped.<>({r=>import r._; _1.map(_=> CourseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column trainerid SqlType(BIGINT) */
    val trainerid: Rep[Long] = column[Long]("trainerid")
    /** Database column theme SqlType(VARCHAR), Length(100,true), Default() */
    val theme: Rep[String] = column[String]("theme", O.Length(100,varying=true), O.Default(""))
    /** Database column target SqlType(VARCHAR), Length(300,true), Default() */
    val target: Rep[String] = column[String]("target", O.Length(300,varying=true), O.Default(""))
    /** Database column outline SqlType(VARCHAR), Length(2000,true), Default() */
    val outline: Rep[String] = column[String]("outline", O.Length(2000,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Course */
  lazy val Course = new TableQuery(tag => new Course(tag))

  /** Entity class storing rows of table History
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param product Database column product SqlType(INT), Default(0)
   *  @param productid Database column productId SqlType(BIGINT), Default(0)
   *  @param creattime Database column creatTime SqlType(BIGINT), Default(0)
   *  @param state Database column state SqlType(INT), Default(0) */
  case class HistoryRow(id: Long, userid: Long, product: Int = 0, productid: Long = 0L, creattime: Long = 0L, state: Int = 0)
  /** GetResult implicit for fetching HistoryRow objects using plain SQL queries */
  implicit def GetResultHistoryRow(implicit e0: GR[Long], e1: GR[Int]): GR[HistoryRow] = GR{
    prs => import prs._
    HistoryRow.tupled((<<[Long], <<[Long], <<[Int], <<[Long], <<[Long], <<[Int]))
  }
  /** Table description of table history. Objects of this class serve as prototypes for rows in queries. */
  class History(_tableTag: Tag) extends Table[HistoryRow](_tableTag, "history") {
    def * = (id, userid, product, productid, creattime, state) <> (HistoryRow.tupled, HistoryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(product), Rep.Some(productid), Rep.Some(creattime), Rep.Some(state)).shaped.<>({r=>import r._; _1.map(_=> HistoryRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column product SqlType(INT), Default(0) */
    val product: Rep[Int] = column[Int]("product", O.Default(0))
    /** Database column productId SqlType(BIGINT), Default(0) */
    val productid: Rep[Long] = column[Long]("productId", O.Default(0L))
    /** Database column creatTime SqlType(BIGINT), Default(0) */
    val creattime: Rep[Long] = column[Long]("creatTime", O.Default(0L))
    /** Database column state SqlType(INT), Default(0) */
    val state: Rep[Int] = column[Int]("state", O.Default(0))
  }
  /** Collection-like TableQuery object for table History */
  lazy val History = new TableQuery(tag => new History(tag))

  /** Entity class storing rows of table Trainer
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param introduce Database column introduce SqlType(VARCHAR), Length(2000,true), Default() */
  case class TrainerRow(id: Long, userid: Long, introduce: String = "")
  /** GetResult implicit for fetching TrainerRow objects using plain SQL queries */
  implicit def GetResultTrainerRow(implicit e0: GR[Long], e1: GR[String]): GR[TrainerRow] = GR{
    prs => import prs._
    TrainerRow.tupled((<<[Long], <<[Long], <<[String]))
  }
  /** Table description of table trainer. Objects of this class serve as prototypes for rows in queries. */
  class Trainer(_tableTag: Tag) extends Table[TrainerRow](_tableTag, "trainer") {
    def * = (id, userid, introduce) <> (TrainerRow.tupled, TrainerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(introduce)).shaped.<>({r=>import r._; _1.map(_=> TrainerRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column introduce SqlType(VARCHAR), Length(2000,true), Default() */
    val introduce: Rep[String] = column[String]("introduce", O.Length(2000,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Trainer */
  lazy val Trainer = new TableQuery(tag => new Trainer(tag))

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
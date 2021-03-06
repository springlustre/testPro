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
  lazy val schema = Array(Chat.schema, Collection.schema, Consultant.schema, Consumer.schema, Course.schema, History.schema, Label.schema, Pic.schema, Picture.schema, Price.schema, Trainer.schema, User.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Chat
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT), Default(0)
   *  @param chatUserid Database column chat_userid SqlType(BIGINT), Default(0)
   *  @param chatImUserid Database column chat_im_userid SqlType(VARCHAR), Length(255,true), Default()
   *  @param updateTime Database column update_time SqlType(BIGINT), Default(0)
   *  @param lastmsg Database column lastMsg SqlType(VARCHAR), Length(255,true) */
  case class ChatRow(id: Long, userid: Long = 0L, chatUserid: Long = 0L, chatImUserid: String = "", updateTime: Long = 0L, lastmsg: String)
  /** GetResult implicit for fetching ChatRow objects using plain SQL queries */
  implicit def GetResultChatRow(implicit e0: GR[Long], e1: GR[String]): GR[ChatRow] = GR{
    prs => import prs._
    ChatRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[Long], <<[String]))
  }
  /** Table description of table chat. Objects of this class serve as prototypes for rows in queries. */
  class Chat(_tableTag: Tag) extends Table[ChatRow](_tableTag, "chat") {
    def * = (id, userid, chatUserid, chatImUserid, updateTime, lastmsg) <> (ChatRow.tupled, ChatRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(chatUserid), Rep.Some(chatImUserid), Rep.Some(updateTime), Rep.Some(lastmsg)).shaped.<>({r=>import r._; _1.map(_=> ChatRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT), Default(0) */
    val userid: Rep[Long] = column[Long]("userid", O.Default(0L))
    /** Database column chat_userid SqlType(BIGINT), Default(0) */
    val chatUserid: Rep[Long] = column[Long]("chat_userid", O.Default(0L))
    /** Database column chat_im_userid SqlType(VARCHAR), Length(255,true), Default() */
    val chatImUserid: Rep[String] = column[String]("chat_im_userid", O.Length(255,varying=true), O.Default(""))
    /** Database column update_time SqlType(BIGINT), Default(0) */
    val updateTime: Rep[Long] = column[Long]("update_time", O.Default(0L))
    /** Database column lastMsg SqlType(VARCHAR), Length(255,true) */
    val lastmsg: Rep[String] = column[String]("lastMsg", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Chat */
  lazy val Chat = new TableQuery(tag => new Chat(tag))

  /** Entity class storing rows of table Collection
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param collectId Database column collect_id SqlType(BIGINT)
   *  @param collectType Database column collect_type SqlType(INT)
   *  @param collectName Database column collect_name SqlType(VARCHAR), Length(50,true)
   *  @param createtime Database column createTime SqlType(BIGINT) */
  case class CollectionRow(id: Long, userid: Long, collectId: Long, collectType: Int, collectName: String, createtime: Long)
  /** GetResult implicit for fetching CollectionRow objects using plain SQL queries */
  implicit def GetResultCollectionRow(implicit e0: GR[Long], e1: GR[Int], e2: GR[String]): GR[CollectionRow] = GR{
    prs => import prs._
    CollectionRow.tupled((<<[Long], <<[Long], <<[Long], <<[Int], <<[String], <<[Long]))
  }
  /** Table description of table collection. Objects of this class serve as prototypes for rows in queries. */
  class Collection(_tableTag: Tag) extends Table[CollectionRow](_tableTag, "collection") {
    def * = (id, userid, collectId, collectType, collectName, createtime) <> (CollectionRow.tupled, CollectionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(collectId), Rep.Some(collectType), Rep.Some(collectName), Rep.Some(createtime)).shaped.<>({r=>import r._; _1.map(_=> CollectionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column collect_id SqlType(BIGINT) */
    val collectId: Rep[Long] = column[Long]("collect_id")
    /** Database column collect_type SqlType(INT) */
    val collectType: Rep[Int] = column[Int]("collect_type")
    /** Database column collect_name SqlType(VARCHAR), Length(50,true) */
    val collectName: Rep[String] = column[String]("collect_name", O.Length(50,varying=true))
    /** Database column createTime SqlType(BIGINT) */
    val createtime: Rep[Long] = column[Long]("createTime")
  }
  /** Collection-like TableQuery object for table Collection */
  lazy val Collection = new TableQuery(tag => new Collection(tag))

  /** Entity class storing rows of table Consultant
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param introduce Database column introduce SqlType(VARCHAR), Length(2000,true), Default()
   *  @param profield Database column proField SqlType(VARCHAR), Length(400,true), Default()
   *  @param industry Database column industry SqlType(VARCHAR), Length(400,true), Default()
   *  @param pic Database column pic SqlType(VARCHAR), Length(255,true), Default() */
  case class ConsultantRow(id: Long, userid: Long, introduce: String = "", profield: String = "", industry: String = "", pic: String = "")
  /** GetResult implicit for fetching ConsultantRow objects using plain SQL queries */
  implicit def GetResultConsultantRow(implicit e0: GR[Long], e1: GR[String]): GR[ConsultantRow] = GR{
    prs => import prs._
    ConsultantRow.tupled((<<[Long], <<[Long], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table consultant. Objects of this class serve as prototypes for rows in queries. */
  class Consultant(_tableTag: Tag) extends Table[ConsultantRow](_tableTag, "consultant") {
    def * = (id, userid, introduce, profield, industry, pic) <> (ConsultantRow.tupled, ConsultantRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(introduce), Rep.Some(profield), Rep.Some(industry), Rep.Some(pic)).shaped.<>({r=>import r._; _1.map(_=> ConsultantRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

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
    /** Database column pic SqlType(VARCHAR), Length(255,true), Default() */
    val pic: Rep[String] = column[String]("pic", O.Length(255,varying=true), O.Default(""))
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

  /** Entity class storing rows of table Label
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param label Database column label SqlType(VARCHAR), Length(30,true)
   *  @param `type` Database column type SqlType(INT) */
  case class LabelRow(id: Long, label: String, `type`: Int)
  /** GetResult implicit for fetching LabelRow objects using plain SQL queries */
  implicit def GetResultLabelRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Int]): GR[LabelRow] = GR{
    prs => import prs._
    LabelRow.tupled((<<[Long], <<[String], <<[Int]))
  }
  /** Table description of table label. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class Label(_tableTag: Tag) extends Table[LabelRow](_tableTag, "label") {
    def * = (id, label, `type`) <> (LabelRow.tupled, LabelRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(label), Rep.Some(`type`)).shaped.<>({r=>import r._; _1.map(_=> LabelRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column label SqlType(VARCHAR), Length(30,true) */
    val label: Rep[String] = column[String]("label", O.Length(30,varying=true))
    /** Database column type SqlType(INT)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[Int] = column[Int]("type")
  }
  /** Collection-like TableQuery object for table Label */
  lazy val Label = new TableQuery(tag => new Label(tag))

  /** Entity class storing rows of table Pic
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param url Database column url SqlType(VARCHAR), Length(100,true), Default()
   *  @param no Database column no SqlType(INT), Default(0) */
  case class PicRow(id: Long, userid: Long, url: String = "", no: Int = 0)
  /** GetResult implicit for fetching PicRow objects using plain SQL queries */
  implicit def GetResultPicRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Int]): GR[PicRow] = GR{
    prs => import prs._
    PicRow.tupled((<<[Long], <<[Long], <<[String], <<[Int]))
  }
  /** Table description of table pic. Objects of this class serve as prototypes for rows in queries. */
  class Pic(_tableTag: Tag) extends Table[PicRow](_tableTag, "pic") {
    def * = (id, userid, url, no) <> (PicRow.tupled, PicRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(url), Rep.Some(no)).shaped.<>({r=>import r._; _1.map(_=> PicRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column url SqlType(VARCHAR), Length(100,true), Default() */
    val url: Rep[String] = column[String]("url", O.Length(100,varying=true), O.Default(""))
    /** Database column no SqlType(INT), Default(0) */
    val no: Rep[Int] = column[Int]("no", O.Default(0))
  }
  /** Collection-like TableQuery object for table Pic */
  lazy val Pic = new TableQuery(tag => new Pic(tag))

  /** Entity class storing rows of table Picture
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(VARCHAR), Length(255,true), Default()
   *  @param username Database column username SqlType(VARCHAR), Length(255,true), Default()
   *  @param url Database column url SqlType(VARCHAR), Length(255,true), Default() */
  case class PictureRow(id: Long, userid: String = "", username: String = "", url: String = "")
  /** GetResult implicit for fetching PictureRow objects using plain SQL queries */
  implicit def GetResultPictureRow(implicit e0: GR[Long], e1: GR[String]): GR[PictureRow] = GR{
    prs => import prs._
    PictureRow.tupled((<<[Long], <<[String], <<[String], <<[String]))
  }
  /** Table description of table picture. Objects of this class serve as prototypes for rows in queries. */
  class Picture(_tableTag: Tag) extends Table[PictureRow](_tableTag, "picture") {
    def * = (id, userid, username, url) <> (PictureRow.tupled, PictureRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(username), Rep.Some(url)).shaped.<>({r=>import r._; _1.map(_=> PictureRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(VARCHAR), Length(255,true), Default() */
    val userid: Rep[String] = column[String]("userid", O.Length(255,varying=true), O.Default(""))
    /** Database column username SqlType(VARCHAR), Length(255,true), Default() */
    val username: Rep[String] = column[String]("username", O.Length(255,varying=true), O.Default(""))
    /** Database column url SqlType(VARCHAR), Length(255,true), Default() */
    val url: Rep[String] = column[String]("url", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Picture */
  lazy val Picture = new TableQuery(tag => new Picture(tag))

  /** Entity class storing rows of table Price
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param must1 Database column must1 SqlType(INT)
   *  @param must2 Database column must2 SqlType(INT)
   *  @param must3 Database column must3 SqlType(INT)
   *  @param option1 Database column option1 SqlType(INT)
   *  @param option2 Database column option2 SqlType(INT)
   *  @param option3 Database column option3 SqlType(INT)
   *  @param option4 Database column option4 SqlType(INT)
   *  @param option5 Database column option5 SqlType(INT)
   *  @param option6 Database column option6 SqlType(INT)
   *  @param option7 Database column option7 SqlType(INT) */
  case class PriceRow(id: Long, userid: Long, must1: Int, must2: Int, must3: Int, option1: Int, option2: Int, option3: Int, option4: Int, option5: Int, option6: Int, option7: Int)
  /** GetResult implicit for fetching PriceRow objects using plain SQL queries */
  implicit def GetResultPriceRow(implicit e0: GR[Long], e1: GR[Int]): GR[PriceRow] = GR{
    prs => import prs._
    PriceRow.tupled((<<[Long], <<[Long], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table price. Objects of this class serve as prototypes for rows in queries. */
  class Price(_tableTag: Tag) extends Table[PriceRow](_tableTag, "price") {
    def * = (id, userid, must1, must2, must3, option1, option2, option3, option4, option5, option6, option7) <> (PriceRow.tupled, PriceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(must1), Rep.Some(must2), Rep.Some(must3), Rep.Some(option1), Rep.Some(option2), Rep.Some(option3), Rep.Some(option4), Rep.Some(option5), Rep.Some(option6), Rep.Some(option7)).shaped.<>({r=>import r._; _1.map(_=> PriceRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column must1 SqlType(INT) */
    val must1: Rep[Int] = column[Int]("must1")
    /** Database column must2 SqlType(INT) */
    val must2: Rep[Int] = column[Int]("must2")
    /** Database column must3 SqlType(INT) */
    val must3: Rep[Int] = column[Int]("must3")
    /** Database column option1 SqlType(INT) */
    val option1: Rep[Int] = column[Int]("option1")
    /** Database column option2 SqlType(INT) */
    val option2: Rep[Int] = column[Int]("option2")
    /** Database column option3 SqlType(INT) */
    val option3: Rep[Int] = column[Int]("option3")
    /** Database column option4 SqlType(INT) */
    val option4: Rep[Int] = column[Int]("option4")
    /** Database column option5 SqlType(INT) */
    val option5: Rep[Int] = column[Int]("option5")
    /** Database column option6 SqlType(INT) */
    val option6: Rep[Int] = column[Int]("option6")
    /** Database column option7 SqlType(INT) */
    val option7: Rep[Int] = column[Int]("option7")
  }
  /** Collection-like TableQuery object for table Price */
  lazy val Price = new TableQuery(tag => new Price(tag))

  /** Entity class storing rows of table Trainer
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param userid Database column userid SqlType(BIGINT)
   *  @param introduce Database column introduce SqlType(VARCHAR), Length(2000,true), Default()
   *  @param pic Database column pic SqlType(VARCHAR), Length(255,true), Default() */
  case class TrainerRow(id: Long, userid: Long, introduce: String = "", pic: String = "")
  /** GetResult implicit for fetching TrainerRow objects using plain SQL queries */
  implicit def GetResultTrainerRow(implicit e0: GR[Long], e1: GR[String]): GR[TrainerRow] = GR{
    prs => import prs._
    TrainerRow.tupled((<<[Long], <<[Long], <<[String], <<[String]))
  }
  /** Table description of table trainer. Objects of this class serve as prototypes for rows in queries. */
  class Trainer(_tableTag: Tag) extends Table[TrainerRow](_tableTag, "trainer") {
    def * = (id, userid, introduce, pic) <> (TrainerRow.tupled, TrainerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(introduce), Rep.Some(pic)).shaped.<>({r=>import r._; _1.map(_=> TrainerRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userid SqlType(BIGINT) */
    val userid: Rep[Long] = column[Long]("userid")
    /** Database column introduce SqlType(VARCHAR), Length(2000,true), Default() */
    val introduce: Rep[String] = column[String]("introduce", O.Length(2000,varying=true), O.Default(""))
    /** Database column pic SqlType(VARCHAR), Length(255,true), Default() */
    val pic: Rep[String] = column[String]("pic", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Trainer */
  lazy val Trainer = new TableQuery(tag => new Trainer(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param loginname Database column loginname SqlType(VARCHAR), Length(255,true), Default()
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default()
   *  @param password Database column password SqlType(VARCHAR), Length(255,true), Default()
   *  @param token Database column token SqlType(VARCHAR), Length(255,true), Default()
   *  @param phone Database column phone SqlType(VARCHAR), Length(255,true), Default()
   *  @param email Database column email SqlType(VARCHAR), Length(255,true), Default()
   *  @param sex Database column sex SqlType(VARCHAR), Length(255,true), Default()
   *  @param birthday Database column birthday SqlType(VARCHAR), Length(255,true), Default()
   *  @param pic Database column pic SqlType(VARCHAR), Length(255,true), Default()
   *  @param imtoken Database column imToken SqlType(VARCHAR), Length(255,true), Default()
   *  @param createtime Database column createTime SqlType(BIGINT), Default(0)
   *  @param updatetime Database column updateTime SqlType(BIGINT), Default(0)
   *  @param locationx Database column locationX SqlType(DOUBLE), Default(0.0)
   *  @param locationy Database column locationY SqlType(DOUBLE), Default(0.0)
   *  @param imuserid Database column imUserid SqlType(VARCHAR), Length(255,true), Default() */
  case class UserRow(id: Long, loginname: String = "", name: String = "", password: String = "", token: String = "", phone: String = "", email: String = "", sex: String = "", birthday: String = "", pic: String = "", imtoken: String = "", createtime: Long = 0L, updatetime: Long = 0L, locationx: Double = 0.0, locationy: Double = 0.0, imuserid: String = "")
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Double]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Long], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Long], <<[Long], <<[Double], <<[Double], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (id, loginname, name, password, token, phone, email, sex, birthday, pic, imtoken, createtime, updatetime, locationx, locationy, imuserid) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(loginname), Rep.Some(name), Rep.Some(password), Rep.Some(token), Rep.Some(phone), Rep.Some(email), Rep.Some(sex), Rep.Some(birthday), Rep.Some(pic), Rep.Some(imtoken), Rep.Some(createtime), Rep.Some(updatetime), Rep.Some(locationx), Rep.Some(locationy), Rep.Some(imuserid)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column loginname SqlType(VARCHAR), Length(255,true), Default() */
    val loginname: Rep[String] = column[String]("loginname", O.Length(255,varying=true), O.Default(""))
    /** Database column name SqlType(VARCHAR), Length(255,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true), O.Default(""))
    /** Database column password SqlType(VARCHAR), Length(255,true), Default() */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true), O.Default(""))
    /** Database column token SqlType(VARCHAR), Length(255,true), Default() */
    val token: Rep[String] = column[String]("token", O.Length(255,varying=true), O.Default(""))
    /** Database column phone SqlType(VARCHAR), Length(255,true), Default() */
    val phone: Rep[String] = column[String]("phone", O.Length(255,varying=true), O.Default(""))
    /** Database column email SqlType(VARCHAR), Length(255,true), Default() */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true), O.Default(""))
    /** Database column sex SqlType(VARCHAR), Length(255,true), Default() */
    val sex: Rep[String] = column[String]("sex", O.Length(255,varying=true), O.Default(""))
    /** Database column birthday SqlType(VARCHAR), Length(255,true), Default() */
    val birthday: Rep[String] = column[String]("birthday", O.Length(255,varying=true), O.Default(""))
    /** Database column pic SqlType(VARCHAR), Length(255,true), Default() */
    val pic: Rep[String] = column[String]("pic", O.Length(255,varying=true), O.Default(""))
    /** Database column imToken SqlType(VARCHAR), Length(255,true), Default() */
    val imtoken: Rep[String] = column[String]("imToken", O.Length(255,varying=true), O.Default(""))
    /** Database column createTime SqlType(BIGINT), Default(0) */
    val createtime: Rep[Long] = column[Long]("createTime", O.Default(0L))
    /** Database column updateTime SqlType(BIGINT), Default(0) */
    val updatetime: Rep[Long] = column[Long]("updateTime", O.Default(0L))
    /** Database column locationX SqlType(DOUBLE), Default(0.0) */
    val locationx: Rep[Double] = column[Double]("locationX", O.Default(0.0))
    /** Database column locationY SqlType(DOUBLE), Default(0.0) */
    val locationy: Rep[Double] = column[Double]("locationY", O.Default(0.0))
    /** Database column imUserid SqlType(VARCHAR), Length(255,true), Default() */
    val imuserid: Rep[String] = column[String]("imUserid", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}

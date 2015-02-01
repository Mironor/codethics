package daos

import play.api.db.slick.Config.driver.simple._

object DBTableDefinitions {

  case class SnippetSubmition(id: Option[Long],
                              token: String,
                              snippet1: String,
                              snippet1Votes: Int,
                              snippet2: String,
                              snippet2Votes: Int)

  class SnippetSubmitions(tag: Tag) extends Table[SnippetSubmition](tag, "submitions") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def token = column[String]("token")

    def snippet1 = column[String]("snippet1", O.DBType("text"))

    def snippet1Votes = column[Int]("snippet1Votes")

    def snippet2 = column[String]("snippet2", O.DBType("text"))

    def snippet2Votes = column[Int]("snippet2Votes")

    def * = (id.?, token, snippet1, snippet1Votes, snippet2, snippet2Votes) <>(SnippetSubmition.tupled, SnippetSubmition.unapply)
  }

}

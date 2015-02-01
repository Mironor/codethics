package daos

import daos.DBTableDefinitions.{SnippetSubmition, SnippetSubmitions}
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._


object SnippetDAO {


  val slickSnippents = TableQuery[SnippetSubmitions]

  def findByToken(token: String) ={
    DB withSession { implicit session =>
      slickSnippents.filter(_.token === token).firstOption
    }
  }

  def insert(snippetSubmition: SnippetSubmition) = {
    DB withSession { implicit session =>
      slickSnippents += snippetSubmition
    }
  }

  def update(snippetSubmition: SnippetSubmition) = {
    DB withSession { implicit session =>
      slickSnippents.filter(_.id === snippetSubmition.id).update(snippetSubmition)
    }
  }

}

package controllers

import daos.DBTableDefinitions.SnippetSubmition
import daos.SnippetDAO
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import scala.annotation.tailrec
import scala.util.Random

object Application extends Controller {

  case class SubmitForm(snippet1: String, snippet2: String)

  val submitForm = Form(
    mapping(
      "snippet1" -> nonEmptyText,
      "snippet2" -> nonEmptyText
    )(SubmitForm.apply)(SubmitForm.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  def token(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) => Ok(views.html.poll(token, snippetSubmition.snippet1, snippetSubmition.snippet2))
      case None => Ok(views.html.index("The requested snippets were not found"))
    }
  }

  def tokenDone(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) => Ok(views.html.poll_done(
        snippetSubmition.snippet1,
        snippetSubmition.snippet1Votes,
        snippetSubmition.snippet2,
        snippetSubmition.snippet2Votes
      ))
      case None => Ok(views.html.index("The requested snippets were not found"))
    }
  }

  def voteSnippetOne(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) =>
        SnippetDAO.update(snippetSubmition.copy(snippet1Votes = snippetSubmition.snippet1Votes + 1))
        Redirect(routes.Application.tokenDone(token))
      case None => Ok(views.html.index("The requested snippets were not found"))
    }
  }

  def voteSnippetTwo(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) =>
        SnippetDAO.update(snippetSubmition.copy(snippet2Votes = snippetSubmition.snippet2Votes + 1))
        Redirect(routes.Application.tokenDone(token))
      case None => Ok(views.html.index("The requested snippets were not found"))
    }
  }

  def submit = Action { implicit request =>
    submitForm.bindFromRequest.fold({
      formWithErrors => Ok(views.html.index("Please fill both of the snippets"))
    }, {
      submitForm =>
        val token = generateToken()
        val snippet = SnippetSubmition(None, token, submitForm.snippet1, 0, submitForm.snippet2, 0)

        SnippetDAO.insert(snippet)

        Redirect(routes.Application.token(token))
    })
  }

  @tailrec
  private def generateToken(): String = {
    val token = generateRandomString(7)
    val snippet = SnippetDAO.findByToken(token)
    snippet match {
      case Some(_) => generateToken() // Snippet with this token already exists
      case None => token
    }
  }

  private def generateRandomString(n: Int): String = {
    val random = new Random(System.currentTimeMillis)
    random.alphanumeric.take(n).mkString
  }

}
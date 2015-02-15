package controllers

import daos.DBTableDefinitions.SnippetSubmition
import daos.SnippetDAO
import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.annotation.tailrec
import scala.util.Random

object Application extends Controller {

  case class SubmitForm(snippet1: String, snippet2: String)

  implicit val snippetFormSubmitionReads: Reads[(String, String)] = (
    (__ \ "snippet1").read[String] and
      (__ \ "snippet2").read[String]
    ).tupled

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def indexProxy(token: String) = index

  def submit = Action(BodyParsers.parse.json) { implicit request =>
    request.body.validate[(String, String)].map {
      case (snippet1: String, snippet2: String) =>
        val token = generateToken()
        val snippet = SnippetSubmition(None, token, snippet1, 0, snippet2, 0)

        SnippetDAO.insert(snippet)

        Ok(Json.obj("token" -> token))
    }.recoverTotal {
      error => BadRequest(Json.obj(
        "fields" -> JsError.toFlatJson(error)
      ))
    }
  }

  def poll(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) => Ok(Json.obj(
        "token" -> snippetSubmition.token,
        "snippet1" -> snippetSubmition.snippet1,
        "snippet2" -> snippetSubmition.snippet2
      ))
      case None => Ok(Json.obj(
        "error" -> 404
      ))
    }
  }

  def result(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) => Ok(Json.obj(
        "snippet1" -> snippetSubmition.snippet1,
        "snippet1Votes" -> snippetSubmition.snippet1Votes,
        "snippet2" -> snippetSubmition.snippet2,
        "snippet2Votes" -> snippetSubmition.snippet2Votes
      ))
      case None => Ok(Json.obj(
        "error" -> 404
      ))
    }
  }

  def voteSnippetOne(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) =>
        SnippetDAO.update(snippetSubmition.copy(snippet1Votes = snippetSubmition.snippet1Votes + 1))
        Ok(Json.obj(
          "token" -> token
        ))
      case None => Ok(Json.obj(
        "error" -> 404
      ))
    }
  }

  def voteSnippetTwo(token: String) = Action { implicit request =>
    SnippetDAO.findByToken(token) match {
      case Some(snippetSubmition) =>
        SnippetDAO.update(snippetSubmition.copy(snippet2Votes = snippetSubmition.snippet2Votes + 1))
        Ok(Json.obj(
          "token" -> token
        ))
      case None => Ok(Json.obj(
        "error" -> 404
      ))
    }
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
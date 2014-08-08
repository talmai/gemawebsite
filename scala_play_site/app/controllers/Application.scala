package controllers

import play.api._
import play.api.mvc._
import com.gemaestudio._
import anorm._
import play.api.db._
import play.api.Play.current
import scala.collection.mutable.MutableList
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json._
import java.io.FileOutputStream
import java.io.ByteArrayInputStream
import java.net.URL
import java.io.File
import java.io.OutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.io.BufferedOutputStream

object Application extends Controller {
  
  def index = Action {
	Ok(views.html.index(Client.findAllMainPage(), Project.findAllMainPage()))
  }

  def about = Action {
    Ok(views.html.about("Your new application is ready."))
  }

  def clients = Action {
    Ok(views.html.clients(Client.findAllClientPage()))
  }

  def portfolio(typeId: Long, projectId: Option[Long]) = Action {
    var updTypeId:Long = typeId
    if (updTypeId == -1){
    	val project:Project = Project.findById(projectId)
    	updTypeId = project.portfolio_type.getOrElse(1).asInstanceOf[Long]
    }
    Ok(views.html.portfolio(updTypeId, Project.findByPortfolioType(updTypeId), Project.findExistingProjectTypes(), projectId))
  }

  def services = Action {
    Ok(views.html.services("Your new application is ready."))
  }

  def contact = Action {
    Ok(views.html.contact("Your new application is ready."))
  }

  val contactForm = Form(
	  tuple(
	    "name" -> nonEmptyText,
	    "email" -> nonEmptyText,
	    "message" -> text
	  )
  )

  def contactSubmit = Action {
    implicit request =>
      contactForm.bindFromRequest.fold(
        errors => 
        	BadRequest(toJson(
		      Map("status" -> "KO", "message" -> "error")
        	)),
        {
          case (name, email, message) =>
	          import anorm.NotAssigned
			  val me = User( name = name, email = email, message = message)
			  User.contactAttempt(me)
			  EmailNotifier.sendMail(me)
              Ok(toJson(Map("status" -> "OK", "message" -> name)))
        })
  }

  def browser = Action {
    Ok(views.html.browser("Your new application is ready."))
  }
}

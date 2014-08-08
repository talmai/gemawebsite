import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import com.gemaestudio.Client
import com.gemaestudio.Project

object Global extends GlobalSettings {

  override def onError(request: RequestHeader, ex: Throwable) = {
    InternalServerError(
    		views.html.index(Client.findAllMainPage(), Project.findAllMainPage())
      )
  }

  override def onHandlerNotFound(request: RequestHeader) = {
    NotFound(
    		views.html.index(Client.findAllMainPage(), Project.findAllMainPage())
      )
  }

  override def onBadRequest(request: RequestHeader, error: String) = {
	 BadRequest(
			 views.html.index(Client.findAllMainPage(), Project.findAllMainPage())
      )
  }
}
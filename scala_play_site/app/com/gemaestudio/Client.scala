package com.gemaestudio

import anorm._
import play.api.db._
import play.api.Play.current


class Client(var id: Pk[Long] = NotAssigned, val name: String, val image: String, val enable_main_page_display: Boolean = false, 
    		val title: Option[String], val description: Option[String], val main_page_banner_image: Option[String], val main_page_content_line_one: Option[String], 
    		val main_page_content_line_two: Option[String], val main_page_content_line_three: Option[String],
    		val main_page_bar_image: Option[String], val presentation_order: Option[Int], val project_id: Option[Int]) {
	override def toString = 
	  "{id:"+id +
	  			", name:'"+name+
	  			"', image:'"+image+
	  			"', enable_main_page_display:"+enable_main_page_display+
	  			", title:'"+title+
	  			"', project_id:'"+project_id+
	  			"', description:'"+description+
	  			"', main_page_banner_image:'"+main_page_banner_image+
	  			"', main_page_content_line_one:'"+main_page_content_line_one+
	  			"', main_page_content_line_two:'"+main_page_content_line_two+
	  			"', main_page_content_line_three:'"+main_page_content_line_three+
	  			"', main_page_bar_image:'"+main_page_bar_image+
	  			"', presentation_order:'"+presentation_order+"'}"
}

object Client {
  def apply(id: Pk[Long], name: String, image: String, enable_main_page_display: Boolean = false, title: Option[String], description: Option[String], 
		  	main_page_banner_image: Option[String], main_page_content_line_one: Option[String], main_page_content_line_two: Option[String], 
		  	main_page_content_line_three: Option[String], main_page_bar_image: Option[String], presentation_order: Option[Int], project_id: Option[Int]) =
    new Client(
      id = id,
      name = name,
      image = image,
      enable_main_page_display = enable_main_page_display,
      title = title,
      description = description,
      main_page_banner_image = main_page_banner_image,
      main_page_content_line_one = main_page_content_line_one,
      main_page_content_line_two = main_page_content_line_two,
      main_page_content_line_three = main_page_content_line_three,
      main_page_bar_image = main_page_bar_image,
      presentation_order = presentation_order,
      project_id = project_id)

  def find( id: Pk[Long] ) : List[Client] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~String~String~Boolean~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[Int]~Option[Int]] = get[Pk[Long]]("id") ~ str("name") ~ str("image") ~ get[Boolean]("enable_main_page_display") ~ get[Option[String]]("title") ~ get[Option[String]]("description") ~ get[Option[String]]("main_page_banner_image") ~ get[Option[String]]("main_page_content_line_one") ~ get[Option[String]]("main_page_content_line_two") ~ get[Option[String]]("main_page_content_line_three") ~ get[Option[String]]("main_page_bar_image") ~ get[Option[Int]]("presentation_order") ~ get[Option[Int]]("project_id")
	DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~String~String~Boolean~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[Int]~Option[Int]]] = rp *
	    val results = SQL("SELECT * FROM clients where id = {id}") on("id" -> id) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~name~image~enable_main_page_display~title~description~main_page_banner_image~main_page_content_line_one~main_page_content_line_two~main_page_content_line_three~main_page_bar_image~presentation_order~project_id  => Client(pk,name,image,enable_main_page_display,title,description,main_page_banner_image,main_page_content_line_one,main_page_content_line_two,main_page_content_line_three,main_page_bar_image,presentation_order,project_id)
	    }
	}
  }

  def findAll(sqlQuery: String = "SELECT * FROM clients") : List[Client] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~String~String~Boolean~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[Int]~Option[Int]] = get[Pk[Long]]("id") ~ str("name") ~ str("image") ~ get[Boolean]("enable_main_page_display") ~ get[Option[String]]("title") ~ get[Option[String]]("description") ~ get[Option[String]]("main_page_banner_image") ~ get[Option[String]]("main_page_content_line_one") ~ get[Option[String]]("main_page_content_line_two") ~ get[Option[String]]("main_page_content_line_three") ~ get[Option[String]]("main_page_bar_image") ~ get[Option[Int]]("presentation_order") ~ get[Option[Int]]("project_id")
	DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~String~String~Boolean~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[String]~Option[Int]~Option[Int]]] = rp *
	    val results = SQL(sqlQuery) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~name~image~enable_main_page_display~title~description~main_page_banner_image~main_page_content_line_one~main_page_content_line_two~main_page_content_line_three~main_page_bar_image~presentation_order~project_id  => Client(pk,name,image,enable_main_page_display,title,description,main_page_banner_image,main_page_content_line_one,main_page_content_line_two,main_page_content_line_three,main_page_bar_image,presentation_order,project_id)
	    }
	}
  }

  def findAllClientPage() : List[Client] = {
    findAll("SELECT * FROM clients where presentation_order is not NULL order by presentation_order")
  }
  
  def findAllMainPage() : List[Client] = {
    findAll("SELECT * FROM clients where enable_main_page_display = 1 order by presentation_order")
  }
}
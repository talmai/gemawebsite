package com.gemaestudio

import anorm._
import play.api.db._
import play.api.Play.current


class ProjectImage(var id: Pk[Long] = NotAssigned, val image: String, val project: Option[Int]) {
	override def toString = 
	  "prjImg: { id:"+id +
	  ", image:"+image+
	  ", project:"+project+"}"
}

class ProjectType(var id: Pk[Long] = NotAssigned, val name: String) {
	override def toString = 
	  "prjType: { id:"+id +
	  ", name:"+name+"}"
}

class Project(var id: Pk[Long] = NotAssigned, val portfolio_type: Option[Int], val name: String, val image: String, val enable_main_page_display: Boolean = false, 
    		val description: Option[String], val main_page_description: Option[String], val presentation_order: Option[Int]) {
  
    var gallery = List[ProjectImage]();

    override def toString = 
	  "{id:'"+id +
	  "', portfolio_type:'"+portfolio_type+
	  "', name:'"+name+
	  "', image:'"+image+
	  "', enable_main_page_display:'"+enable_main_page_display+
	  "', description:'"+description+
	  "', main_page_description:'"+main_page_description+
	  "', presentation_order:'"+presentation_order+
	  "', gallery: '"+
	  		gallery +
	  "'}"

	def add(img: ProjectImage) = gallery ::= img
}


object Project {
  def apply(id: Pk[Long] = NotAssigned, portfolio_type: Option[Int], name: String, image: String, enable_main_page_display: Boolean = false, 
    		description: Option[String], main_page_description: Option[String], presentation_order: Option[Int]) =
    new Project(
      id = id,
      portfolio_type = portfolio_type,
      name = name,
      image = image,
      enable_main_page_display = enable_main_page_display,
      description = description,
      main_page_description = main_page_description,
      presentation_order = presentation_order)

  def findByPortfolioType( id: Long ) : List[Project] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]] = get[Pk[Long]]("id") ~ get[Option[Int]]("portfolio_type") ~ str("name") ~ str("image") ~ get[Boolean]("enable_main_page_display") ~ get[Option[String]]("description") ~ get[Option[String]]("main_page_description") ~ get[Option[Int]]("presentation_order")
	val results: List[Project] = DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]]] = rp *
	    val results = SQL("SELECT * FROM project where portfolio_type = {id}") on("id" -> id) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~portfolio_type~name~image~enable_main_page_display~description~main_page_description~presentation_order  => Project(pk,portfolio_type,name,image,enable_main_page_display,description,main_page_description,presentation_order)
	    }
	}
	results.foreach(proj => findGallery(proj.id).foreach(img => proj.add(img)))
	results
  }

  def findGallery( projectId: Pk[Long] ) : List[ProjectImage] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~String~Option[Int]] = get[Pk[Long]]("id") ~ str("image") ~ get[Option[Int]]("project")
	DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~String~Option[Int]]] = rp *
	    val results = SQL("SELECT * FROM project_images where project = {id} order by id desc") on("id" -> projectId) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~image~project => new ProjectImage(pk,image,project)
	    }
	}
  }

  def findAll(sqlQuery: String = "SELECT * FROM project") : List[Project] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]] = get[Pk[Long]]("id") ~ get[Option[Int]]("portfolio_type") ~ str("name") ~ str("image") ~ get[Boolean]("enable_main_page_display") ~ get[Option[String]]("description") ~ get[Option[String]]("main_page_description") ~ get[Option[Int]]("presentation_order")
	val results: List[Project] = DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]]] = rp *
	    val results = SQL(sqlQuery) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~portfolio_type~name~image~enable_main_page_display~description~main_page_description~presentation_order  => Project(pk,portfolio_type,name,image,enable_main_page_display,description,main_page_description,presentation_order)
	    }
	}
	results.foreach(proj => findGallery(proj.id).foreach(img => proj.add(img)))
	results
  }

  def findAllMainPage() : List[Project] = {
    findAll("SELECT * FROM project where enable_main_page_display = 1 order by presentation_order")
  }

  def findById(id: Option[Long]) : Project = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]] = get[Pk[Long]]("id") ~ get[Option[Int]]("portfolio_type") ~ str("name") ~ str("image") ~ get[Boolean]("enable_main_page_display") ~ get[Option[String]]("description") ~ get[Option[String]]("main_page_description") ~ get[Option[Int]]("presentation_order")
	val results: List[Project] = DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~Option[Int]~String~String~Boolean~Option[String]~Option[String]~Option[Int]]] = rp *
	    val results = SQL("SELECT * FROM project where id = {id}") on("id" -> id) as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~portfolio_type~name~image~enable_main_page_display~description~main_page_description~presentation_order  => Project(pk,portfolio_type,name,image,enable_main_page_display,description,main_page_description,presentation_order)
	    }
	}
	results.foreach(proj => findGallery(proj.id).foreach(img => proj.add(img)))
	results(0)
  }
  
  def findExistingProjectTypes() : List[ProjectType] = {
	import anorm.SqlParser.{get,str}
	import anorm.Pk
	 
	// Create a row parser object
	val rp: RowParser[Pk[Long]~String] = get[Pk[Long]]("id") ~ str("name")
	DB.withConnection("default") { implicit connection =>
	    // Parse All Results from SQL Statement
	    val rsp : ResultSetParser[List[Pk[Long]~String]] = rp *
	    val results = SQL("select * from portfolio_type where id in (select distinct(portfolio_type) from project)") as ( rsp )
	    // Use pattern matching to re-construct our objects
	    results map {
	        case pk~name => new ProjectType(pk,name)
	    }
	}
  }
}
package com.gemaestudio

import anorm._
import play.api.db._
import play.api.Play.current

class User(val name: String, val email: String, val message: String) {

}

object User {
  def apply(name: String, email:String, message: String) =
    new User(
      name = name,
      email = email,
      message = message)
  
  def contactAttempt(p:User) : Pk[Long] = {
    // Open the default database
    DB.withConnection ("default") { implicit connection =>
        // Use string-replacement to construct the SQL query
        SQL("""
            INSERT INTO user_contact(name, email, message) 
             values ({name}, {email}, {message})
            """).on(
            "name"  -> p.name,
            "email"  -> p.email,
            "message"  -> p.message
        // Execute the statement
        ).executeInsert() // returns Option[Long] 
    } match {
        // Extracts the Long value, if it exists
        case Some(long) => new Id[Long](long)
        // If no value is returned, the Insert failed
        case None    => throw new Exception(
           "SQL Error - Did not save Person"
        )
    }
  }
}
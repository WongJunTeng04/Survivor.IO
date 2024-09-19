package util

// Imports
import model.Record
import scalikejdbc._

/***************************************************************************************
 *    Title: AddressApp (Database)
 *    Author: Dr. Chin Teck Min (Lecturer)
 *    Date: 21/08/2024
 *    Code version: SCALA 2.12.19/ ScalaFX8
 *    Availability: Tutorial from Uni work
 *
 ***************************************************************************************/

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:gameDB;create=true;"

  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "user", "password")

  implicit val session = AutoSession
}

object Database extends Database {
  def setupDB(): Unit = {
    if (!hasDBInitialize) {
      Record.initializeTable()
    }
  }

  def hasDBInitialize: Boolean = {
    DB getTable "record" match {
      case Some(_) => true
      case None => false
    }
  }
}

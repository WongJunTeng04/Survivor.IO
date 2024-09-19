package model


/***************************************************************************************
 *    Title: AddressApp (Database)
 *    Author: Dr Chin Teck Min
 *    Date: May 9, 2017
 *    Code version: Scala 2.12.19
 *    Availability: -
 *
 ***************************************************************************************/

// Imports
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalikejdbc._
import util.Database
import scala.util.Try

// Record
class Record(_name: String, _score: Int){
  // Name and Score
  val name: StringProperty = new StringProperty(_name)
  val score: IntegerProperty = IntegerProperty(_score)

  // Save the value into the database
  def save(): Try[Int] = {
    Try(DB autoCommit { implicit session =>
      sql"""
         insert into record(
                            name,
                            score
                            ) values
                                    (
                                    ${name.value},
                                    ${score.value}
                                    )
      """.update.apply()
    })
  }
}

// Object Record
object Record extends Database {
  // Initialising the table
  def initializeTable(): Boolean = {
    if (!tableExists("record")) {
      DB autoCommit { implicit session =>
        sql"""
            create table record(
               id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
               name varchar(255),
               score int
              )
          """.execute.apply()
      }
    }
    true
  }

  // Get the records of the table
  def getRecords(count: Int): List[Record] = {
    DB readOnly { implicit session =>
      val records = sql"""
      select * from record order by score desc fetch first ${count} rows only
    """.map(rs => {
        new Record(
          rs.string("name"),
          rs.int("score")
        )
      }).list.apply()
      records
    }
  }

  // Check if table exists
  private def tableExists(tableName: String): Boolean = {
    DB getTable tableName match {
      case Some(_) => true
      case None => false
    }
  }
}
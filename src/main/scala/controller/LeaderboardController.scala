// Import
import model.Record
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalafx.beans.value.ObservableValue
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

// Leaderboard Controller
@sfxml
class LeaderboardController(
                             val rankTable: TableView[Record],
                             val numberTableColumn: TableColumn[Record, Int],  // Changed from Int to Number
                             val nameTableColumn: TableColumn[Record, String],
                             val scoreTableColumn: TableColumn[Record, String]
                           ) {

  // Records
  val records: List[Record] = Record.getRecords(10) // Fetch top 10 records
  private val observableRecords = CommonUtil.listToObservableBuffer(records)  // Initialize observableRecords
  rankTable.items = observableRecords

  // Number Table Column
  numberTableColumn.cellValueFactory = { cellData =>
    IntegerProperty(observableRecords.indexOf(cellData.value) + 1).asInstanceOf[ObservableValue[Int, Int]]
  }

  // Name Table Column
  nameTableColumn.cellValueFactory = {
    _.value.name
  }

  // Score Table Column
  scoreTableColumn.cellValueFactory = { x =>
    new StringProperty(x.value.score.value.toString)
  }

  // Handle going back home
  def handleBack(): Unit = {
    Main.showHome()
  }
}

/***************************************************************************************
 *    Title: LeaderboardController.scala
 *    Author: Dr. Chin Teck Min (Lecturer) & ChatGPT
 *    Date: 21/08/2024
 *    Code version: Scala 2.12.19/ ScalaFX8
 *    Availability: Tutorial from Uni work
 *
 ***************************************************************************************/
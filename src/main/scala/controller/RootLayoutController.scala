package controller

// Import
import scalafxml.core.macros.sfxml

// Root Layout Controller
@sfxml
class RootLayoutController(){
  def handleClose(): Unit = {
    System.exit(0)
  }
}
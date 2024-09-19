import scalafxml.core.macros.sfxml

@sfxml
class HowToPlayController {
  def handleBack(): Unit = {
    Main.showHome()
  }
}

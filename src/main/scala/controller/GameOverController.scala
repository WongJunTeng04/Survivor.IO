// Import
import scalafxml.core.macros.sfxml

// Game Over Controller
@sfxml
class GameOverController {

  // Allows the player to restart the game
  def restartGame(): Unit = {
    Main.showGame()
  }

  // Allows the player to go back to the main menu
  def homeBtn(): Unit = {
    Main.showHome()
  }
}

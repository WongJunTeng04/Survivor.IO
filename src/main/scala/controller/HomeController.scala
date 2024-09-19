import Main.{showHowToPlay, showLeaderBoard}
import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml

@sfxml
class HomeController(private val NameField: TextField) {

  // This method is called when the START button is pressed
  def handlePlay(): Unit = {
    // Capture the user's name from the TextField
    Main.playerName = NameField.text.value
    Main.showGame() // Transition to the game screen
  }
  // Initialize the TextField with the current player's name, if an
  NameField.text = Main.playerName

  // Leaderboard pressed
  def handleLeaderBoard(): Unit = {
    showLeaderBoard()
  }

  // Exit Button Pressed
  def handleExit(): Unit = {
    System.exit(0)
  }

  // Handle How To Play button
  def handleHowToPlay(): Unit = {
    showHowToPlay()
  }
}
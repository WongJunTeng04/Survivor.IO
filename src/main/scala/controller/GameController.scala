package controller

// Imports
import model.GameModel
import view.GameView
import scalafx.animation.AnimationTimer
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.Includes._

// Class GameController
class GameController(val model: GameModel, val view: GameView, val playerName: String) {

  // Initialize the character and platforms
  def initialize(): Unit = {
    view.drawPlatforms(model.platforms)
    view.drawCharacter(model.character)

    // Set the player's name using the passed parameter and display it.
    view.playerNameText.text = s"PLAYER: $playerName"

    // Animation timer to run the game loop and also used as a scoring system.
    var lastTime = 0L
    val timer = AnimationTimer { currentTime =>
      if (lastTime != 0) {
        val elapsedTime = (currentTime - lastTime) / 1e9 // Convert to seconds // Logic referred from: https://onecompiler.com/scala/3zvdpxq54
        model.update(elapsedTime)
        view.updateCharacter(model.isCharacterMovingLeft, model.isCharacterMovingRight)
        view.drawProjectiles(model.getProjectiles)
        view.updateProjectiles()
        view.updateHealth(model.character.hp)
        view.updateScore(model.score)
      }
      lastTime = currentTime
    }
    timer.start()

    /***************************************************************************************
     *    Title: Keyboard and Mouse Input (using ScalaFX)
     *    Author: Mark Lewis
     *    Date: 12/9/2016
     *    Code version: YouTube Tutorial
     *    Availability: https://www.youtube.com/watch?v=DGACpb-ALG4&t=561
     *
     ***************************************************************************************/
    // To read keyboard presses // Referred to https://www.youtube.com/watch?v=DGACpb-ALG4&t=561s (Mark Lewis- Keyboard and Mouse Input (using ScalaFX))
    view.gamePane.onKeyPressed = (e: KeyEvent) => {
      e.code match {
        case KeyCode.A => model.startMovingLeft()
        case KeyCode.D => model.startMovingRight()
        case KeyCode.Space => model.startJump()
        case _ => // Ignore other keys
      }
    }

    // To read keyboard presses
    view.gamePane.onKeyReleased = (e: KeyEvent) => {
      e.code match {
        case KeyCode.A => model.stopMovingLeft()
        case KeyCode.D => model.stopMovingRight()
        case _ => // Ignore other keys
      }
    }
    view.gamePane.requestFocus()
  }
}

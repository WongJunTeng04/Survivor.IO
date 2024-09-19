package view

// Imports
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text
import model.{Character, Platform, Projectile}
import scala.collection.mutable.ListBuffer

// Game View, this is where the game runs on
class GameView {
  val gamePane: Pane = new Pane() {
    prefWidth = 800
    prefHeight = 700
    // Set the background image using CSS
    styleClass += "game-pane"
  }

  // Player Text to be displayed at the top left of the screen
  val playerNameText: Text = new Text {
    text = "PLAYER: "
    layoutX = 10
    layoutY = 20
    styleClass += "player-name-text"
  }

  // Score Text to be displayed at the top left of the screen
  private val scoreText: Text = new Text {
    text = "SCORE: 0"
    layoutX = 10
    layoutY = 40
    styleClass += "score-text"
  }

  // Health Text to be displayed at the top left of the screen
  private val healthText: Text = new Text {
    text = "HEALTH: 5"
    layoutX = 10
    layoutY = 60
    styleClass += "health-text"
  }

  // Character and Projectile View
  private var characterView: Option[CharacterView] = None
  private val projectileViews: ListBuffer[ProjectileView] = ListBuffer()

  // Draw the platforms with images
  def drawPlatforms(platforms: List[Platform]): Unit = {
    gamePane.children.clear() // Clear the pane, a precaution step.

    platforms.foreach { platform => // For each platform, draw it out on the game pane.
      gamePane.children.add(new PlatformView(platform))
    }
    characterView.foreach(view => gamePane.children.add(view))
    // Add the Player name Text and Score Text
    gamePane.children.add(playerNameText)
    gamePane.children.add(scoreText)
    gamePane.children.add(healthText)
  }

  /***************************************************************************************
   *    Title: Animation for GIF
   *    Author: SelfTeachingKings
   *    Date: May 9, 2017
   *    Code version: -
   *    Availability: https://www.deviantart.com/selfteachingkings/art/Pixel-Man-Running-Animation-Coursework-679727903
   *
   ***************************************************************************************/
  // Draw the characters and adds it to the pane.
  def drawCharacter(character: Character): Unit = {
    val leftImagePath = "images/RunnerLeft.GIF" // Facing Left Character Image/Gif
    val rightImagePath = "images/RunnerRight.GIF" // Facing Right Character Image/Gif
    val view = new CharacterView(character, leftImagePath, rightImagePath)
    characterView = Some(view)
    gamePane.children.add(view)
  }

  // Update the direction the character is facing.
  def updateCharacter(isMovingLeft: Boolean, isMovingRight: Boolean): Unit = {
    characterView.foreach(_.update(isMovingLeft, isMovingRight))
  }

  // Draw the projectiles the players have to dodge
  // Same logic as the one above, clear first then add it.
  def drawProjectiles(projectiles: List[Projectile]): Unit = {
    projectileViews.foreach(view => gamePane.children.remove(view))
    projectileViews.clear()
    projectiles.foreach { projectile =>
      val projectileView = new ProjectileView(projectile)
      projectileViews += projectileView
      gamePane.children.add(projectileView)
    }
  }

  // Update the projectiles movement.
  def updateProjectiles(): Unit = {
    projectileViews.foreach(_.update())
  }

  // Update the score.
  def updateScore(score: Int): Unit = {
    scoreText.text = s"SCORE: $score"
  }

  def updateHealth(health: Int): Unit = {
    healthText.text = s"HEALTH: $health"
  }

  // Method to clear the game pane when the game is over
  def clearGamePane(): Unit = {
    gamePane.children.clear() // Remove all children from the game pane
  }
}

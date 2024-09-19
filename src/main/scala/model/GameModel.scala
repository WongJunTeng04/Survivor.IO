package model

// Import
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success}

// Class Game Model
class GameModel(playerName: String) {

  // Initializing platforms, character, and projectiles
  // Platforms
  val platforms: List[Platform] = Platform.Platforms
  // Characters
  val character: Character = new Character(100, 520, 65, 65, 5)
  // Projectiles
  var projectiles: ListBuffer[Projectile] = ListBuffer()
  // Score
  var score: Int = 0
  // Score Timer
  private var scoreTimer: Double = 0.0

  // Character Movement Logic:
  // Gravity
  private val gravity: Double = 0.25
  // Jump height AKA Jump Strength
  private val jumpStrength: Double = -10
  // Collision Tolerance
  private val collisionTolerance: Double = 0.4

  // Pane Parameters
  private val paneWidth: Double = 800 // Pane (Width)
  private val paneHeight: Double = 700 // Pane (Height)

  // Movement Logic
  private var isMovingLeft: Boolean = false // The character is moving left, set to FALSE
  private var isMovingRight: Boolean = false // The character is moving right, set to FALSE
  private var isJumping: Boolean = false // The character is Jumping, set to FALSE

  // Projectile
  private var projectileSpawnTimer: Double = 2 // Projectile Spawner
  private val projectileSpawnInterval: Double = 1.0 // The interval the projectile will spawn in

  // Flag to check if the game is over
  private var gameOver: Boolean = false

  // Called when the game is over and used in Main.scala to show Game Over Screen.
  var onGameOver: () => Unit = () => {}

  // Methods to check if the character is moving left or right
  def isCharacterMovingLeft: Boolean = isMovingLeft
  def isCharacterMovingRight: Boolean = isMovingRight

  // Methods to get the list of projectiles
  def getProjectiles: List[Projectile] = projectiles.toList

  // This methods updates as long as the Character has more than 0 HP and only update if the game is not over
  def update(elapsedTime: Double): Unit = {
    if (!gameOver) {
      if (character.hp > 0) {

        // Character-related updates
        character.applyGravity(gravity)

        // Character movement logic
        if (isMovingLeft) character.moveLeft()
        if (isMovingRight) character.moveRight()

        // Updates the character position
        character.updatePosition()
        // Updates the character deceleration
        character.applyDeceleration()

        // Collision logic with platform with character
        platforms.foreach { platform =>
          if (platform.isCharacterLanding(character, collisionTolerance)) {
            character.y = platform.y - character.height
            character.velocityY = 0
            isJumping = false
          } else if (platform.isCharacterHittingFromBelow(character, collisionTolerance)) {
            character.y = platform.y + platform.height
            character.velocityY = 0
          }
        }

        // Constraint character within pane (Width/X-axis)
        if (character.x < 0) {
          character.x = 0
          character.velocityX = 0
        } else if (character.x + character.width > paneWidth) {
          character.x = paneWidth - character.width
          character.velocityX = 0
        }

        // Constraint character within pane (Height/Y-axis)
        if (character.y < 0) {
          character.y = 0
          character.velocityY = 0
        } else if (character.y + character.height > paneHeight) {
          character.y = paneHeight - character.height
          character.velocityY = 0
          isJumping = false
        }

        // Projectile-related updates
        projectiles.foreach { projectile =>
          projectile.move()
          projectile.updateLifeTime()

          // Collision check between character and projectiles. Remove 1 HP from character when character gets hit
          // by projectile.
          if (projectile.isCollidingWith(character)) {
            character.hp -= 1
            projectiles -= projectile // Remove the projectile after collision
          }

          // Remove projectiles that are off-screen
          if (projectile.isOffScreen(paneWidth)) {
            projectiles -= projectile
          }
        }

        // Spawn projectiles (Not more than 4 at a time)
        projectileSpawnTimer += elapsedTime
        if (projectileSpawnTimer >= projectileSpawnInterval && projectiles.size < 4) {
          addProjectile(paneWidth, scala.util.Random.nextDouble() * paneHeight, 4)
        }

        // Update the score every second
        scoreTimer += elapsedTime
        if (scoreTimer >= 1.0) {
          score += 1
          scoreTimer = 0.0
        }
      } else {
        // When HP is 0 or below, trigger game over
        gameOver = true // Set the flag to true to prevent further updates for the game loop
        saveScoreToLeaderboard(playerName, score)
        onGameOver()
      }
    }
  }

  // Add projectiles
  private def addProjectile(x: Double, y: Double, velocityX: Double): Unit = {
    val startX = paneWidth + scala.util.Random.nextDouble() * (paneWidth / 2)
    val adjustedY = if (y > 650 - 10) 650 - 10 else y
    val radius = 10.0 // Adjust the radius as needed

    if (projectiles.size < 4) {
      projectiles += new Projectile(startX, adjustedY, radius, velocityX)
    }
  }

  // Methods for character movement control
  // Start Moving Left
  def startMovingLeft(): Unit = {
    isMovingLeft = true
  }
  // Start Moving Right
  def startMovingRight(): Unit = {
    isMovingRight = true
  }
  // Stop Moving Left
  def stopMovingLeft(): Unit = {
    isMovingLeft = false
  }
  // Stop Moving Right
  def stopMovingRight(): Unit = {
    isMovingRight = false
  }
  // Start Jump
  def startJump(): Unit = {
    if (!isJumping) {
      character.startJump(jumpStrength)
      isJumping = true
    }
  }

  // Save the score to the Leaderboard.
  private def saveScoreToLeaderboard(playerName: String, score: Int): Unit = {
    val record = new Record(playerName, score)
    record.save() match {
      case Success(_) =>
        println(s"Score $score saved successfully!")
      case Failure(exception) =>
        println(s"Failed to save score: ${exception.getMessage}")
    }
  }
}
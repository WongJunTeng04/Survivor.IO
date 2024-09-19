package model

// Import
import scalafx.scene.media.AudioClip

// Class for character that extends MovableObject with Sound
class Character(
                 var x: Double,
                 var y: Double,
                 var width: Double,
                 var height: Double,
                 var velocityX: Double = 0,
                 var velocityY: Double = 0,
                 var hp: Int = 5
               ) extends MovableObject with Sound {

  // Variables
  private val maxSpeed: Double = 4
  private val acceleration: Double = 2.5
  private val deceleration: Double = 1


  // Implementing the Sound trait for jumping, whenever they jump a sound plays
  override val audioClip: AudioClip = new AudioClip(getClass.getResource("/sounds/Jump.mp3").toString) {
    volume = 0.05
  }

  // Override method for update position from MovableObject abstract class
  override def updatePosition(): Unit = {
    super.updatePosition()
    applyDeceleration()
  }

  // Move Left method
  def moveLeft(): Unit = {
    velocityX -= acceleration
    velocityX = math.max(velocityX, -maxSpeed)
  }

  // Move Right method
  def moveRight(): Unit = {
    velocityX += acceleration
    velocityX = math.min(velocityX, maxSpeed)
  }

  // Start Jump
  def startJump(jumpStrength: Double): Unit = {
    velocityY = jumpStrength
    playSound()
  }

  // Pull the characters down where gravity is applied
  def applyGravity(gravity: Double): Unit = {
    velocityY += gravity
  }

  // Apply deceleration to the player
  def applyDeceleration(): Unit = {
    if (velocityX > 0) {
      velocityX -= deceleration
    } else if (velocityX < 0) {
      velocityX += deceleration
    }
  }
}

/***************************************************************************************
 *    Title: Jump Sound Effect
 *    Author: Karbon13
 *    Date: 22/8/2024
 *    Code version: -
 *    Availability: https://www.youtube.com/watch?v=Msx3flEqeq8
 *
 ***************************************************************************************/
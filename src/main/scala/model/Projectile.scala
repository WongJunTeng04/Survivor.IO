package model

// Import
import scalafx.scene.media.AudioClip

// Class for Projectile that extends MovableObject with Sound
class Projectile(
                  var x: Double,
                  var y: Double,
                  val radius: Double,
                  var velocityX: Double,
                  var lifeTime: Double = 0
                ) extends MovableObject with Sound {

  // Override variable for Velocity Y because the projectiles never move vertically, only horizontally.
  override var velocityY: Double = 0 // Projectiles do not move vertically

  // Implementing the Sound trait for collision with the character
  override val audioClip: AudioClip = new AudioClip(getClass.getResource("/sounds/HitSoundEffect.mp3").toString) {
    volume = 0.05
  }

  // Override method for updatePosition for movement
  override def updatePosition(): Unit = {
    move()
  }

  // Move method (Right to Left)
  def move(): Unit = {
    x -= velocityX
  }

  // Check if the projectile is off the screen
  def isOffScreen(paneWidth: Double): Boolean = {
    x + radius < 0 || x - radius > paneWidth
  }

  // Collision detection with the character
  def isCollidingWith(character: Character): Boolean = {
    val dx = x - (character.x + character.width / 2)
    val dy = y - (character.y + character.height / 2)
    val distance = Math.sqrt(dx * dx + dy * dy)

    // Playing sound effect when collision with the character occurs
    if (distance < (radius + character.width / 2)) {
      playSound() // Play the hit sound when there is a collision of projectiles with character.
      true
    } else {
      false
    }
  }

  // Lifetime before they de-spawn
  def updateLifeTime(): Unit = {
    lifeTime += 1
  }
}

/***************************************************************************************
 *    Title: Something being hit - Sound Effect
 *    Author: dyl0n
 *    Date: 22/8/2024
 *    Code version: -
 *    Availability: https://www.youtube.com/watch?v=JcG_ugrzfHE
 *
 ***************************************************************************************/
package model

// Case class for the Platform
case class Platform(
                     x: Double,
                     y: Double,
                     width: Double,
                     height: Double
                   ) {

  // Method to check if the character is landing on the platform
  def isCharacterLanding(character: Character, collisionTolerance: Double): Boolean = {
    character.velocityY > 0 &&
      character.x < x + width &&
      character.x + character.width > x &&
      character.y + character.height <= y + collisionTolerance &&
      character.y + character.height + character.velocityY >= y
  }

  // Method to check if the character hits the platform from below
  def isCharacterHittingFromBelow(character: Character, collisionTolerance: Double): Boolean = {
    character.velocityY < 0 &&
      character.x < x + width &&
      character.x + character.width > x &&
      character.y >= y + height &&
      character.y + character.velocityY <= y + height + collisionTolerance
  }
}

// Object for Platform
object Platform {
  // Drawing the platforms
  val Platforms: List[Platform] = List(
    Platform(0, 650, 800, 30),      // Full-width bottom platform
    Platform(100, 520, 130, 20),    // First jump, left side
    Platform(400, 480, 110, 20),    // Middle, slightly higher
    Platform(700, 450, 100, 20),    // Right side, narrow platform
    Platform(200, 400, 120, 20),    // Left-middle, higher
    Platform(600, 360, 90, 20),     // Right side, medium height
    Platform(350, 320, 140, 20),    // Middle, wide platform
    Platform(80, 280, 110, 20),     // Left side, lower but further
    Platform(750, 240, 100, 20),    // Right side, narrow and higher
    Platform(300, 180, 150, 20)     // Middle, final platform, wider
  )
}

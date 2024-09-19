package view

// Imports
import scalafx.scene.image.{Image, ImageView}
import model.Character

// Character View
class CharacterView(character: Character, leftImagePath: String, rightImagePath: String) extends ImageView {
  // Image used when the character faces left
  private val leftImage = new Image(leftImagePath)
  // Image used when the character faces right
  private val rightImage = new Image(rightImagePath)

  image = rightImage  // Defaults to the right-facing image

  x = character.x
  y = character.y
  fitWidth = character.width
  fitHeight = character.height

  // Update the character's position and image on the pane.
  def update(isMovingLeft: Boolean, isMovingRight: Boolean): Unit = {
    x = character.x
    y = character.y

    // Change the character's image based on movement direction
    if (isMovingLeft) image = leftImage
    else if (isMovingRight) image = rightImage
  }
}

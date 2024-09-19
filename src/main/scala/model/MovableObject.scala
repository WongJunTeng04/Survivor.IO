package model

// Abstract class for movable objects in the game to showcase more OOP concepts since they can be categorised
// under MovableObject

// Common Variables
abstract class MovableObject {
  var x: Double
  var y: Double
  var velocityX: Double
  var velocityY: Double

  // Common method, have to update it
  def updatePosition(): Unit = {
    x += velocityX
    y += velocityY
  }
}

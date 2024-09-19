package view

import scalafx.scene.shape.Rectangle
import model.Platform

// PlatformView with enhanced rectangle appearance
class PlatformView(platform: Platform) extends Rectangle {
  x = platform.x
  y = platform.y
  width = platform.width
  height = platform.height

  // Apply the CSS class defined in the external CSS file
  styleClass.add("rectangle-platform")
}

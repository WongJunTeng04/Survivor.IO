package view

// The view for the projectile.
import scalafx.scene.shape.Circle
import model.Projectile

// Setting up the projectile view
class ProjectileView(projectile: Projectile) extends Circle {
  centerX = projectile.x + projectile.radius
  centerY = projectile.y + projectile.radius
  radius = projectile.radius

  // Update the position of the projectile
  def update(): Unit = {
    centerX = projectile.x + projectile.radius
    centerY = projectile.y + projectile.radius
  }

  // Apply the CSS style class to the projectile
  styleClass.add("circle-projectile")
}

package model

import scalafx.scene.media.AudioClip

trait Sound {
  val audioClip: AudioClip

  // Method to play the sound
  def playSound(): Unit = {
    audioClip.play()
  }
}

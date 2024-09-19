// Import
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import controller.GameController
import model.{GameModel, Record}
import view.GameView
import scalafx.scene.layout.BorderPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.media.{Media, MediaPlayer}
import util.Database

import java.net.URL

// Object Main (Main entry point)
object Main extends JFXApp {

  // Database
  Database.setupDB() // Set up the Database
  Record.initializeTable() // Ensure the table is initialized

  // Variables for GameModel and GameController
  var playerName: String = "Anonymous" // Default name
  var model: GameModel = new GameModel(playerName)
  var view: GameView = new GameView()
  var controller: GameController = new GameController(model, view, playerName)

  // Link the game model's onGameOver method to the Main's showGameOver method
  model.onGameOver = showGameOver _

  // GAME: Background Music (BGM) & Sound Effects
  private val backgroundMusic: Media = new Media(getClass.getResource("sounds/ThemeSong.mp3").toString)
  val backgroundMusicPlayer: MediaPlayer = new MediaPlayer(backgroundMusic){
    autoPlay = true
    cycleCount = MediaPlayer.Indefinite
    volume = 0.5
  }

  // Load RootLayout.fxml
  val rootResource: URL = getClass.getResource("/view/RootLayout.fxml")
  val rootLoader: FXMLLoader = new FXMLLoader(rootResource, NoDependencyResolver)
  rootLoader.load()
  val rootLayout: jfxs.layout.BorderPane = rootLoader.getRoot[jfxs.layout.BorderPane]()

  // Primary Stage
  stage = new PrimaryStage {
    title = "Survivor.io"
    minWidth = 800
    minHeight = 700
    resizable = false
    scene = new Scene {
      root = new BorderPane(rootLayout)
      stylesheets = Seq(getClass.getResource("/view/Stylesheet.css").toString)
    }
  }

  // Show Home screen in the center of RootLayout
  def showHome(): Unit = {
    val resource = getClass.getResource("/view/Home.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val homePane = loader.getRoot[jfxs.layout.AnchorPane]
    rootLayout.setCenter(homePane)
  }

  // Transition to Game screen
  def showGame(): Unit = {
    // Create a new model and controller for each game session
    model = new GameModel(playerName)
    view = new GameView()
    controller = new GameController(model, view, playerName)
    // Link the game model's onGameOver method to the Main's showGameOver method
    model.onGameOver = showGameOver _

    rootLayout.setCenter(view.gamePane)
    controller.initialize()
  }

  // Show the Game Over screen
  def showGameOver(): Unit = {
    view.clearGamePane()  // Clear the game pane before showing the Game Over screen
    val resource = getClass.getResource("/view/GameOver.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val gameOverPane = loader.getRoot[jfxs.layout.AnchorPane]
    rootLayout.setCenter(gameOverPane)
  }

  // Show Leaderboard
  def showLeaderBoard(): Unit = {
    val resource = getClass.getResource("/view/Leaderboard.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val leaderBoardPane = loader.getRoot[jfxs.layout.AnchorPane]
    rootLayout.setCenter(leaderBoardPane)
  }

  // Show How-To-Play Screen
  def showHowToPlay(): Unit = {
    val resource = getClass.getResource("/view/HowToPlay.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val howToPlayPane = loader.getRoot[jfxs.layout.AnchorPane]
    rootLayout.setCenter(howToPlayPane)
  }
  // Show the Home screen on application start
  showHome()
}

// Background Music Code:
/***************************************************************************************
 *    Title: Play audio in Scala- A tutorial
 *    Author: ChatGPT
 *    Date: 22/8/2024
 *    Code version: ChatGPT
 *    Availability: -
 *
 ***************************************************************************************/

// The Actual Background Music:
/***************************************************************************************
 *    Title: Hollow Knight OST - Title Theme
 *    Author: n o o k
 *    Date: 22/8/2024
 *    Code version: -
 *    Availability: https://www.youtube.com/watch?v=N8Xr6x92MD0
 *
 ***************************************************************************************/

// Main:
/***************************************************************************************
 *    Title: AddressApp
 *    Author: Dr. Chin Teck Min
 *    Date: 22/8/2024
 *    Code version: ChatGPT
 *    Availability: -
 *
 ***************************************************************************************/
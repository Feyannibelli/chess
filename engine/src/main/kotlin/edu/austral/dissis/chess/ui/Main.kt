package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.engine.main.adapter.UnifiedGameEngine
import edu.austral.dissis.chess.engine.main.gameconfig.GameConstants
import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.createGameViewFrom
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    launch(GameSelectionApplication::class.java)
}

class GameSelectionApplication : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        // UI Layout Constants
        private const val BUTTON_WIDTH = 200.0
        private const val BUTTON_HEIGHT = 50.0
        private const val VBOX_SPACING = 20.0
        private const val VBOX_PADDING = 50.0
        private const val SCENE_WIDTH = 300.0
        private const val SCENE_HEIGHT = 270.0 // Increased for third button

        fun getGameTitle(gameType: GameConstants.GameType): String {
            return when (gameType) {
                GameConstants.GameType.CHESS -> "Chess"
                GameConstants.GameType.CHECKERS -> "Checkers"
                GameConstants.GameType.CAPABLANCA -> "Capablanca Chess"
            }
        }
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Select Game"

        // Create buttons for each game
        val chessButton =
            javafx.scene.control.Button("Chess").apply {
                setOnAction { startGame(GameConstants.GameType.CHESS, primaryStage) }
                prefWidth = BUTTON_WIDTH
                prefHeight = BUTTON_HEIGHT
            }

        val checkersButton =
            javafx.scene.control.Button("Checkers").apply {
                setOnAction { startGame(GameConstants.GameType.CHECKERS, primaryStage) }
                prefWidth = BUTTON_WIDTH
                prefHeight = BUTTON_HEIGHT
            }

        val capablancaButton =
            javafx.scene.control.Button("Capablanca Chess").apply {
                setOnAction { startGame(GameConstants.GameType.CAPABLANCA, primaryStage) }
                prefWidth = BUTTON_WIDTH
                prefHeight = BUTTON_HEIGHT
            }

        // Layout
        val vbox =
            javafx.scene.layout.VBox(VBOX_SPACING).apply {
                children.addAll(chessButton, checkersButton, capablancaButton)
                alignment = javafx.geometry.Pos.CENTER
                padding = javafx.geometry.Insets(VBOX_PADDING)
            }

        primaryStage.scene = Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT)
        primaryStage.show()
    }

    private fun startGame(
        gameType: GameConstants.GameType,
        primaryStage: Stage,
    ) {
        val gameEngine = UnifiedGameEngine(gameType)

        primaryStage.title = getGameTitle(gameType)
        val root = createGameViewFrom(gameEngine, imageResolver)
        primaryStage.scene = Scene(root)
    }
}

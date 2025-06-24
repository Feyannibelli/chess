package edu.austral.dissis.chess.engine.main.adapter;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;

public final class GameController {
  private GameState gameState;

  public GameController(GameState initialState) {
    this.gameState = initialState;
  }

  public void makeMove(Movement movement) {
    gameState = gameState.movePiece(movement);
  }

  public void undoMove() {
    gameState = gameState.undo();
  }

  public void redoMove() {
    gameState = gameState.redo();
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }
}

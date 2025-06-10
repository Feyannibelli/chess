package edu.austral.dissis.chess.engine.main.adapter;

import edu.austral.dissis.chess.engine.main.chess.factory.ChessGame;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import org.jetbrains.annotations.NotNull;

public class ChessGameEngine implements GameEngine {
  private final Adapter adapter;

  public ChessGameEngine(GameState gameState) {
    adapter = new Adapter(gameState);
  }

  public ChessGameEngine() {
    GameState gameState = ChessGame.createChessNormalGame();
    adapter = new Adapter(gameState);
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    return adapter.move(move);
  }

  @NotNull
  @Override
  public InitialState init() {
    return adapter.init();
  }

  @NotNull
  @Override
  public NewGameState undo() {
    return adapter.undo();
  }

  @NotNull
  @Override
  public NewGameState redo() {
    return adapter.redo();
  }
}

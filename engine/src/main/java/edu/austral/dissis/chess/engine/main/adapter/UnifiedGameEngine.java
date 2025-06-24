package edu.austral.dissis.chess.engine.main.adapter;

import edu.austral.dissis.chess.engine.main.checkers.factory.CheckersGame;
import edu.austral.dissis.chess.engine.main.chess.factory.ChessCapablancaGame;
import edu.austral.dissis.chess.engine.main.chess.factory.ChessGame;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.gameconfig.GameConstants;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import org.jetbrains.annotations.NotNull;

public class UnifiedGameEngine implements GameEngine {
  private final Adapter adapter;
  private final GameConstants.GameType gameType;

  public UnifiedGameEngine(GameConstants.GameType gameType) {
    this.gameType = gameType;
    GameState gameState = createGameState(gameType);
    this.adapter = new Adapter(gameState);
  }

  public UnifiedGameEngine(GameConstants.GameType gameType, GameState gameState) {
    this.gameType = gameType;
    this.adapter = new Adapter(gameState);
  }

  private GameState createGameState(GameConstants.GameType gameType) {
    return switch (gameType) {
      case CHESS -> ChessGame.createChessNormalGame();
      case CHECKERS -> CheckersGame.createCheckersNormalGame();
      case CAPABLANCA -> ChessCapablancaGame.createChessCapablancaGame();
    };
  }

  public GameConstants.GameType getGameType() {
    return gameType;
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

  public GameState getGameState() {
    return adapter.getGameState();
  }

  public void setGameState(GameState gameState) {
    adapter.setGameState(gameState);
  }
}

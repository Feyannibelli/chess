package edu.austral.dissis.chess.engine.main.common.validator.wincondition;

import edu.austral.dissis.chess.engine.main.common.game.GameState;

// define las condiciones de vixtoria
public interface WinCondition {

  boolean isWin(GameState gameState);

  boolean isDraw(GameState gameState);
}

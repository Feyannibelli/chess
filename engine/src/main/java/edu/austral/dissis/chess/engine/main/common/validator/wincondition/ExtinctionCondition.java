package edu.austral.dissis.chess.engine.main.common.validator.wincondition;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.game.GameState;

// valida que solo queda piezas de un color
public class ExtinctionCondition implements WinCondition {

  @Override
  public boolean isWin(GameState gameState) {
    Color color = gameState.getCurrentTurn();
    // si todas las piezas son de un color ganar
    return gameState.getCurrentBoard().getPieces().stream()
        .allMatch(piece -> piece.getColor() == color);
  }

  @Override
  public boolean isDraw(GameState gameState) {
    return false;
  }
}

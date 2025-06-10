package edu.austral.dissis.chess.engine.main.common.validator.board;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// verifica si la posicon esta dentro del tablero
public class LegalPositionValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    int height = gameState.getCurrentBoard().getHeight();
    int width = gameState.getCurrentBoard().getWidth();

    if (movement.to().column() < height
        && movement.to().row() < width
        && movement.to().column() >= 0
        && movement.to().row() >= 0) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("Estás fuera del tablero.");
    }
  }
}

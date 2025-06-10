package edu.austral.dissis.chess.engine.main.common.validator.board;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// validacion si el movimiento tiene la distancia exacta
public class ExactMovementValidator implements Validator {

  private final int distance;

  public ExactMovementValidator(int distance) {
    this.distance = distance;
  }

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    int horizontalDistance = Math.abs(movement.from().column() - movement.to().column());
    int verticalDistance = Math.abs(movement.from().row() - movement.to().row());

    if (horizontalDistance == distance || verticalDistance == distance) {
      return new ValidatorResponse.ValidatorResultValid("Valid");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("Invalid");
    }
  }
}

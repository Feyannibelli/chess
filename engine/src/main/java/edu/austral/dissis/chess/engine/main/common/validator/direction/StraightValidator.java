package edu.austral.dissis.chess.engine.main.common.validator.direction;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class StraightValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    if (movement.from().column() == movement.to().column()) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("No es un movimiento recto.");
    }
  }
}

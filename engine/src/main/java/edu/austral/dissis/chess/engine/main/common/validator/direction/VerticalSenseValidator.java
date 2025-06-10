package edu.austral.dissis.chess.engine.main.common.validator.direction;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class VerticalSenseValidator implements Validator {
  private final int sense;

  public VerticalSenseValidator(int sense) {
    this.sense = sense;
  }

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    boolean isValidMove;
    if (sense == 1) {
      isValidMove = movement.from().row() < movement.to().row();
    } else if (sense == -1) {
      isValidMove = movement.from().row() > movement.to().row();
    } else {
      isValidMove = false;
    }

    if (isValidMove) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("No es un sentido válido.");
    }
  }
}

package edu.austral.dissis.chess.engine.main.common.validator.composition;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.List;

public class OrValidator implements Validator {

  private final List<Validator> validators;

  public OrValidator(List<Validator> validators) {
    this.validators = validators;
  }

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    for (Validator validator : validators) {
      ValidatorResponse response = validator.validate(movement, gameState);
      if (response instanceof ValidatorResponse.ValidatorResultValid) {
        return new ValidatorResponse.ValidatorResultValid("OK");
      }
    }
    return new ValidatorResponse.ValidatorResultInvalid("Invalid move");
  }
}

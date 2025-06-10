package edu.austral.dissis.chess.engine.main.common.validator.board;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// especifica la cantidad mde numero de cuadrados queuna pieza puede moverse
public class LimitedMovementValidator implements Validator {

  private final int maxMoveQuantity;

  public LimitedMovementValidator(int maxMoveQuantity) {
    this.maxMoveQuantity = maxMoveQuantity;
  }

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    int horizontalDistance = Math.abs(movement.from().column() - movement.to().column());
    int verticalDistance = Math.abs(movement.from().row() - movement.to().row());

    boolean isHorizontalValid = horizontalDistance <= maxMoveQuantity;
    boolean isVerticalValid = verticalDistance <= maxMoveQuantity;

    if (isHorizontalValid && isVerticalValid) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("Movement exceeds limit");
    }
  }
}

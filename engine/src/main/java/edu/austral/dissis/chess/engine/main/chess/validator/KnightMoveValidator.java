package edu.austral.dissis.chess.engine.main.chess.validator;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class KnightMoveValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    int fromRow = movement.from().row();
    int fromColumn = movement.from().column();
    int toRow = movement.to().row();
    int toColumn = movement.to().column();

    if ((fromRow == toRow + 1 && fromColumn == toColumn + 2)
        || (fromRow == toRow + 2 && fromColumn == toColumn + 1)
        || (fromRow == toRow + 1 && fromColumn == toColumn - 2)
        || (fromRow == toRow + 2 && fromColumn == toColumn - 1)
        || (fromRow == toRow - 1 && fromColumn == toColumn + 2)
        || (fromRow == toRow - 2 && fromColumn == toColumn + 1)
        || (fromRow == toRow - 1 && fromColumn == toColumn - 2)
        || (fromRow == toRow - 2 && fromColumn == toColumn - 1)) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("No respeta el movimiento del caballo.");
    }
  }
}

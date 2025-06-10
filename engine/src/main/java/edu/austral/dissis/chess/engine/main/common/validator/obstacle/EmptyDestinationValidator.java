package edu.austral.dissis.chess.engine.main.common.validator.obstacle;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// valida que el destino este vacio si no lo esta tira error
public class EmptyDestinationValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    if (gameState.getCurrentBoard().getPieceByPosition(movement.to()) == null) {
      return new ValidatorResponse.ValidatorResultValid("OK");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid(
          "Hay una pieza en la posicion de destino.");
    }
  }
}

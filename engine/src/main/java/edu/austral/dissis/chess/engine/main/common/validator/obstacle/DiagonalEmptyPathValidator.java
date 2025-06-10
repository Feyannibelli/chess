package edu.austral.dissis.chess.engine.main.common.validator.obstacle;

import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// vetifica y valida el movimiento diagonal si el camino esta vacio
public class DiagonalEmptyPathValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    if (Math.abs(movement.from().row() - movement.to().row())
        != Math.abs(movement.from().column() - movement.to().column())) {
      return new ValidatorResponse.ValidatorResultInvalid("No es un movimiento diagonal");
    }

    Position currentCoordinate = movement.from();

    while (currentCoordinate.row() != movement.to().row()
        || currentCoordinate.column() != movement.to().column()) {
      if (currentCoordinate.row() < movement.to().row()
          && currentCoordinate.column() < movement.to().column()) {
        currentCoordinate =
            new Position(currentCoordinate.row() + 1, currentCoordinate.column() + 1);
      } else if (currentCoordinate.row() < movement.to().row()) {
        currentCoordinate =
            new Position(currentCoordinate.row() + 1, currentCoordinate.column() - 1);
      } else if (currentCoordinate.column() < movement.to().column()) {
        currentCoordinate =
            new Position(currentCoordinate.row() - 1, currentCoordinate.column() + 1);
      } else {
        currentCoordinate =
            new Position(currentCoordinate.row() - 1, currentCoordinate.column() - 1);
      }
      if (currentCoordinate.row() != movement.to().row()
          && currentCoordinate.column() != movement.to().column()
          && gameState.getCurrentBoard().getPieceByPosition(currentCoordinate) != null) {
        return new ValidatorResponse.ValidatorResultInvalid("Hay piezas en el camino");
      }
    }

    return new ValidatorResponse.ValidatorResultValid("OK");
  }
}

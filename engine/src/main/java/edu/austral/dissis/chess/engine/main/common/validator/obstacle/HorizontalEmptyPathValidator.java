package edu.austral.dissis.chess.engine.main.common.validator.obstacle;

import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// mismo caso que el diagonal pero horizontal
public class HorizontalEmptyPathValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    if (movement.from().row() != movement.to().row()) {
      return new ValidatorResponse.ValidatorResultInvalid("No es un movimiento horizontal.");
    }

    BoardImpl positions = (BoardImpl) gameState.getCurrentBoard();

    int fromX = movement.from().row();
    int fromY = movement.from().column();
    int toY = movement.to().column();

    return isEmptyPath(fromX, fromY, toY, positions);
  }

  private ValidatorResponse isEmptyPath(int fromX, int fromY, int toY, BoardImpl positions) {
    String typeMove = fromY < toY ? "right movement" : "left movement";

    if (typeMove.equals("right movement")) {
      return isEmptyPathRightMovement(fromX, fromY, toY, positions);
    } else {
      return isEmptyPathLeftMovement(fromX, fromY, toY, positions);
    }
  }

  private ValidatorResponse isEmptyPathRightMovement(
      int fromX, int fromY, int toY, BoardImpl positions) {
    for (int y = fromY + 1; y < toY; y++) {
      Position positionToCheck = new Position(fromX, y);
      if (positions.getPieceByPosition(positionToCheck) != null) {
        return new ValidatorResponse.ValidatorResultInvalid("Hay piezas en el camino");
      }
    }
    return new ValidatorResponse.ValidatorResultValid("OK");
  }

  private ValidatorResponse isEmptyPathLeftMovement(
      int fromX, int fromY, int toY, BoardImpl positions) {
    for (int y = fromY - 1; y > toY; y--) {
      Position positionToCheck = new Position(fromX, y);
      if (positions.getPieceByPosition(positionToCheck) != null) {
        return new ValidatorResponse.ValidatorResultInvalid("Hay piezas en el camino");
      }
    }
    return new ValidatorResponse.ValidatorResultValid("OK");
  }
}

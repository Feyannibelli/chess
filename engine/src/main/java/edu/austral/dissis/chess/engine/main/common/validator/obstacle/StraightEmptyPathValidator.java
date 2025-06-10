package edu.austral.dissis.chess.engine.main.common.validator.obstacle;

import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// mismo caso que el diagonal pero de forma recta
public class StraightEmptyPathValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    BoardImpl positions = (BoardImpl) gameState.getCurrentBoard();

    int fromX = movement.from().row();
    int toX = movement.to().row();
    int toY = movement.to().column();

    return isEmptyPath(fromX, toX, toY, positions);
  }

  private ValidatorResponse isEmptyPath(int fromX, int toX, int toY, BoardImpl positions) {
    String typeMove = fromX < toX ? "downward movement" : "upward movement";

    if (typeMove.equals("downward movement")) {
      return isEmptyPathDownwardMovement(fromX, toX, toY, positions);
    } else {
      return isEmptyPathUpwardMovement(fromX, toX, toY, positions);
    }
  }

  private ValidatorResponse isEmptyPathDownwardMovement(
      int fromX, int toX, int toY, BoardImpl positions) {
    for (int x = fromX + 1; x < toX; x++) {
      Position positionToCheck = new Position(x, toY);
      if (positions.getPieceByPosition(positionToCheck) != null) {
        return new ValidatorResponse.ValidatorResultInvalid("Hay piezas en el camino.");
      }
    }
    return new ValidatorResponse.ValidatorResultValid("OK");
  }

  private ValidatorResponse isEmptyPathUpwardMovement(
      int fromX, int toX, int toY, BoardImpl positions) {
    for (int x = fromX - 1; x > toX; x--) {
      Position positionToCheck = new Position(x, toY);
      if (positions.getPieceByPosition(positionToCheck) != null) {
        return new ValidatorResponse.ValidatorResultInvalid("Hay piezas en el camino.");
      }
    }
    return new ValidatorResponse.ValidatorResultValid("OK");
  }
}

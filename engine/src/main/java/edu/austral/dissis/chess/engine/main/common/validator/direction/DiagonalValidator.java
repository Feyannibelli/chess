package edu.austral.dissis.chess.engine.main.common.validator.direction;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class DiagonalValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    int fromX = movement.from().row();
    int fromY = movement.from().column();
    int toX = movement.to().row();
    int toY = movement.to().column();

    return isDiagonalMove(fromX, fromY, toX, toY);
  }

  private ValidatorResponse isDiagonalMove(int fromX, int fromY, int toX, int toY) {
    int deltaX = toX - fromX;
    int deltaY = toY - fromY;

    return Math.abs(deltaX) == Math.abs(deltaY)
        ? new ValidatorResponse.ValidatorResultValid("OK")
        : new ValidatorResponse.ValidatorResultInvalid("No es un movimiento diagonal.");
  }
}

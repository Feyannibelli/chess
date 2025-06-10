package edu.austral.dissis.chess.engine.main.checkers.factory;

import edu.austral.dissis.chess.engine.main.checkers.validator.EnemyInBetween;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.board.ExactMovementValidator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LimitedMovementValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.DiagonalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.VerticalSenseValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.EmptyDestinationValidator;
import java.util.List;

public class Moves {

  public static Validator simpleDiagonalMoveWithSense(int sense) {
    return new AndValidator(
        List.of(
            new DiagonalValidator(),
            new LimitedMovementValidator(1),
            new VerticalSenseValidator(sense),
            new EmptyDestinationValidator(),
            new LegalPositionValidator()));
  }

  public static Validator diagonalCaptureWithSense(int sense) {
    // captura en diagonal, tiene que caer atras de la que se come
    return new AndValidator(
        List.of(
            new VerticalSenseValidator(sense),
            new DiagonalValidator(),
            new ExactMovementValidator(2),
            new EnemyInBetween(),
            new EmptyDestinationValidator(),
            new LegalPositionValidator()));
  }

  public static Validator simpleDiagonalMove() {
    return new AndValidator(
        List.of(
            new DiagonalValidator(),
            new LimitedMovementValidator(1),
            new EmptyDestinationValidator()));
  }

  public static Validator diagonalCapture() {
    return new AndValidator(
        List.of(
            new DiagonalValidator(),
            new ExactMovementValidator(2),
            new EnemyInBetween(),
            new EmptyDestinationValidator()));
  }
}

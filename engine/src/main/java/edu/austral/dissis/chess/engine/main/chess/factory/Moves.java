package edu.austral.dissis.chess.engine.main.chess.factory;

import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.OrValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.DiagonalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.HorizontalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.StraightValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.DiagonalEmptyPathValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.EmptyDestinationValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.HorizontalEmptyPathValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.StraightEmptyPathValidator;
import edu.austral.dissis.chess.engine.main.common.validator.piece.IsEnemyValidator;
import java.util.Arrays;
import java.util.List;

public class Moves {

  public static Validator destinationPosition() {
    List<Validator> validators =
        Arrays.asList(new IsEnemyValidator(), new EmptyDestinationValidator());
    return new OrValidator(validators);
  }

  public static Validator diagonalMove() {
    List<Validator> validators =
        Arrays.asList(new DiagonalValidator(), new DiagonalEmptyPathValidator());
    return new AndValidator(validators);
  }

  public static Validator straightMove() {
    List<Validator> validators =
        Arrays.asList(new StraightValidator(), new StraightEmptyPathValidator());
    return new AndValidator(validators);
  }

  public static Validator horizontalMove() {
    List<Validator> validators =
        Arrays.asList(new HorizontalValidator(), new HorizontalEmptyPathValidator());
    return new AndValidator(validators);
  }
}

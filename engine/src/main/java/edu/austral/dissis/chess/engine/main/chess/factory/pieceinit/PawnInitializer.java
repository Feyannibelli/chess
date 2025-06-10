package edu.austral.dissis.chess.engine.main.chess.factory.pieceinit;

import edu.austral.dissis.chess.engine.main.chess.factory.Moves;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LimitedMovementValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.OrValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.DiagonalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.VerticalSenseValidator;
import edu.austral.dissis.chess.engine.main.common.validator.obstacle.EmptyDestinationValidator;
import edu.austral.dissis.chess.engine.main.common.validator.piece.IsEnemyValidator;
import edu.austral.dissis.chess.engine.main.common.validator.piece.IsFirstMoveValidator;
import java.util.List;
import java.util.UUID;

public class PawnInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    int sense = (color == Color.WHITE) ? 1 : -1;
    return new Piece(
        id,
        color,
        PieceType.PAWN,
        new AndValidator(
            List.of(
                new LegalPositionValidator(),
                new OrValidator(
                    List.of(
                        new AndValidator(
                            List.of(
                                Moves.straightMove(),
                                new LimitedMovementValidator(1),
                                new EmptyDestinationValidator(),
                                new VerticalSenseValidator(sense))),
                        new AndValidator(
                            List.of(
                                new IsFirstMoveValidator(),
                                Moves.straightMove(),
                                new LimitedMovementValidator(2),
                                new EmptyDestinationValidator(),
                                new VerticalSenseValidator(sense))),
                        new AndValidator(
                            List.of(
                                new IsEnemyValidator(),
                                new DiagonalValidator(),
                                new LimitedMovementValidator(1))))))));
  }
}

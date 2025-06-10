package edu.austral.dissis.chess.engine.main.chess.factory.pieceinit;

import edu.austral.dissis.chess.engine.main.chess.factory.Moves;
import edu.austral.dissis.chess.engine.main.chess.validator.move.LongCastleValidator;
import edu.austral.dissis.chess.engine.main.chess.validator.move.ShortCastleValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LimitedMovementValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.OrValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.DiagonalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.HorizontalValidator;
import edu.austral.dissis.chess.engine.main.common.validator.direction.StraightValidator;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class KingInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    List<Validator> validators =
        Arrays.asList(
            new LegalPositionValidator(),
            Moves.destinationPosition(),
            new OrValidator(
                Arrays.asList(
                    new AndValidator(
                        Arrays.asList(
                            new LimitedMovementValidator(1),
                            new OrValidator(
                                Arrays.asList(
                                    new StraightValidator(),
                                    new DiagonalValidator(),
                                    new HorizontalValidator())))),
                    new ShortCastleValidator(),
                    new LongCastleValidator())));

    return new Piece(id, color, PieceType.KING, new AndValidator(validators));
  }
}

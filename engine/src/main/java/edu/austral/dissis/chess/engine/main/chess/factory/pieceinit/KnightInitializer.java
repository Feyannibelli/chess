package edu.austral.dissis.chess.engine.main.chess.factory.pieceinit;

import edu.austral.dissis.chess.engine.main.chess.factory.Moves;
import edu.austral.dissis.chess.engine.main.chess.validator.KnightMoveValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import java.util.List;
import java.util.UUID;

public class KnightInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    return new Piece(
        id,
        color,
        PieceType.KNIGHT,
        new AndValidator(
            List.of(
                new KnightMoveValidator(),
                new LegalPositionValidator(),
                Moves.destinationPosition())));
  }
}

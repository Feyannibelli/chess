package edu.austral.dissis.chess.engine.main.checkers.factory.pieceinit;

import edu.austral.dissis.chess.engine.main.checkers.factory.Moves;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.OrValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    int sense = color == Color.WHITE ? 1 : -1;

    List<Validator> validators = new ArrayList<>();
    validators.add(Moves.simpleDiagonalMoveWithSense(sense));
    validators.add(Moves.diagonalCaptureWithSense(sense));

    return new Piece(id, color, PieceType.PAWN, new OrValidator(validators));
  }
}

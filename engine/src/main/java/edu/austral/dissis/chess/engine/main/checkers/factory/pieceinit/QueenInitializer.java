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

public class QueenInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = java.util.UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    List<Validator> validators = new ArrayList<>();
    validators.add(Moves.simpleDiagonalMove());
    validators.add(Moves.diagonalCapture());

    // movimiento simple en diagonal sin sentido determinado
    return new Piece(id, color, PieceType.QUEEN, new OrValidator(validators));
  }
}

package edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.variante;

import edu.austral.dissis.chess.engine.main.chess.factory.Moves;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArchbishopInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    List<Validator> validators = new ArrayList<>();
    validators.add(new LegalPositionValidator());
    validators.add(Moves.diagonalMove());
    validators.add(Moves.destinationPosition());

    Validator finalValidator = new AndValidator(validators);

    return new Piece(id, color, PieceType.ARCHBISHOP, finalValidator);
  }
}

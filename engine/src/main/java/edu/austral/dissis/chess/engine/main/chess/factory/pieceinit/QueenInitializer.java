package edu.austral.dissis.chess.engine.main.chess.factory.pieceinit;

import edu.austral.dissis.chess.engine.main.chess.factory.Moves;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.board.LegalPositionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.AndValidator;
import edu.austral.dissis.chess.engine.main.common.validator.composition.OrValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QueenInitializer implements PieceInitializer {

  @Override
  public Piece initialize(Color color) {
    String uuid = UUID.randomUUID().toString();
    return initialize(color, uuid);
  }

  @Override
  public Piece initialize(Color color, String id) {
    List<Validator> combinedValidators = new ArrayList<>();

    combinedValidators.add(new LegalPositionValidator());
    combinedValidators.add(Moves.destinationPosition());
    combinedValidators.add(
        new OrValidator(
            List.of(Moves.horizontalMove(), Moves.straightMove(), Moves.diagonalMove())));

    return new Piece(id, color, PieceType.QUEEN, new AndValidator(combinedValidators));
  }
}

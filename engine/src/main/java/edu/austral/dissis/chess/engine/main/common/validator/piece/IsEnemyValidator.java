package edu.austral.dissis.chess.engine.main.common.validator.piece;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class IsEnemyValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    Board board = gameState.getCurrentBoard();
    Piece piece = board.getPieceByPosition(movement.to());
    Piece pieceFrom = board.getPieceByPosition(movement.from());

    if (pieceFrom == null) {
      return new ValidatorResponse.ValidatorResultInvalid("No hay pieza.");
    }

    if (piece == null) {
      return new ValidatorResponse.ValidatorResultInvalid("No hay pieza.");
    } else {
      if (piece.getColor() != pieceFrom.getColor()) {
        return new ValidatorResponse.ValidatorResultValid("Es enemigo.");
      } else {
        return new ValidatorResponse.ValidatorResultInvalid(
            "Pieza aliada en la posición de destino.");
      }
    }
  }
}

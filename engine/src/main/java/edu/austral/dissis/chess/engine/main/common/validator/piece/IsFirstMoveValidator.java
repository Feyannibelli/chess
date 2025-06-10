package edu.austral.dissis.chess.engine.main.common.validator.piece;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// validacionque la pieza esta  realizando su movimiento
public class IsFirstMoveValidator implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    Board board = gameState.getCurrentBoard();
    Piece fromPiece = board.getPieceByPosition(movement.from());

    if (fromPiece == null) {
      return new ValidatorResponse.ValidatorResultInvalid(
          "No hay una pieza en esta posición para mover.");
    }

    if (fromPiece.getMoveCounter() == 0) {
      return new ValidatorResponse.ValidatorResultValid("Es el primer movimiento.");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("No es el primer movimiento.");
    }
  }
}

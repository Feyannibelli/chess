package edu.austral.dissis.chess.engine.main.chess.validator.move;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class ShortCastleValidator implements Validator {
  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    Board board = gameState.getCurrentBoard();

    if (board.getPieceByPosition(movement.from()).getType() != PieceType.KING) {
      return new ValidatorResponse.ValidatorResultInvalid(
          "La pieza que estás moviendo no es un rey");
    }

    if (board.getPieceByPosition(movement.from()).hasMoved()) {
      return new ValidatorResponse.ValidatorResultInvalid("El rey se ha movido");
    }

    if (movement.to().row() != movement.from().row()) {
      return new ValidatorResponse.ValidatorResultInvalid(
          "El rey solo se puede mover en horizontal (Para el enroque)");
    }

    if (movement.to().column() != movement.from().column() + 2) {
      return new ValidatorResponse.ValidatorResultInvalid(
          "El rey solo se puede mover dos casillas a la derecha (Para el enroque)");
    }

    if (!noPiecesBetweenCastling(board, movement)) {
      return new ValidatorResponse.ValidatorResultInvalid("Hay piezas entre el rey y la torre!");
    }

    return new ValidatorResponse.ValidatorResultValid("Castling!");
  }

  private boolean noPiecesBetweenCastling(Board board, Movement movement) {
    for (int i = movement.from().column() + 1; i < movement.to().column(); i++) {
      Position auxPos = new Position(movement.from().row(), i);
      if (board.getPieceByPosition(auxPos) != null) {
        return false;
      }
    }
    return true;
  }
}

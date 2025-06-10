package edu.austral.dissis.chess.engine.main.chess.validator.postcondition;

import edu.austral.dissis.chess.engine.main.chess.validator.move.LongCastleValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionResult;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultInvalid;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultValid;

public class CastleLeftPostCondition implements PostConditionValidator {

  private final Validator longCastleValidator = new LongCastleValidator();

  @Override
  public PostConditionResult validate(GameState gameState, Board updatedBoard) {
    Movement movement = findMovement(gameState, updatedBoard);
    if (longCastleValidator.validate(movement, gameState)
        instanceof ValidatorResponse.ValidatorResultInvalid) {
      return new ResultInvalid("No se puede realizar el enroque.");
    }
    Board newBoard = updateRookPosition(updatedBoard, movement);
    return new ResultValid(newBoard);
  }

  private Board updateRookPosition(Board board, Movement movement) {
    Position rookPosition = new Position(movement.from().row(), movement.from().column() - 4);
    Position newRookPosition = new Position(movement.from().row(), movement.from().column() - 1);
    Piece rook = board.getPieceByPosition(rookPosition);
    Board newBoard = board.updatePieceByPosition(newRookPosition, rook);
    Board finalBoard = newBoard.removePieceByPosition(rookPosition);
    return finalBoard;
  }

  private Movement findMovement(GameState gameState, Board updatedBoard) {
    Board initialBoard = gameState.getCurrentBoard();
    Position initialKingPosition = findKingPosition(initialBoard, gameState.getCurrentTurn());
    Position updatedKingPosition = findKingPosition(updatedBoard, gameState.getCurrentTurn());
    return new Movement(initialKingPosition, updatedKingPosition);
  }

  private Position findKingPosition(Board board, Color color) {
    Piece king = null;
    for (Piece piece : board.getPieces()) {
      if (piece.getColor() == color && piece.getType() == PieceType.KING) {
        king = piece;
        break;
      }
    }
    assert king != null;
    return board.getPositionByPiece(king);
  }
}

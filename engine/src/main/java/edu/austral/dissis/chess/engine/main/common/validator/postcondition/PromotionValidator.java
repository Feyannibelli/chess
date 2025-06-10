package edu.austral.dissis.chess.engine.main.common.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceInitializer;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;

public class PromotionValidator implements PostConditionValidator {

  private final PieceInitializer initializer;

  public PromotionValidator(PieceInitializer initializer) {
    this.initializer = initializer;
  }

  // valida la promosion
  @Override
  public PostConditionResult validate(GameState gameState, Board board) {
    Board newBoard = board;
    boolean pawnPromoted = false;

    for (int i = 0; i < 8; i++) {
      // chequea por peones blancos
      Piece pieceWhite = getPiece(new Position(7, i), newBoard);
      if (pieceWhite != null && isPawn(pieceWhite) && isColor(pieceWhite, Color.WHITE)) {
        Piece newPiece = initializer.initialize(Color.WHITE, pieceWhite.getId());
        Piece valPiece = newBoard.getPieceByPosition(new Position(7, i));
        newBoard =
            newBoard.updatePieceByPosition(
                new Position(7, i),
                valPiece.withType(newPiece.getType()).withValidator(newPiece.getValidator()));
        pawnPromoted = true;
      }

      // chequea por peones negros
      Piece pieceBlack = getPiece(new Position(0, i), newBoard);
      if (pieceBlack != null && isPawn(pieceBlack) && isColor(pieceBlack, Color.BLACK)) {
        Piece newPiece = initializer.initialize(Color.BLACK, pieceBlack.getId());
        Piece valPiece = newBoard.getPieceByPosition(new Position(0, i));
        newBoard =
            newBoard.updatePieceByPosition(
                new Position(0, i),
                valPiece.withType(newPiece.getType()).withValidator(newPiece.getValidator()));
        pawnPromoted = true;
      }
    }

    if (pawnPromoted) {
      return new ResultValid(newBoard);
    } else {
      return new ResultInvalid("No pawns were promoted.");
    }
  }

  private Piece getPiece(Position position, Board board) {
    return board.getPieceByPosition(position);
  }

  // chequea si la peiza es un peon
  private boolean isPawn(Piece piece) {
    return piece.getType() == PieceType.PAWN;
  }

  // verifica si la pieza es de un color especifico
  private boolean isColor(Piece piece, Color color) {
    return piece.getColor() == color;
  }
}

package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;

public class PawnMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color pieceColor) {
        int direction = pieceColor == Color.WHITE ? 1 : -1;
        int rowDiff = to.row() - from.row();
        int colDiff = Math.abs(to.column() - from.column());

        if (colDiff == 0) {
            return canMoveForward(from, to, board, direction, rowDiff);
        }

        if (colDiff == 1 && rowDiff == direction) {
            return canCaptureDiagonally(to, board, pieceColor);
        }

        return false;
    }

    private boolean canMoveForward(Position from, Position to, Board board, int direction, int rowDiff) {
        if (rowDiff == direction) {
            return board.isEmpty(to);
        }

        if (rowDiff == 2 * direction && isInitialPosition(from, direction > 0)) {
            return board.isEmpty(to) && board.isEmpty(new Position(from.row() + direction, from.column()));
        }

        return false;
    }

    private boolean canCaptureDiagonally(Position to, Board board, Color pieceColor) {
        return board.getPieceAt(to)
                .map(piece -> piece.color() != pieceColor)
                .orElse(false);
    }

    private boolean isInitialPosition(Position position, boolean isWhite) {
        return isWhite ? position.row() == 1 : position.row() == 6;
    }
}
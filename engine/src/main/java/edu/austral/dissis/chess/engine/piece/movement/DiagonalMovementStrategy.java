package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;

public final class DiagonalMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        //verificar que el movimiento sea diagonal
        if (!isDiagonalMove(from, to)) {
            return false;
        }

        //verificar que el camino este libre
        return isPathClear(from, to, board);
    }

    private boolean isDiagonalMove(Position from, Position to) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());
        return rowDiff == colDiff && rowDiff > 0;
    }

    private boolean isPathClear(Position from, Position to, Board board) {
        int rowDirection = Integer.compare(to.getRow(), from.getRow());
        int colDirection = Integer.compare(to.getColumn(), from.getColumn());

        int currentRow = from.getRow() + rowDirection;
        int currentCol = from.getColumn() + colDirection;

        while (currentRow != to.getRow() || currentCol != to.getColumn()) {
            if (board.hasPieceAt(new Position(currentRow, currentCol))) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }
}

package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;

public class LinearMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color pieceColor) {
        if (!isLinearMove(from, to)) {
            return false;
        }

        if (!isPathClear(from, to, board)) {
            return false;
        }

        return !isOccupiedBySameColor(to, board, pieceColor);
    }

    private boolean isLinearMove(Position from, Position to) {
        return from.row() == to.row() || from.column() == to.column();
    }

    private boolean isPathClear(Position from, Position to, Board board) {
        int rowDirection = Integer.compare(to.row(), from.row());
        int colDirection = Integer.compare(to.column(), from.column());

        int currentRow = from.row() + rowDirection;
        int currentCol = from.column() + colDirection;

        while (currentRow != to.row() || currentCol != to.column()) {
            if (board.isOccupied(new Position(currentRow, currentCol))) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }

    private boolean isOccupiedBySameColor(Position position, Board board, Color pieceColor) {
        return board.getPieceAt(position)
                .map(piece -> piece.color() == pieceColor)
                .orElse(false);
    }
}
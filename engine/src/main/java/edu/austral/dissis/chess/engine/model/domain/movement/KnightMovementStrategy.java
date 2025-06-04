package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;

public class KnightMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color pieceColor) {
        if (!isKnightMove(from, to)) {
            return false;
        }

        return !isOccupiedBySameColor(to, board, pieceColor);
    }

    private boolean isKnightMove(Position from, Position to) {
        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    private boolean isOccupiedBySameColor(Position position, Board board, Color pieceColor) {
        return board.getPieceAt(position)
                .map(piece -> piece.color() == pieceColor)
                .orElse(false);
    }
}
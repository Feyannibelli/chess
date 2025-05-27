package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;

public final class KnightMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        return isKnightMove(from, to);
    }

    private boolean isKnightMove(Position from, Position to) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}
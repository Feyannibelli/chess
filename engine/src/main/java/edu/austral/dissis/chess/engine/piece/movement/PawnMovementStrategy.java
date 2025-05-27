package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;


public final class PawnMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        int direction = color == Color.WHITE ? 1 : -1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        //movimiento hacia adelante
        if (colDiff == 0) {
            return canMoveForward(from, to, board, color, direction, rowDiff);
        }

        //captura diagonal
        if (colDiff == 1 && rowDiff == direction) {
            return board.hasPieceAt(to);
        }

        return false;
    }

    private boolean canMoveForward(Position from, Position to, Board board, Color color,
                                   int direction, int rowDiff) {
        //no puede capturar hacia adelante
        if (board.hasPieceAt(to)) {
            return false;
        }

        //movimiento de una casilla
        if (rowDiff == direction) {
            return true;
        }

        //movimiento de dos casillas desde posicion inicial
        if (rowDiff == 2 * direction && isInStartingPosition(from, color)) {
            return true;
        }

        return false;
    }

    private boolean isInStartingPosition(Position position, Color color) {
        int startingRow = color == Color.WHITE ? 1 : 6;
        return position.getRow() == startingRow;
    }
}
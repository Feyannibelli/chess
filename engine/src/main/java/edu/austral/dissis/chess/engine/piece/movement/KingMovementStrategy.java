package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;

public final class KingMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        return isKingMove(from, to) || isCastlingMove(from, to, board, color);
    }

    private boolean isKingMove(Position from, Position to) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        return rowDiff <= 1 && colDiff <= 1 && (rowDiff > 0 || colDiff > 0);
    }

    private boolean isCastlingMove(Position from, Position to, Board board, Color color) {
        //verificar si es un movimiento de enroque
        if (Math.abs(to.getColumn() - from.getColumn()) != 2 || to.getRow() != from.getRow()) {
            return false;
        }

        //verificar que el rey este en su posicion inicial
        int expectedRow = color == Color.WHITE ? 0 : 7;
        if (from.getRow() != expectedRow || from.getColumn() != 4) {
            return false;
        }

        //determinar si es enroque corto o largo
        boolean isKingSide = to.getColumn() > from.getColumn();
        int rookColumn = isKingSide ? 7 : 0;
        Position rookPosition = new Position(expectedRow, rookColumn);

        //verificar que la torre este en su posición
        if (!board.hasPieceAt(rookPosition)) {
            return false;
        }

        //verificar que el camino este libre
        return isPathClearForCastling(from, to, board);
    }

    private boolean isPathClearForCastling(Position kingFrom, Position kingTo, Board board) {
        int startCol = Math.min(kingFrom.getColumn(), kingTo.getColumn());
        int endCol = Math.max(kingFrom.getColumn(), kingTo.getColumn());

        for (int col = startCol + 1; col < endCol; col++) {
            if (board.hasPieceAt(new Position(kingFrom.getRow(), col))) {
                return false;
            }
        }

        return true;
    }
}
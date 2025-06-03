package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;

//movimiento en L
public final class KnightMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        if (from.equals(to)) {
            return false;
        }

        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        //movimiento en L
        boolean isLMove = (rowDiff == 2 && colDiff == 1) ||
                (rowDiff == 1 && colDiff == 2);

        if (!isLMove) {
            return false;
        }

        //destino debe estar vacio o con pieza enemiga
        return board.getPiece(to)
                .map(piece -> !piece.color().equals(color))
                .orElse(true);
    }
}
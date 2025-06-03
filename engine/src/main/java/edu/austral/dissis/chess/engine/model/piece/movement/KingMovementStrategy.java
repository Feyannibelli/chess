package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;

//movimiento del rey
public final class KingMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        if (from.equals(to)) {
            return false;
        }

        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        //Se mueve en un cuadrado a cualquier direccion
        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }

        //el destino debe estar vacio o pieza enemiga
        return board.getPiece(to)
                .map(piece -> !piece.color().equals(color))
                .orElse(true);
    }
}
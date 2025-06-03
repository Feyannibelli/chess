package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;

//movimiento en diagonal
public final class DiagonalMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        if (from.equals(to)) {
            return false;
        }

        //debe ser movimiento diagonal
        if (!from.isSameDiagonal(to)) {
            return false;
        }

        //el camino debe estar claro
        if (!board.isPathClear(from, to)) {
            return false;
        }

        //el destino debe estar vacio o tener pieza enemiga
        return board.getPiece(to)
                .map(piece -> !piece.color().equals(color))
                .orElse(true);
    }
}
package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;

//movimiento lineal horizontal o vertical
public final class LinearMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        if (from.equals(to)) {
            return false;
        }

        //debe ser misma fila y columna
        if (!from.isSameRow(to) && !from.isSameColumn(to)) {
            return false;
        }

        //el camino debe estar despejado
        if (!board.isPathClear(from, to)) {
            return false;
        }

        //el destino debe estar vacio o con pieza enemiga
        return board.getPiece(to)
                .map(piece -> !piece.color().equals(color))
                .orElse(true);
    }
}

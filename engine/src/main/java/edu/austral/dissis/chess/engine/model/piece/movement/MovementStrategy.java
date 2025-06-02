package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.Set;

//para definir disrintos tipos de movimientos de piezas
public interface MovementStrategy {

    //posiciones validas a las que x pieza puede moverse
    Set<Position> getPosibleMoves(Position from, Board board, Piece piece);

    boolean isValidMove(Position from, Position to, Board board, Piece piece);

    //obtiene el nombre del movimiento
    default String getStrategyName() {
        return this.getClass().getSimpleName();
    }
}

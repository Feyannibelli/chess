package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;

//define la estrategia de movimientos de una pieza
@FunctionalInterface
public interface MovementStrategy {

    //verifica si una pieza puede moverse desde una posicion
    boolean canMoveTo(Position from, Position to, Board board, Color color);

    //combina esta estrategua con otra
    default MovementStrategy or(MovementStrategy other) {
        return (from, to, board, color) ->
                this.canMoveTo(from, to, board, color) ||
                        other.canMoveTo(from, to, board, color);
    }

    default MovementStrategy and(MovementStrategy other) {
        return (from, to, board, color) ->
                this.canMoveTo(from, to, board, color) &&
                        other.canMoveTo(from, to, board, color);
    }
    
    default MovementStrategy negate() {
        return (from, to, board, color) ->
                !this.canMoveTo(from, to, board, color);
    }
}
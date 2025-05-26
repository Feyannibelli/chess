package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;


// estratefia que determina como las piezas se mueven
public interface MovementStrategy {

    //determina si una pieza puede moverse de su posicion actual a la posicion desea dada en el tablero
    boolean canMoveTo(Position from, Position to, Board board, Color color);
}
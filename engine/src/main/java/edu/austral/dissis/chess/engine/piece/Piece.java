package edu.austral.dissis.chess.engine.piece;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.movement.MovementStrategy;

//representacion de las piezas
public final class Piece {
    private final PieceType type;
    private final Color color;
    private final MovementStrategy movementStrategy;

    //crea una nueva piexa cpm tipo, color y movimiento
    public Piece(PieceType type, Color color, MovementStrategy movementStrategy) {
        this.type = type;
        this.color = color;
        this.movementStrategy = movementStrategy;
    }

    // consigue el tipo de pieza
    public PieceType getType() {
        return type;
    }

    //consigue el color de la pieza
    public Color getColor() {
        return color;
    }

    //determina si la pieza puede moverse de su posicion actual a la posicion deseada
    public boolean canMoveTo(Position from, Position to, Board board) {
        return movementStrategy.canMoveTo(from, to, board, color);
    }
}
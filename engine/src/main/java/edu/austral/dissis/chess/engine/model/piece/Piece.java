package edu.austral.dissis.chess.engine.model.piece;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.movement.MovementStrategy;

//repersenta una pieza
public record Piece(PieceType type, Color color, MovementStrategy movement) {

    //valida que los parametros no sean nulos
    public Piece {
        if (type == null) {
            throw new IllegalArgumentException("Piece type cannot be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("Piece color cannot be null");
        }
        if (movement == null) {
            throw new IllegalArgumentException("Movement strategy cannot be null");
        }
    }

    //verifica si esta pieza puede moverse
    public boolean canMoveTo(Position from, Position to, Board board) {
        return movement.canMoveTo(from, to, board, color);
    }

    //verifica si esta pieza es de color B
    public boolean isWhite() {
        return color.isWhite();
    }

    //verifica si esta pieza es de color N
    public boolean isBlack() {
        return color.isBlack();
    }

    //verifica si esta peiza es del mismo color que la otra
    public boolean isSameColor(Piece other) {
        return color == other.color;
    }

    //verifica si esta pieza es del color opuesto a otra
    public boolean isOppositeColor(Piece other) {
        return color != other.color;
    }

    public boolean isPawn() {
        return type.isPawn();
    }

    public boolean isKing() {
        return type.isKing();
    }

    public int getValue() {
        return type.getValue();
    }

    public String getSymbol() {
        return type.getSymbol();
    }

    @Override
    public String toString() {
        return color.name().toLowerCase() + " " + type.getName().toLowerCase();
    }

    public String getShortRepresentation() {
        String colorPrefix = color.isWhite() ? "W" : "b";
        return colorPrefix + type.getSymbol();
    }
}
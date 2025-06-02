package edu.austral.dissis.chess.engine.model.piece;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.game.Move;
import edu.austral.dissis.chess.engine.model.piece.movement.MovementStrategy;
import javafx.beans.binding.ObjectExpression;
import javafx.geometry.Pos;

import java.util.Objects;
import java.util.Set;

//representacion de las piezas
public record Piece(PieceType type, Color color, MovementStrategy movementStrategy, boolean hasMoved) {

    //validador de parametros requeridos
    public Piece {
        Objects.requireNonNull(type, "Piece type cannot be null");
        Objects.requireNonNull(color, "piece color cannot be null");
        Objects.requireNonNull(movementStrategy, "movement strategy cannot be null");
    }

    public Piece(PieceType type, Color color, MovementStrategy movementStrategy) {
        this(type, color, movementStrategy, false);
    }

    //verifica si la pieza puede moverse en x posicion
    public boolean canMoveTo(Position from, Position to, Board board) {
        return movementStrategy.isValidMove(from, to, board, this);
    }

    //obtiene posiciones validas para x pieza
    public Set<Position> getPosibleMoves(Position from, Board board) {
        return movementStrategy.getPosibleMoves(from, board, this);
    }

    //marca pieza como movida
    public Piece withMoved() {
        return new Piece(type, color, movementStrategy, true);
    }

    public boolean isWhite() {
        return color.isWhite();
    }

    public boolean isBlack() {
        return color.isBlack();
    }

    public boolean isOpponentOf(Piece other) {
        return !this.color.equals(other.color);
    }

    @Override
    public String toString() {
        return color.getSymbol() + type.getSymbol();
    }
}

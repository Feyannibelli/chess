package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.board.Position;

//repersenta movimiento basicos de una posicion a otra
public record Move(Position from, Position to, Player player) {

    public Move {
        if (from == null) {
            throw new IllegalArgumentException("From position cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("To position cannot be null");
        }
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and to positions cannot be the same");
        }
    }

    //crea un movimiento para un jugador
    public static Move of(Position from, Position to, Player player) {
        return new Move(from, to, player);
    }

    public int getDistance() {
        return from.manhattanDistance(to);
    }

    public boolean isHorizontal() {
        return from.isSameRow(to);
    }

    public boolean isVertical() {
        return from.isSameColumn(to);
    }

    public boolean isDiagonal() {
        return from.isSameDiagonal(to);
    }

    @Override
    public String toString() {
        return from.toString() + "-" + to.toString();
    }
}
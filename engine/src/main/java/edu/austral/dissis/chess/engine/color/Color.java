package edu.austral.dissis.chess.engine.color;

// representa el color de la pieza
public enum Color {
    WHITE,
    BLACK;

    @Override
    public String toString() {
        return this == WHITE ? "White" : "Black";
    }
}
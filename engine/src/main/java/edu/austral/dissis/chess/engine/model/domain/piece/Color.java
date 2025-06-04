package edu.austral.dissis.chess.engine.model.domain.piece;

public enum Color {
    WHITE, BLACK;

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
package edu.austral.dissis.chess.engine.model.piece;

//colores posibles de las piezas
public enum Color {
    WHITE, BLACK;

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public int getPawnDirection() {
        return this == WHITE ? -1 : 1;
    }

    public int getPawnStartRow() {
        return this == WHITE ? 6 : 1;
    }

    public int getPromotionRow() {
        return this == WHITE ? 0 : 7;
    }

    public int getBackRank() {
        return this == WHITE ? 7 : 0;
    }
}
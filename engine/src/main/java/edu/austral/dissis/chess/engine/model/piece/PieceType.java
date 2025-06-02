package edu.austral.dissis.chess.engine.model.piece;

public enum PieceType {
    KING("K", 0, "King"),
    QUEEN("Q", 9, "Queen"),
    ROOK("R", 5, "Rook"),
    BISHOP("B", 3, "Bishop"),
    KNIGHT("N", 3, "Knight"),
    PAWN("P", 1, "Pawn");

    private final String symbol;
    private final int value;
    private final String displayName;

    PieceType(String symbol, int value, String displayName) {
        this.symbol = symbol;
        this.value = value;
        this.displayName = displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean canJump() {
        return this == KNIGHT;
    }

    public boolean isPawn() {
        return this == PAWN;
    }


    public boolean isKing() {
        return this == KING;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

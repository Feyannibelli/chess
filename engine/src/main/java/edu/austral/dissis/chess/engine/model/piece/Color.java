package edu.austral.dissis.chess.engine.model.piece;

// representa loc colores de las piezas
public enum Color {
    WHITE("White", "w"),
    BLACK("Black", "b");

    private final String displayName;
    private final String symbol;

    Color(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

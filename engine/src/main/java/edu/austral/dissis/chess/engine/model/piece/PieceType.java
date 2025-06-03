package edu.austral.dissis.chess.engine.model.piece;

//representa los diferentes tipos de piezas
public enum PieceType {
    //piezas estandar
    KING("King", "K", 0),
    QUEEN("Queen", "Q", 9),
    ROOK("Rook", "R", 5),
    BISHOP("Bishop", "B", 3),
    KNIGHT("Knight", "N", 3),
    PAWN("Pawn", "P", 1),

    //piezas de variantes
    ARCHBISHOP("Archbishop", "A", 7), // Alfil + Caballo
    CHANCELLOR("Chancellor", "C", 8); // Torre + Caballo

    private final String name;
    private final String symbol;
    private final int value;

    //tipos de piezas
    PieceType(String name, String symbol, int value) {
        this.name = name;
        this.symbol = symbol;
        this.value = value;
    }

    //obtiene el nombre completo de la pieza
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public boolean isPawn() {
        return this == PAWN;
    }

    public boolean isKing() {
        return this == KING;
    }

    public boolean canCastle() {
        return this == KING || this == ROOK;
    }

    public boolean isVariantPiece() {
        return this == ARCHBISHOP || this == CHANCELLOR;
    }

    public static PieceType[] getPromotionPieces() {
        return new PieceType[]{QUEEN, ROOK, BISHOP, KNIGHT};
    }

    public static PieceType[] getCapablancaPromotionPieces() {
        return new PieceType[]{QUEEN, ROOK, BISHOP, KNIGHT, ARCHBISHOP, CHANCELLOR};
    }
}
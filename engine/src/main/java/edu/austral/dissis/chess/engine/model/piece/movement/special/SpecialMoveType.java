package edu.austral.dissis.chess.engine.model.piece.movement.special;

//define los tipos de movimientos especiales
public enum SpecialMoveType {
    CASTLING_KINGSIDE("Kingside Castling", "O-O", "King moves two squares toward kingside rook"),
    CASTLING_QUEENSIDE("Queenside Castling", "O-O-O", "King moves two squares toward queenside rook"),
    EN_PASSANT("En Passant", "e.p.", "Pawn captures enemy pawn that moved two squares forward"),
    PAWN_PROMOTION("Pawn Promotion", "=", "Pawn reaches opposite end and transforms to another piece"),
    PAWN_DOUBLE_MOVE("Pawn Double Move", "", "Pawn moves two squares on first move");

    private final String displayName;
    private final String notation;
    private final String description;

    SpecialMoveType(String displayName, String notation, String description) {
        this.displayName = displayName;
        this.notation = notation;
        this.description = description;
    }

    //obtiene nombre
    public String getDisplayName() {
        return displayName;
    }

    public String getNotation() {
        return notation;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCastling() {
        return this == CASTLING_KINGSIDE || this == CASTLING_QUEENSIDE;
    }

    public boolean isKingsideCastling() {
        return this == CASTLING_KINGSIDE;
    }

    public boolean isQueensideCastling() {
        return this == CASTLING_QUEENSIDE;
    }

    public boolean isEnPassant() {
        return this == EN_PASSANT;
    }

    public boolean isPawnPromotion() {
        return this == PAWN_PROMOTION;
    }

    public boolean isPawnDoubleMove() {
        return this == PAWN_DOUBLE_MOVE;
    }
    public boolean involvesPawn() {
        return this == EN_PASSANT || this == PAWN_PROMOTION || this == PAWN_DOUBLE_MOVE;
    }

    public boolean involvesKing() {
        return isCastling();
    }

    public boolean requiresSpecialConditions() {
        return isCastling() || this == EN_PASSANT;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

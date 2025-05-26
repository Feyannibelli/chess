package edu.austral.dissis.chess.engine.piece;

public enum PieceType {
    KING,
    QUEEN,
    ROOK,
    BISHOP,
    KNIGHT,
    PAWN;

    @Override
    public String toString() {
        return switch (this) {
            case KING -> "King";
            case QUEEN -> "Queen";
            case ROOK -> "Rook";
            case BISHOP -> "Bishop";
            case KNIGHT -> "Knight";
            case PAWN -> "Pawn";
        };
    }
}
package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.board.Position;

//repesenta movimiento especiales
public record SpecialMove(Position from, Position to, Player player, String description) {

    public SpecialMove {
        if (from == null) {
            throw new IllegalArgumentException("From position cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("To position cannot be null");
        }
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and to positions cannot be the same");
        }
    }

    public static SpecialMove of(Position from, Position to, Player player, String description) {
        return new SpecialMove(from, to, player, description);
    }

    //enroque
    public static SpecialMove castling(Position from, Position to, Player player, boolean isKingSide) {
        String description = isKingSide ? "King-side castling" : "Queen-side castling";
        return new SpecialMove(from, to, player, description);
    }

    //enpasant
    public static SpecialMove enPassant(Position from, Position to, Player player) {
        return new SpecialMove(from, to, player, "En passant capture");
    }

    //promocion de peon
    public static SpecialMove promotion(Position from, Position to, Player player, String pieceType) {
        return new SpecialMove(from, to, player, "Pawn promotion to " + pieceType);
    }

    public Move toMove() {
        return new Move(from, to, player);
    }

    @Override
    public String toString() {
        return from.toString() + "-" + to.toString() + " (" + description + ")";
    }
}

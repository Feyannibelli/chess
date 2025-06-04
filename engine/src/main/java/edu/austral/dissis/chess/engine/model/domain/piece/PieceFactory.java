package edu.austral.dissis.chess.engine.model.domain.piece;

import edu.austral.dissis.chess.engine.model.domain.movement.MovementStrategyFactory;

public class PieceFactory {

    public static Piece createPiece(PieceType type, Color color) {
        return new Piece(
                type,
                color,
                MovementStrategyFactory.createStrategy(type)
        );
    }

    // Standard set creation methods
    public static Piece createWhitePawn() {
        return createPiece(PieceType.PAWN, Color.WHITE);
    }

    public static Piece createBlackPawn() {
        return createPiece(PieceType.PAWN, Color.BLACK);
    }

    public static Piece createWhiteRook() {
        return createPiece(PieceType.ROOK, Color.WHITE);
    }

    public static Piece createBlackRook() {
        return createPiece(PieceType.ROOK, Color.BLACK);
    }

    public static Piece createWhiteKnight() {
        return createPiece(PieceType.KNIGHT, Color.WHITE);
    }

    public static Piece createBlackKnight() {
        return createPiece(PieceType.KNIGHT, Color.BLACK);
    }

    public static Piece createWhiteBishop() {
        return createPiece(PieceType.BISHOP, Color.WHITE);
    }

    public static Piece createBlackBishop() {
        return createPiece(PieceType.BISHOP, Color.BLACK);
    }

    public static Piece createWhiteQueen() {
        return createPiece(PieceType.QUEEN, Color.WHITE);
    }

    public static Piece createBlackQueen() {
        return createPiece(PieceType.QUEEN, Color.BLACK);
    }

    public static Piece createWhiteKing() {
        return createPiece(PieceType.KING, Color.WHITE);
    }

    public static Piece createBlackKing() {
        return createPiece(PieceType.KING, Color.BLACK);
    }
}
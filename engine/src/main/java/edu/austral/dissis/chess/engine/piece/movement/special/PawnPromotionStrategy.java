package edu.austral.dissis.chess.engine.piece.movement.special;

import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceFactory;
import edu.austral.dissis.chess.engine.piece.PieceType;

public final class PawnPromotionStrategy {

    public static boolean isPromotionMove(Position from, Position to, Color color) {
        return isPawnReachingEndRow(to, color);
    }

    public static Piece promoteToQueen(Color color) {
        return PieceFactory.createQueen(color);
    }

    public static Piece promoteTo(PieceType pieceType, Color color) {
        return switch (pieceType) {
            case QUEEN -> PieceFactory.createQueen(color);
            case ROOK -> PieceFactory.createRook(color);
            case BISHOP -> PieceFactory.createBishop(color);
            case KNIGHT -> PieceFactory.createKnight(color);
            default -> throw new IllegalArgumentException("Invalid promotion piece type");
        };
    }

    private static boolean isPawnReachingEndRow(Position position, Color color) {
        int endRow = color == Color.WHITE ? 7 : 0;
        return position.row() == endRow;
    }
}
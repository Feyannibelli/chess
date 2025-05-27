package edu.austral.dissis.chess.engine.piece;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.movement.*;
import java.util.List;

public final class PieceFactory {

    public static Piece createKing(Color color) {
        return new Piece(PieceType.KING, color, new KingMovementStrategy());
    }

    public static Piece createQueen(Color color) {
        return new Piece(PieceType.QUEEN, color, createQueenMovement());
    }

    public static Piece createRook(Color color) {
        return new Piece(PieceType.ROOK, color, new LinearMovementStrategy());
    }

    public static Piece createBishop(Color color) {
        return new Piece(PieceType.BISHOP, color, new DiagonalMovementStrategy());
    }

    public static Piece createKnight(Color color) {
        return new Piece(PieceType.KNIGHT, color, new KnightMovementStrategy());
    }

    public static Piece createPawn(Color color) {
        return new Piece(PieceType.PAWN, color, new PawnMovementStrategy());
    }

    private static MovementStrategy createQueenMovement() {
        return new CompositeMovementStrategy(
                List.of(new LinearMovementStrategy(), new DiagonalMovementStrategy())
        );
    }
}
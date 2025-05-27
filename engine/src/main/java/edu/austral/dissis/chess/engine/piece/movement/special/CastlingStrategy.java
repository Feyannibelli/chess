package edu.austral.dissis.chess.engine.piece.movement.special;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CastlingStrategy {

    public static List<Position> getCastlingMoves(Position kingPosition, Board board, Color color) {
        List<Position> moves = new ArrayList<>();

        if (!isKingInInitialPosition(kingPosition, color)) {
            return moves;
        }
        if (canCastleKingside(kingPosition, board, color)) {
            moves.add(new Position(kingPosition.row(), 6));
        }
        if (canCastleQueenside(kingPosition, board, color)) {
            moves.add(new Position(kingPosition.row(), 2));
        }

        return moves;
    }

    private static boolean isKingInInitialPosition(Position position, Color color) {
        int expectedRow = color == Color.WHITE ? 0 : 7;
        return position.row() == expectedRow && position.column() == 4;
    }

    private static boolean canCastleKingside(Position kingPos, Board board, Color color) {
        Position rookPos = new Position(kingPos.row(), 7);

        return isRookAtPosition(board, rookPos, color) &&
                areSquaresClear(board, kingPos, rookPos) &&
                !isKingInCheck(board, color) &&
                !wouldPassThroughCheck(board, kingPos, color, 1);
    }

    private static boolean canCastleQueenside(Position kingPos, Board board, Color color) {
        Position rookPos = new Position(kingPos.row(), 0);

        return isRookAtPosition(board, rookPos, color) &&
                areSquaresClear(board, kingPos, rookPos) &&
                !isKingInCheck(board, color) &&
                !wouldPassThroughCheck(board, kingPos, color, -1);
    }

    private static boolean isRookAtPosition(Board board, Position pos, Color color) {
        Optional<Piece> piece = board.getPieceAt(pos);
        return piece.isPresent() &&
                piece.get().type() == PieceType.ROOK &&
                piece.get().color() == color;
    }

    private static boolean areSquaresClear(Board board, Position kingPos, Position rookPos) {
        int start = Math.min(kingPos.column(), rookPos.column()) + 1;
        int end = Math.max(kingPos.column(), rookPos.column());

        for (int col = start; col < end; col++) {
            if (!board.isEmpty(new Position(kingPos.row(), col))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isKingInCheck(Board board, Color color) {
        // This would need to be implemented with full game context
        // For now, simplified implementation
        return false;
    }

    private static boolean wouldPassThroughCheck(Board board, Position kingPos, Color color, int direction) {
        // Check if king would pass through check during castling
        Position intermediatePos = new Position(kingPos.row(), kingPos.column() + direction);
        // This would need full check detection logic
        return false;
    }
}
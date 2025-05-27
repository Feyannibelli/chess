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

    private static final int KING_INITIAL_COLUMN = 4;
    private static final int KINGSIDE_ROOK_COLUMN = 7;
    private static final int QUEENSIDE_ROOK_COLUMN = 0;
    private static final int KINGSIDE_KING_TARGET = 6;
    private static final int QUEENSIDE_KING_TARGET = 2;

    public static List<Position> getCastlingMoves(Position kingPosition, Board board, Color color) {
        List<Position> moves = new ArrayList<>();

        if (!isKingInInitialPosition(kingPosition, color)) {
            return moves;
        }

        if (canCastleKingside(kingPosition, board, color)) {
            moves.add(createKingsideTarget(kingPosition));
        }

        if (canCastleQueenside(kingPosition, board, color)) {
            moves.add(createQueensideTarget(kingPosition));
        }

        return moves;
    }

    private static boolean isKingInInitialPosition(Position position, Color color) {
        int expectedRow = getInitialRow(color);
        return position.row() == expectedRow && position.column() == KING_INITIAL_COLUMN;
    }

    private static int getInitialRow(Color color) {
        return color == Color.WHITE ? 0 : 7;
    }

    private static Position createKingsideTarget(Position kingPosition) {
        return new Position(kingPosition.row(), KINGSIDE_KING_TARGET);
    }

    private static Position createQueensideTarget(Position kingPosition) {
        return new Position(kingPosition.row(), QUEENSIDE_KING_TARGET);
    }

    private static boolean canCastleKingside(Position kingPos, Board board, Color color) {
        Position rookPos = createKingsideRookPosition(kingPos);

        return isRookPresent(board, rookPos, color) &&
                areSquaresClearBetween(board, kingPos, rookPos) &&
                !wouldKingPassThroughDanger(board, kingPos, color, 1);
    }

    private static boolean canCastleQueenside(Position kingPos, Board board, Color color) {
        Position rookPos = createQueensideRookPosition(kingPos);

        return isRookPresent(board, rookPos, color) &&
                areSquaresClearBetween(board, kingPos, rookPos) &&
                !wouldKingPassThroughDanger(board, kingPos, color, -1);
    }

    private static Position createKingsideRookPosition(Position kingPos) {
        return new Position(kingPos.row(), KINGSIDE_ROOK_COLUMN);
    }

    private static Position createQueensideRookPosition(Position kingPos) {
        return new Position(kingPos.row(), QUEENSIDE_ROOK_COLUMN);
    }

    private static boolean isRookPresent(Board board, Position pos, Color color) {
        Optional<Piece> piece = board.getPieceAt(pos);
        return piece.isPresent() && isCorrectRook(piece.get(), color);
    }

    private static boolean isCorrectRook(Piece piece, Color color) {
        return piece.type() == PieceType.ROOK && piece.color() == color;
    }

    private static boolean areSquaresClearBetween(Board board, Position kingPos, Position rookPos) {
        int start = Math.min(kingPos.column(), rookPos.column()) + 1;
        int end = Math.max(kingPos.column(), rookPos.column());

        for (int col = start; col < end; col++) {
            if (isSquareOccupied(board, kingPos.row(), col)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSquareOccupied(Board board, int row, int col) {
        return !board.isEmpty(new Position(row, col));
    }

    private static boolean wouldKingPassThroughDanger(Board board, Position kingPos, Color color, int direction) {
        return false;
    }
}
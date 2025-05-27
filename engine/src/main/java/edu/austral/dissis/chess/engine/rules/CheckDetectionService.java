package edu.austral.dissis.chess.engine.rules;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceType;
import java.util.List;
import java.util.Optional;

public final class CheckDetectionService {

    public boolean isInCheck(Board board, Color kingColor) {
        Position kingPosition = findKingPosition(board, kingColor);
        return isPositionUnderAttack(board, kingPosition, kingColor.opposite());
    }

    public boolean isCheckmate(Board board, Color kingColor) {
        if (!isInCheck(board, kingColor)) {
            return false;
        }
        return hasNoValidMoves(board, kingColor);
    }

    public boolean isStalemate(Board board, Color kingColor) {
        if (isInCheck(board, kingColor)) {
            return false;
        }
        return hasNoValidMoves(board, kingColor);
    }

    public boolean wouldMoveLeaveKingInCheck(Board board, Position from, Position to, Color kingColor) {
        Optional<Piece> piece = board.getPieceAt(from);
        if (piece.isEmpty()) {
            return false;
        }

        Board testBoard = simulateMove(board, from, to);
        return isInCheck(testBoard, kingColor);
    }

    private Position findKingPosition(Board board, Color kingColor) {
        return board.getAllPieces().entrySet().stream()
                .filter(entry -> isKingOfColor(entry.getValue(), kingColor))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("King not found for color: " + kingColor));
    }

    private boolean isKingOfColor(Piece piece, Color color) {
        return piece.type() == PieceType.KING && piece.color() == color;
    }

    private boolean isPositionUnderAttack(Board board, Position position, Color attackingColor) {
        return board.getAllPieces().entrySet().stream()
                .filter(entry -> entry.getValue().color() == attackingColor)
                .anyMatch(entry -> canAttackPosition(board, entry.getKey(), position, entry.getValue()));
    }

    private boolean canAttackPosition(Board board, Position from, Position target, Piece piece) {
        List<Position> validMoves = piece.movementStrategy().getValidMoves(from, board, piece.color());
        return validMoves.contains(target);
    }

    private boolean hasNoValidMoves(Board board, Color color) {
        return board.getAllPieces().entrySet().stream()
                .filter(entry -> entry.getValue().color() == color)
                .noneMatch(entry -> hasValidMove(board, entry.getKey(), entry.getValue()));
    }

    private boolean hasValidMove(Board board, Position from, Piece piece) {
        List<Position> possibleMoves = piece.movementStrategy().getValidMoves(from, board, piece.color());

        return possibleMoves.stream()
                .anyMatch(to -> !wouldMoveLeaveKingInCheck(board, from, to, piece.color()));
    }

    private Board simulateMove(Board board, Position from, Position to) {
        Optional<Piece> piece = board.getPieceAt(from);
        if (piece.isEmpty()) {
            return board;
        }

        return board.withoutPieceAt(from).withPieceAt(to, piece.get());
    }
}
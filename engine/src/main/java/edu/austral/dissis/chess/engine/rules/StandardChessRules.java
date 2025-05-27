package edu.austral.dissis.chess.engine.rules;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.game.GameState;
import edu.austral.dissis.chess.engine.game.Move;
import edu.austral.dissis.chess.engine.game.Player;
import edu.austral.dissis.chess.engine.game.Result;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceType;
import edu.austral.dissis.chess.engine.piece.movement.special.PawnPromotionStrategy;
import java.util.List;
import java.util.Optional;

public final class StandardChessRules implements GameRules {

    @Override
    public boolean isValidMove(Move move, Board board) {
        Optional<Piece> piece = board.getPieceAt(move.from());

        if (piece.isEmpty()) {
            return false;
        }

        if (piece.get().color() != move.player().color()) {
            return false;
        }

        List<Position> validMoves = piece.get().movementStrategy()
                .getValidMoves(move.from(), board, piece.get().color());

        if (!validMoves.contains(move.to())) {
            return false;
        }

        return !wouldLeaveKingInCheck(move, board);
    }

    @Override
    public Result checkGameResult(Board board) {
        // Simplified implementation - would need full check/checkmate detection
        return Result.ongoing();
    }

    @Override
    public Board applyMove(Move move, Board board) {
        Optional<Piece> movingPiece = board.getPieceAt(move.from());

        if (movingPiece.isEmpty()) {
            return board;
        }

        Board newBoard = board.withoutPieceAt(move.from());

        Piece finalPiece = handleSpecialMoves(movingPiece.get(), move, board);
        newBoard = newBoard.withPieceAt(move.to(), finalPiece);

        return newBoard;
    }

    private boolean wouldLeaveKingInCheck(Move move, Board board) {
        Board testBoard = applyMove(move, board);
        Position kingPos = findKing(testBoard, move.player().color());
        return isPositionUnderAttack(kingPos, testBoard, move.player().color().opposite());
    }

    private Position findKing(Board board, Color color) {
        return board.getAllPieces().entrySet().stream()
                .filter(entry -> entry.getValue().type() == PieceType.KING &&
                        entry.getValue().color() == color)
                .map(entry -> entry.getKey())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("King not found"));
    }

    private boolean isPositionUnderAttack(Position position, Board board, Color attackingColor) {
        return board.getAllPieces().entrySet().stream()
                .filter(entry -> entry.getValue().color() == attackingColor)
                .anyMatch(entry -> {
                    List<Position> moves = entry.getValue().movementStrategy()
                            .getValidMoves(entry.getKey(), board, attackingColor);
                    return moves.contains(position);
                });
    }

    private Piece handleSpecialMoves(Piece piece, Move move, Board board) {
        if (piece.type() == PieceType.PAWN &&
                PawnPromotionStrategy.isPromotionMove(move.from(), move.to(), piece.color())) {
            return PawnPromotionStrategy.promoteToQueen(piece.color());
        }

        return piece;
    }
}
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

    private final CheckDetectionService checkDetectionService;

    public StandardChessRules() {
        this.checkDetectionService = new CheckDetectionService();
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if (isPieceNotPresent(move, board)) {
            return false;
        }

        if (isWrongPlayersPiece(move, board)) {
            return false;
        }

        if (isMoveNotInValidMoves(move, board)) {
            return false;
        }

        return !wouldLeaveKingInCheck(move, board);
    }

    @Override
    public Result checkGameResult(Board board) {
        for (Color color : Color.values()) {
            if (checkDetectionService.isCheckmate(board, color)) {
                Player winner = createWinnerForColor(color.opposite());
                return Result.checkmate(winner);
            }

            if (checkDetectionService.isStalemate(board, color)) {
                return Result.draw("Stalemate");
            }
        }

        return Result.ongoing();
    }

    @Override
    public Board applyMove(Move move, Board board) {
        Optional<Piece> movingPiece = board.getPieceAt(move.from());

        if (movingPiece.isEmpty()) {
            return board;
        }

        Board newBoard = removePieceFromOrigin(board, move.from());
        Piece finalPiece = handleSpecialMoves(movingPiece.get(), move, board);

        return placePieceAtDestination(newBoard, move.to(), finalPiece);
    }

    private boolean isPieceNotPresent(Move move, Board board) {
        return board.getPieceAt(move.from()).isEmpty();
    }

    private boolean isWrongPlayersPiece(Move move, Board board) {
        Optional<Piece> piece = board.getPieceAt(move.from());
        return piece.map(p -> p.color() != move.player().color()).orElse(true);
    }

    private boolean isMoveNotInValidMoves(Move move, Board board) {
        Optional<Piece> piece = board.getPieceAt(move.from());
        if (piece.isEmpty()) {
            return true;
        }

        List<Position> validMoves = getValidMovesForPiece(piece.get(), move.from(), board);
        return !validMoves.contains(move.to());
    }

    private List<Position> getValidMovesForPiece(Piece piece, Position from, Board board) {
        return piece.movementStrategy().getValidMoves(from, board, piece.color());
    }

    private boolean wouldLeaveKingInCheck(Move move, Board board) {
        return checkDetectionService.wouldMoveLeaveKingInCheck(
                board, move.from(), move.to(), move.player().color());
    }

    private Player createWinnerForColor(Color color) {
        return new Player("Player_" + color.name(), color);
    }

    private Board removePieceFromOrigin(Board board, Position from) {
        return board.withoutPieceAt(from);
    }

    private Board placePieceAtDestination(Board board, Position to, Piece piece) {
        return board.withPieceAt(to, piece);
    }

    private Piece handleSpecialMoves(Piece piece, Move move, Board board) {
        if (isPawnPromotion(piece, move)) {
            return PawnPromotionStrategy.promoteToQueen(piece.color());
        }
        return piece;
    }

    private boolean isPawnPromotion(Piece piece, Move move) {
        return piece.type() == PieceType.PAWN &&
                PawnPromotionStrategy.isPromotionMove(move.from(), move.to(), piece.color());
    }
}
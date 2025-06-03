package edu.austral.dissis.chess.engine.model.game.rules;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.game.Game;
import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

//situacion de jaquemate
public final class CheckmateValidator {

    //verifica si un color esta en jaque mate
    public static boolean isCheckmate(Game game, Color color) {
        Objects.requireNonNull(game, "Game cannot be null");
        Objects.requireNonNull(color, "Color cannot be null");

        Board board = game.getBoard();

        if (!CheckValidator.isKingInCheck(board, color)) {
            return false;
        }

        return !hasLegalMoves(game, color);
    }

    //verifica si un color tiene movimientos legales disponibles
    public static boolean hasLegalMoves(Game game, Color color) {
        Objects.requireNonNull(game, "Game cannot be null");
        Objects.requireNonNull(color, "Color cannot be null");

        Board board = game.getBoard();
        Map<Position, Piece> pieces = board.getPiecesOfColor(color);

        return pieces.entrySet().stream()
                .anyMatch(entry -> hasLegalMovesForPiece(entry.getKey(), entry.getValue(), board));
    }

    //verifica si una pieza tiene movimientos legales
    private static boolean hasLegalMovesForPiece(Position position, Piece piece, Board board) {
        Set<Position> possibleMoves = piece.getPosibleMoves(position, board);

        return possibleMoves.stream()
                .anyMatch(targetPosition -> isMoveLegal(position, targetPosition, piece, board));
    }

    //verifica si es legal el movimiento
    private static boolean isMoveLegal(Position from, Position to, Piece piece, Board board) {
        Board boardAfterMove = simulateMove(from, to, board);

        return !CheckValidator.isKingInCheck(boardAfterMove, piece.color());
    }

    private static Board simulateMove(Position from, Position to, Board board) {
        return board.movePiece(from, to);
    }
}

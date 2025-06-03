package edu.austral.dissis.chess.engine.model.game.rules;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.game.Game;
import edu.austral.dissis.chess.engine.model.piece.Color;

import java.util.Objects;

//situacion de empate o ahogado
public final class StalemateValidator {

    //verifica si un color esta en stalemate
    public static boolean isStalemate(Game game, Color color) {
        Objects.requireNonNull(game, "Game cannot be null");
        Objects.requireNonNull(color, "Color cannot be null");

        Board board = game.getBoard();

        if (CheckValidator.isKingInCheck(board, color)) {
            return false;
        }

        return !CheckmateValidator.hasLegalMoves(game, color);
    }

    //verifica si el juego esta en situacion de empate
    public static boolean isDraw(Game game) {
        Objects.requireNonNull(game, "Game cannot be null");

        Color currentPlayerColor = game.getCurrentPlayer().color();
        Color opponentColor = currentPlayerColor.opposite();

        return isStalemate(game, currentPlayerColor) ||
                isStalemate(game, opponentColor) ||
                isInsufficientMaterial(game.getBoard());
    }

    //verifica si hay insuficiensa para dar mate
    private static boolean isInsufficientMaterial(Board board) {
        var allPieces = board.getAllPieces();

        if (allPieces.size() == 2) {
            return allPieces.values().stream()
                    .allMatch(piece -> piece.type().isKing());
        }

        if (allPieces.size() == 3) {
            long nonKingPieces = allPieces.values().stream()
                    .filter(piece -> !piece.type().isKing())
                    .count();

            return nonKingPieces == 1 && allPieces.values().stream()
                    .anyMatch(piece -> piece.type().canJump() ||
                            piece.type().name().equals("BISHOP"));
        }

        return false;
    }
}
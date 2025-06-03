package edu.austral.dissis.chess.engine.model.game.rules;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.PieceType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

//verifica situacion de jaque
public final class CheckValidator {

    //verifica si el rey de un color especifico esta en jaque
    public static boolean isKingInCheck(Board board, Color kingColor) {
        Objects.requireNonNull(board, "Board cannot be null");
        Objects.requireNonNull(kingColor, "King color cannot be null");

        Position kingPosition = findKingPosition(board, kingColor);
        if (kingPosition == null) {
            return false;
        }

        return isPositionUnderAttack(board, kingPosition, kingColor);
    }

    //verifica si una posición está siendo atacada por piezas del color opuesto
    public static boolean isPositionUnderAttack(Board board, Position position, Color defendingColor) {
        Objects.requireNonNull(board, "Board cannot be null");
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(defendingColor, "Defending color cannot be null");

        Color attackingColor = defendingColor.opposite();
        Map<Position, Piece> attackingPieces = board.getPiecesOfColor(attackingColor);

        return attackingPieces.entrySet().stream()
                .anyMatch(entry -> canPieceAttackPosition(entry.getKey(), entry.getValue(), position, board));
    }

    //posicion del rey en el tablero
    public static Position findKingPosition(Board board, Color color) {
        Objects.requireNonNull(board, "Board cannot be null");
        Objects.requireNonNull(color, "Color cannot be null");

        return board.getPiecesOfColor(color).entrySet().stream()
                .filter(entry -> entry.getValue().type().isKing())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private static boolean canPieceAttackPosition(Position piecePosition, Piece piece,
                                                  Position targetPosition, Board board) {
        return piece.canMoveTo(piecePosition, targetPosition, board);
    }
}

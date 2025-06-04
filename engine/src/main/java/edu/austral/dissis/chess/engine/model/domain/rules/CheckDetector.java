package edu.austral.dissis.chess.engine.model.domain.rules;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.engine.model.domain.piece.PieceType;

import java.util.Optional;

public class CheckDetector {

    public static boolean isInCheck(Board board, Color kingColor) {
        Optional<Position> kingPosition = findKingPosition(board, kingColor);

        if (kingPosition.isEmpty()) {
            return false;
        }

        return isPositionAttacked(board, kingPosition.get(), kingColor.opposite());
    }

    public static boolean isCheckmate(Board board, Color kingColor) {
        if (!isInCheck(board, kingColor)) {
            return false;
        }

        Optional<Position> kingPosition = findKingPosition(board, kingColor);
        if (kingPosition.isEmpty()) {
            return false;
        }

        return !hasValidMoves(board, kingColor);
    }

    public static boolean isStalemate(Board board, Color currentPlayerColor) {
        if (isInCheck(board, currentPlayerColor)) {
            return false;
        }

        return !hasValidMoves(board, currentPlayerColor);
    }

    private static Optional<Position> findKingPosition(Board board, Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position position = new Position(row, col);
                Optional<Piece> piece = board.getPieceAt(position);

                if (piece.isPresent() &&
                        piece.get().type() == PieceType.KING &&
                        piece.get().color() == color) {
                    return Optional.of(position);
                }
            }
        }
        return Optional.empty();
    }

    private static boolean isPositionAttacked(Board board, Position target, Color attackerColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position position = new Position(row, col);
                Optional<Piece> piece = board.getPieceAt(position);

                if (piece.isPresent() &&
                        piece.get().color() == attackerColor &&
                        piece.get().canMoveTo(position, target, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasValidMoves(Board board, Color playerColor) {
        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Position from = new Position(fromRow, fromCol);
                Optional<Piece> piece = board.getPieceAt(from);

                if (piece.isPresent() && piece.get().color() == playerColor) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            Position to = new Position(toRow, toCol);

                            if (piece.get().canMoveTo(from, to, board)) {
                                Board newBoard = board.movePiece(from, to);
                                if (!isInCheck(newBoard, playerColor)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

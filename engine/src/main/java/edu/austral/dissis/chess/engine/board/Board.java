package edu.austral.dissis.chess.engine.board;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import java.util.Map;
import java.util.Optional;

public interface Board {
    Optional<Piece> getPieceAt(Position position);
    Board withPieceAt(Position position, Piece piece);
    Board withoutPieceAt(Position position);
    Map<Position, Piece> getAllPieces();

    default boolean isEmpty(Position position) {
        return getPieceAt(position).isEmpty();
    }

    default boolean isOpponentPiece(Position position, Color color) {
        return getPieceAt(position)
                .map(piece -> piece.color() != color)
                .orElse(false);
    }
}
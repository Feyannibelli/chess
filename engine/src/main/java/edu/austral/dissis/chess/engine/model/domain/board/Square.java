package edu.austral.dissis.chess.engine.model.domain.board;

import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import java.util.Optional;

public record Square(Position position, Optional<Piece> piece) {

    public Square(Position position) {
        this(position, Optional.empty());
    }

    public Square(Position position, Piece piece) {
        this(position, Optional.of(piece));
    }

    public boolean isEmpty() {
        return piece.isEmpty();
    }

    public boolean isOccupied() {
        return piece.isPresent();
    }
}
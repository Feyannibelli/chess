package edu.austral.dissis.chess.engine.piece;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.movement.MovementStrategy;

public record Piece(PieceType type, Color color, MovementStrategy movementStrategy) {

    public Piece {
        if (type == null || color == null || movementStrategy == null) {
            throw new IllegalArgumentException("Piece parameters cannot be null");
        }
    }
}
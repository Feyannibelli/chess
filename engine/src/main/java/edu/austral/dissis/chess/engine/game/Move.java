package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.board.Position;

public record Move(Position from, Position to, Player player) {

    public Move {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("Move parameters cannot be null");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and to positions cannot be the same");
        }
    }
}
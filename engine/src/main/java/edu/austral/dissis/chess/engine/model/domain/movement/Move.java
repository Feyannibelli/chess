package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.game.Player;

public record Move(Position from, Position to, Player player) {

    public Move {
        if (from == null || to == null || player == null) {
            throw new IllegalArgumentException("Move parameters cannot be null");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("From and To positions cannot be the same");
        }
    }
}
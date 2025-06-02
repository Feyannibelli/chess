package edu.austral.dissis.chess.engine.model.game;

import java.util.Objects;

public record InvalidMove(String reason) implements MoveResult {

    public InvalidMove {
        Objects.requireNonNull(reason, "Reason cannot be null");

        if (reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }
    }

    @Override
    public String toString() {
        return "Invalid move: " + reason;
    }
}
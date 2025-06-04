package edu.austral.dissis.chess.engine.model.domain.game;

import edu.austral.dissis.chess.engine.model.domain.piece.Color;

public record Player(String id, Color color) {

    public Player {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Player ID cannot be null or empty");
        }
    }
}
package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.color.Color;

public record Player(String id, Color color) {

    public Player {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id cannot be null or blank");
        }
        if (color == null) {
            throw new IllegalArgumentException("Player color cannot be null");
        }
    }
}
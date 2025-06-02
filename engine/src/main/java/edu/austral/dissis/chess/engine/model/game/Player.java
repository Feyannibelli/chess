package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.piece.Color;

import java.util.Objects;

//representa a los jugadores
public record Player(String name, Color color) {

    public Player {
        Objects.requireNonNull(name, "Player name cannot be null");
        Objects.requireNonNull(color, "Player color cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
    }

    public boolean isWhite() {
        return color.isWhite();
    }

    public boolean isBlack() {
        return color.isBlack();
    }

    public String getDisplayName() {
        return name + " (" + color.getDisplayName() + ")";
    }

    public boolean isOpponentOf(Player other) {
        return !this.color.equals(other.color);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}

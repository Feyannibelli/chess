package edu.austral.dissis.chess.engine.model.domain.game;

public record Turn(int number, Player player) {

    public Turn {
        if (number < 1) {
            throw new IllegalArgumentException("Turn number must be positive");
        }
    }

    public Turn next(Player nextPlayer) {
        return new Turn(number + 1, nextPlayer);
    }
}
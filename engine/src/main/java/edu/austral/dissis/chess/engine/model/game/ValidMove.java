package edu.austral.dissis.chess.engine.model.game;

import java.util.Objects;

public record ValidMove(Game resultingGame) implements MoveResult {


    public ValidMove {
        Objects.requireNonNull(resultingGame, "Resulting game cannot be null");
    }

    @Override
    public String toString() {
        return "Valid move - Game state: " + resultingGame.getGameState();
    }
}
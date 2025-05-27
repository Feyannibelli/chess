package edu.austral.dissis.chess.engine.game;

import java.util.Optional;

public record Result(GameState state, Optional<Player> winner, String message) {

    public Result {
        if (state == null) {
            throw new IllegalArgumentException("Game state cannot be null");
        }
        if (winner == null) {
            throw new IllegalArgumentException("Winner optional cannot be null");
        }
        if (message == null) {
            message = "";
        }
    }

    public static Result ongoing() {
        return new Result(GameState.ONGOING, Optional.empty(), "Game in progress");
    }

    public static Result checkmate(Player winner) {
        return new Result(GameState.CHECKMATE, Optional.of(winner), "Checkmate");
    }

    public static Result draw(String reason) {
        return new Result(GameState.DRAW, Optional.empty(), reason);
    }
}
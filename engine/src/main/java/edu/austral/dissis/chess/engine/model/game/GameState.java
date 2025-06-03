
package edu.austral.dissis.chess.engine.model.game;

//representa el estado actual del juego
public enum GameState {
    ONGOING("Game in progress"),
    CHECK("Check"),
    CHECKMATE("Checkmate"),
    STALEMATE("Stalemate"),
    DRAW("Draw");

    private final String description;

    GameState(String description) {
        this.description = description;
    }

    public boolean isGameOver() {
        return this == CHECKMATE || this == STALEMATE || this == DRAW;
    }

    public boolean isOngoing() {
        return this == ONGOING || this == CHECK;
    }

    public boolean hasWinner() {
        return this == CHECKMATE;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
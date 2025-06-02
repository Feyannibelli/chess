package edu.austral.dissis.chess.engine.model.game;

//representa los posibles estados de partida
public enum GameState {
    ACTIVE("Active", "El juego está en curso"),
    CHECK("Check", "El rey está en jaque"),
    CHECKMATE("Checkmate", "Jaque mate - fin del juego"),
    STALEMATE("Stalemate", "Empate por ahogado"),
    DRAW("Draw", "Empate");

    private final String displayName;
    private final String description;

    GameState(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this == ACTIVE || this == CHECK;
    }

    public boolean isGameOver() {
        return this == CHECKMATE || this == STALEMATE || this == DRAW;
    }

    public boolean hasWinner() {
        return this == CHECKMATE;
    }

    public boolean isDraw() {
        return this == STALEMATE || this == DRAW;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

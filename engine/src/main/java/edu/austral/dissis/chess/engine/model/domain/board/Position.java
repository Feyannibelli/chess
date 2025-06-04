package edu.austral.dissis.chess.engine.model.domain.board;

public record Position(int row, int column) {

    public Position {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new IllegalArgumentException("Position out of bounds");
        }
    }

    public boolean isValid() {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }
}
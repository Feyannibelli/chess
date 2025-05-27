package edu.austral.dissis.chess.engine.board;

public record Position(int row, int column) {

    public Position {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new IllegalArgumentException("Position out of bounds");
        }
    }

    public Position add(int deltaRow, int deltaColumn) {
        return new Position(row + deltaRow, column + deltaColumn);
    }

    public boolean isValid() {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }

    public int distanceTo(Position other) {
        return Math.max(Math.abs(row - other.row), Math.abs(column - other.column));
    }
}
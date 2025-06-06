package edu.austral.dissis.chess.engine.model.domain.board;

public record Position(int row, int column) {

    public Position {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be negative");
        }
    }

    public boolean isValidFor(int maxRows, int maxColumns) {
        return row >= 0 && row < maxRows && column >= 0 && column < maxColumns;
    }

    public boolean isValid() {
        return isValidFor(8, 8);
    }

    public Position add(int rowOffset, int columnOffset) {
        return new Position(row + rowOffset, column + columnOffset);
    }

    public Position subtract(Position other) {
        return new Position(row - other.row, column - other.column);
    }

    public int manhattanDistance(Position other) {
        return Math.abs(row - other.row) + Math.abs(column - other.column);
    }

    public double euclideanDistance(Position other) {
        int rowDiff = row - other.row;
        int colDiff = column - other.column;
        return Math.sqrt(rowDiff * rowDiff + colDiff * colDiff);
    }

    public boolean isAdjacent(Position other) {
        return manhattanDistance(other) == 1;
    }

    public boolean isDiagonal(Position other) {
        int rowDiff = Math.abs(row - other.row);
        int colDiff = Math.abs(column - other.column);
        return rowDiff == colDiff && rowDiff > 0;
    }

    public boolean isLinear(Position other) {
        return row == other.row || column == other.column;
    }

    public static Position at(int row, int column) {
        return new Position(row, column);
    }
}
package edu.austral.dissis.chess.engine.model.board;

import java.util.Objects;

public record BoardDimensions(int rows, int columns) {

    public static final BoardDimensions STANDARD_CHESS = new BoardDimensions(8, 8);

    public BoardDimensions {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                    "Board dimensions must be positive: rows=" + rows + ", columns=" + columns
            );
        }

        if (rows > 100 || columns > 100) {
            throw new IllegalArgumentException(
                    "Board dimensions too large: rows=" + rows + ", columns=" + columns
            );
        }
    }

    public boolean isValidPosition(Position position) {
        Objects.requireNonNull(position, "Position cannot be null");
        return position.row() >= 0 &&
                position.row() < rows &&
                position.column() >= 0 &&
                position.column() < columns;
    }


    public int getTotalSquares() {
        return rows * columns;
    }

    public boolean isSquare() {
        return rows == columns;
    }

    public int getMaxRow() {
        return rows - 1;
    }

    public int getMaxColumn() {
        return columns - 1;
    }

    public boolean isStandardChess() {
        return rows == 8 && columns == 8;
    }

    @Override
    public String toString() {
        return rows + "x" + columns;
    }
}
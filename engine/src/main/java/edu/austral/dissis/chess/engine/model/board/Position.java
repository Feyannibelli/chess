package edu.austral.dissis.chess.engine.model.board;

//representa la posicion en el tablero
public record Position(int row, int column) {

    //valida la posicion este dentro del limite del tablero
    public Position {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new IllegalArgumentException("Position must be within board bounds (0-7)");
        }
    }

    //crea nueva posicion desplazada
    public Position offset(int deltaRow, int deltaColumn) {
        int newRow = row + deltaRow;
        int newColumn = column + deltaColumn;

        if (newRow < 0 || newRow > 7 || newColumn < 0 || newColumn > 7) {
            return this;
        }

        return new Position(newRow, newColumn);
    }

    //calcula la distancia entre una posicion y otra
    public int manhattanDistance(Position other) {
        return Math.abs(row - other.row) + Math.abs(column - other.column);
    }

    //verifica si esta posicion esta en la misma fila que otra
    public boolean isSameRow(Position other) {
        return row == other.row;
    }

    //verifica si esta posicion esta en la misma columna que otra
    public boolean isSameColumn(Position other) {
        return column == other.column;
    }

    //verifica  si esta posicion esta en la misma diagonal que otra
    public boolean isSameDiagonal(Position other) {
        return Math.abs(row - other.row) == Math.abs(column - other.column);
    }

    //representa notaciones
    @Override
    public String toString() {
        char file = (char) ('a' + column);
        int rank = 8 - row;
        return "" + file + rank;
    }
}
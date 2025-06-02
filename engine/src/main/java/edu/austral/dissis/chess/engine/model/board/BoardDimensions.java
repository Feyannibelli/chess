package edu.austral.dissis.chess.engine.model.board;

//representacion dela dimensiones del tablero(permite tableros de varios tamaños)
public record BoardDimensions(int rows, int columns) {

    public static final BoardDimensions STANDARD = new BoardDimensions(8,8);

    //validacon de dimensiones
    public BoardDimensions {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive: rows=" + rows + ", columns=" + columns);
        }
    }

    //verifica si las posiciones estan dentro de las dimenciones
    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < rows &&
                position.column() >= 0 && position.column() < columns;
    }

    //calcula el numero total de casillas
    public int getTotalSquares() {
        return rows * columns;
    }

    public boolean isSquare() {
        return rows == columns;
    }
}

package edu.austral.dissis.chess.engine.model.board;

import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import java.util.*;

//representa el tablero de ajedrez, utiliza mapa interno que almacena las piezas y sus posiciones
public final class Board {
    private final int rows;
    private final int columns;
    private final Map<Position, Piece> pieces;

    //constructor principal que crea un nuevo tablero
    public Board(int rows, int columns, Map<Position, Piece> pieces) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = Map.copyOf(pieces);
    }

    //tablero estandar 8x8 vacio
    public Board() {
        this(8, 8, Map.of());
    }

    //constructor tablero de tamaño especifico vacio
    public Board(int rows, int columns) {
        this(rows, columns, Map.of());
    }

    //posicion de la pieza en posicion especifico
    public Optional<Piece> getPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    //crea un nuevo tablero con una pieza colocada  en la posicion especifica
    public Board withPiece(Position position, Piece piece) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.put(position, piece);
        return new Board(rows, columns, newPieces);
    }

    //crea un nuevo tablero sin la pieza
    public Board withoutPiece(Position position) {
        if (!pieces.containsKey(position)) {
            return this;
        }

        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.remove(position);
        return new Board(rows, columns, newPieces);
    }

    //verifica si una posicion esta ocupada por una pieza
    public boolean isOccupied(Position position) {
        return pieces.containsKey(position);
    }

    //verificar si una posicion esta dentro de los limites del tablero.
    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < rows &&
                position.column() >= 0 && position.column() < columns;
    }

    //obtiene todas las posiciones ocupadas por la pieza
    public Set<Position> getPositionsForColor(Color color) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().color() == color)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toSet());
    }

    //verifica si el camino entre dos posiciones esta libre
    public boolean isPathClear(Position from, Position to) {
        if (from.equals(to)) return true;

        int rowStep = Integer.compare(to.row() - from.row(), 0);
        int colStep = Integer.compare(to.column() - from.column(), 0);

        Position current = new Position(from.row() + rowStep, from.column() + colStep);

        while (!current.equals(to)) {
            if (isOccupied(current)) {
                return false;
            }
            current = new Position(current.row() + rowStep, current.column() + colStep);
        }

        return true;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    //obtiene una copia del mapa de piezas
    public Map<Position, Piece> getPieces() {
        return Map.copyOf(pieces);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        return rows == board.rows &&
                columns == board.columns &&
                Objects.equals(pieces, board.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns, pieces);
    }
}
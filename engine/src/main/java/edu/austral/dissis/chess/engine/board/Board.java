package edu.austral.dissis.chess.engine.board;

import edu.austral.dissis.chess.engine.piece.Piece;

import java.util.*;

//representacion del tablero
public final class Board {
    private final int rows;
    private final int columns;
    private final Map<Position, Piece> pieces;

    //crea un nuevo tablero con dimensiones y piezas especificas
    public Board(int rows, int columns, Map<Position, Piece> pieces) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = Collections.unmodifiableMap(new HashMap<>(pieces));
    }

    // da el numero de filas en el tablero
    public int getRows() {
        return rows;
    }

    //da el numero de columnas en el tablero
    public int getColumns() {
        return columns;
    }

    //consigue una pieza en una posicion especifica, si es que hay alguna
    public Optional<Piece> getPieceAt(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    // verifica si x posicion tiene ya una pieza ahi
    public boolean hasPieceAt(Position position) {
        return pieces.containsKey(position);
    }

    // verifica si la posicion esta dentro del rango del tablero
    public boolean isPositionInBounds(Position position) {
        int row = position.getRow();
        int column = position.getColumn();
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    //devuelve un nuevo tablero con una pieza especifica colocada en una posicion especifica
    public Board withPiece(Position position, Piece piece) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.put(position, piece);
        return new Board(rows, columns, newPieces);
    }

    //devuelve un nuevo tablero con una pieza especifica en una posicion removida
    public Board withoutPiece(Position position) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.remove(position);
        return new Board(rows, columns, newPieces);
    }

    // todas las piecas en el tablero
    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
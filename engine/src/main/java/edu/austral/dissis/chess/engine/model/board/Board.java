package edu.austral.dissis.chess.engine.model.board;

import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.Color;

import java.util.Optional;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.stream.Collectors;

//representa el tablero del juego
public final class Board {
    private final Map<Position, Square> squares;
    private final BoardDimensions dimensions;

    //crea un tablero con casillas dadas
    public Board(Map<Position, Square> squares, BoardDimensions dimensions) {
        this.squares = Map.copyOf(Objects.requireNonNull(squares, "Squares cannot be null"));
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions cannot be null");
        validateBoard();
    }

    //crea un tablero vacio
    public Board(BoardDimensions dimensions) {
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions cannot be null");
        this.squares = createEmptySquares();
    }

    //obtiene pieza en posicion especifica
    public Optional<Piece> getPieceAt(Position position) {
        return getSquareAt(position)
                .flatMap(Square::getPiece);
    }

    //coloca una pieza en una posicion especifica
    public Board placePiece(Position position, Piece piece) {
        validatePosition(position);
        Objects.requireNonNull(piece, "Piece cannot be null");

        Map<Position, Square> newSquares = new HashMap<>(squares);
        newSquares.put(position, new Square(position, piece));
        return new Board(newSquares, dimensions);
    }

    //remueve la pieza de una posicion
    public Board removePiece(Position position) {
        validatePosition(position);

        Map<Position, Square> newSquares = new HashMap<>(squares);
        newSquares.put(position, new Square(position));
        return new Board(newSquares, dimensions);
    }

    //mueve una pieza de una posicion a otra
    public Board movePiece(Position from, Position to) {
        validatePosition(from);
        validatePosition(to);

        Optional<Piece> piece = getPieceAt(from);
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("No piece at position " + from);
        }

        return this.removePiece(from).placePiece(to, piece.get().withMoved());
    }

    //verifica si una posicion esta vacia
    public boolean isEmpty(Position position) {
        return getPieceAt(position).isEmpty();
    }

    //verifica si una posicion es valida en el tablero
    public boolean isValidPosition(Position position) {
        return dimensions.isValidPosition(position);
    }

    //da toda  las piezas del tablero
    public Map<Position, Piece> getAllPieces() {
        return squares.values().stream()
                .filter(Square::hasPiece)
                .collect(Collectors.toUnmodifiableMap(
                        Square::position,
                        square -> square.getPiece().orElseThrow()
                ));
    }

    //dimensiones del tablero
    public BoardDimensions getDimensions() {
        return dimensions;
    }


    //obtiene todas las piezas de un color en especifico
    public Map<Position, Piece> getPiecesOfColor(Color color) {
        return getAllPieces().entrySet().stream()
                .filter(entry -> entry.getValue().color().equals(color))
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    private Optional<Square> getSquareAt(Position position) {
        return Optional.ofNullable(squares.get(position));
    }

    private Map<Position, Square> createEmptySquares() {
        Map<Position, Square> emptySquares = new HashMap<>();
        for (int row = 0; row < dimensions.rows(); row++) {
            for (int col = 0; col < dimensions.columns(); col++) {
                Position pos = new Position(row, col);
                emptySquares.put(pos, new Square(pos));
            }
        }
        return emptySquares;
    }

    private void validatePosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    private void validateBoard() {
        for (Position pos : squares.keySet()) {
            if (!isValidPosition(pos)) {
                throw new IllegalArgumentException("Invalid position in board: " + pos);
            }
        }
    }
}
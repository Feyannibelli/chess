package edu.austral.dissis.chess.engine.model.board;

import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.Objects;
import java.util.Optional;

//representa una casilla del tablero
public record Square(Position position, Optional<Piece> piece) {

    //validador de parametros
    public Square {
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(piece, "Piece optional cannot be null");
    }

    //casillas vacias
    public Square(Position position) {
        this(position, Optional.empty());
    }

    //casillas con piezas
    public Square(Position position, Piece piece) {
        this(position, Optional.of(piece));
    }

    //verificador de casillas vacias
    public boolean isEmpty() {
        return piece.isEmpty();
    }

    //verifica si la casilla tiene una pieza
    public boolean hasPiece() {
        return piece.isPresent();
    }

    //obtiene pieza existente
    public Optional<Piece> getPiece() {
        return piece;
    }

    //crea una nueva casilla con pieza diferente
    public Square withPiece(Piece newPiece) {
        return new Square(position, Optional.empty());
    }

    //crea una nueva casilla vacía en la misma posicion
    public Square withoutPiece() {
        return new Square(position, Optional.empty());
    }

    //verifica si hay una pieza de color especifico en x casilla
    public boolean hasPieceOfColor(Color color) {
        return piece.map(p -> p.color().equals(color)).orElse(false);
    }

    @Override
    public String toString(){
        return position.toString() + (piece.map(p -> ":" + p.toString()).orElse(":empty"));
    }
}

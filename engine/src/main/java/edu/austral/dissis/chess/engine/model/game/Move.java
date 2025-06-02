package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.movement.special.SpecialMoveType;

import java.util.Objects;
import java.util.Optional;

//representa el movimiento dentro del juego
public record Move(Position from, Position to, Piece piece, Optional<Piece> capturedPiece, Optional<SpecialMoveType> specialMoveType) {

    public Move {
        Objects.requireNonNull(from, "From position cannot be null");
        Objects.requireNonNull(to, "To position cannot be null");
        Objects.requireNonNull(piece, "Piece cannot be null");
        Objects.requireNonNull(capturedPiece, "Captured piece optional cannot be null");
        Objects.requireNonNull(specialMoveType, "Special move type optional cannot be null");
    }

    //movimientos sin captura
    public Move(Position from, Position to, Piece piece) {
        this(from, to, piece, Optional.empty(), Optional.empty());
    }

    //movimiento con captura
    public Move(Position from, Position to, Piece piece, Piece capturedPiece) {
        this(from, to, piece, Optional.of(capturedPiece), Optional.empty());
    }

    //movimientos especiales
    public Move(Position from, Position to, Piece piece, SpecialMoveType specialType) {
        this(from, to, piece, Optional.empty(), Optional.of(specialType));
    }

    //verifica si el movimiento es una captura
    public boolean isCapture() {
        return capturedPiece.isPresent();
    }

    //verifica si es un movimiento especial
    public boolean isSpecialMove() {
        return specialMoveType.isPresent();
    }

    //manhatan par calcular movimientos tipo torre
    public int getManhattanDistance() {
        return Math.abs(to.row() - from.row()) + Math.abs(to.column() - from.column());
    }

    //verifica movimientos en diagonal
    public boolean isDiagonal() {
        return Math.abs(to.row() - from.row()) == Math.abs(to.column() - from.column());
    }

    //verifica movimientos si son horizontales o verticales
    public boolean isLinear() {
        return to.row() == from.row() || to.column() == from.column();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(piece.toString()).append(" ");
        sb.append(from.toString()).append(" -> ").append(to.toString());

        if (isCapture()) {
            sb.append(" captures ").append(capturedPiece.get().toString());
        }

        if (isSpecialMove()) {
            sb.append(" (").append(specialMoveType.get().toString()).append(")");
        }

        return sb.toString();
    }
}

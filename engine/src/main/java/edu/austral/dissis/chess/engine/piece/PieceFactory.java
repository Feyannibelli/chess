package edu.austral.dissis.chess.engine.piece;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.movement.*;

public final class PieceFactory {

    //da color y tipo especifico
    public Piece createPiece(PieceType type, Color color) {
        MovementStrategy strategy = createMovementStrategy(type);
        return new Piece(type, color, strategy);
    }

    //crea pieza con x movimientos
    public Piece createCustomPiece(PieceType type, Color color, MovementStrategy strategy) {
        return new Piece(type, color, strategy);
    }

    //metodo para crear la estrategias de movimientos
    private MovementStrategy createMovementStrategy(PieceType type) {
        return switch (type) {
            case PAWN -> new PawnMovementStrategy();
            case ROOK -> new LinearMovementStrategy();
            case BISHOP -> new DiagonalMovementStrategy();
            case QUEEN -> new CompositeMovementStrategy(
                    java.util.List.of(new LinearMovementStrategy(), new DiagonalMovementStrategy())
            );
            case KING -> new KingMovementStrategy();
            case KNIGHT -> new KnightMovementStrategy();
        };
    }
}
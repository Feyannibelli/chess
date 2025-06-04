package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.piece.PieceType;
import java.util.List;

public class MovementStrategyFactory {

    public static MovementStrategy createStrategy(PieceType pieceType) {
        return switch (pieceType) {
            case PAWN -> new PawnMovementStrategy();
            case ROOK -> new LinearMovementStrategy();
            case BISHOP -> new DiagonalMovementStrategy();
            case KNIGHT -> new KnightMovementStrategy();
            case KING -> new KingMovementStrategy();
            case QUEEN -> new CompositeMovementStrategy(List.of(
                    new LinearMovementStrategy(),
                    new DiagonalMovementStrategy()
            ));
        };
    }
}
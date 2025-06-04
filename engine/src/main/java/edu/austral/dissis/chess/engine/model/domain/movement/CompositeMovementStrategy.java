package edu.austral.dissis.chess.engine.model.domain.movement;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import java.util.List;

public class CompositeMovementStrategy implements MovementStrategy {
    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color pieceColor) {
        return strategies.stream()
                .anyMatch(strategy -> strategy.canMoveTo(from, to, board, pieceColor));
    }
}
package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;
import java.util.List;

//combina multiples movimiento(perfecto para la reyna o variantes)
public final class CompositeMovementStrategy implements MovementStrategy {
    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalArgumentException("Strategies list cannot be null or empty");
        }
        this.strategies = List.copyOf(strategies);
    }

    public CompositeMovementStrategy(MovementStrategy... strategies) {
        this(List.of(strategies));
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        return strategies.stream()
                .anyMatch(strategy -> strategy.canMoveTo(from, to, board, color));
    }

    public List<MovementStrategy> getStrategies() {
        return strategies;
    }
}
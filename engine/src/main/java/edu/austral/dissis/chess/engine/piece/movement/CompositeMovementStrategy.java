package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;

import java.util.Collections;
import java.util.List;

//utiliza piezas que usen metodos o movimiento lineales como diagonales
public final class CompositeMovementStrategy implements MovementStrategy {

    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        this.strategies = Collections.unmodifiableList(strategies);
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        return strategies.stream()
                .anyMatch(strategy -> strategy.canMoveTo(from, to, board, color));
    }
}
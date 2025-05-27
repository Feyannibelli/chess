package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import java.util.ArrayList;
import java.util.List;

public final class CompositeMovementStrategy implements MovementStrategy {

    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public List<Position> getValidMoves(Position from, Board board, Color color) {
        List<Position> allMoves = new ArrayList<>();

        for (MovementStrategy strategy : strategies) {
            allMoves.addAll(strategy.getValidMoves(from, board, color));
        }

        return allMoves;
    }
}
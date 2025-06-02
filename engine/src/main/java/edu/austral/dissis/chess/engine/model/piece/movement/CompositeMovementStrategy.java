package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

//combinacion de multiples movimientos, implementa varios patrones de movimientos
public final class CompositeMovementStrategy implements MovementStrategy {
    private final List<MovementStrategy> strategies;
    private final String name;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        this.strategies = List.copyOf(Objects.requireNonNull(strategies, "Strategies cannot be null"));

        if (strategies.isEmpty()) {
            throw new IllegalArgumentException("Strategies list cannot be empty");
        }

        validateStrategies();
        this.name = buildCompositeName();
    }

    //constructor para dos movimientos
    public CompositeMovementStrategy(MovementStrategy first, MovementStrategy second) {
        this(List.of(first, second));
    }

    //constructir para tres movimientos
    public CompositeMovementStrategy(MovementStrategy first, MovementStrategy second, MovementStrategy third) {
        this(List.of(first, second, third));
    }

    //obtiene todos los movimientos posibles
    @Override
    public Set<Position> getPosibleMoves(Position from, Board board, Piece piece) {
        Set<Position> allMoves = new HashSet<>();

        for (MovementStrategy strategy : strategies) {
            Set<Position> strategyMoves = strategy.getPosibleMoves(from, board, piece);
            allMoves.addAll(strategyMoves);
        }

        return allMoves;
    }

    //verifica si el movimiento es valido
    @Override
    public boolean isValidMove(Position from, Position to, Board board, Piece piece) {
        return strategies.stream()
                .anyMatch(strategy -> strategy.isValidMove(from, to, board, piece));
    }

    //retorna nombre
    @Override
    public String getStrategyName() {
        return name;
    }

    //obtiene lista de movimientos
    public List<MovementStrategy> getStrategies() {
        return strategies;
    }

    //verifica si contiene movimiento especifico
    public boolean containsStrategy(Class<? extends MovementStrategy> strategyType) {
        return strategies.stream()
                .anyMatch(strategy -> strategyType.isInstance(strategy));
    }

    public int getStrategyCount() {
        return strategies.size();
    }

    private void validateStrategies() {
        for (MovementStrategy strategy : strategies) {
            if (strategy == null) {
                throw new IllegalArgumentException("Strategy cannot be null");
            }

            if (strategy instanceof CompositeMovementStrategy) {
                throw new IllegalArgumentException("Nested composite strategies are not allowed");
            }
        }
    }

    private String buildCompositeName() {
        if (strategies.size() == 1) {
            return strategies.get(0).getStrategyName();
        }

        StringBuilder nameBuilder = new StringBuilder("Composite(");
        for (int i = 0; i < strategies.size(); i++) {
            if (i > 0) nameBuilder.append("+");
            nameBuilder.append(strategies.get(i).getStrategyName());
        }
        nameBuilder.append(")");

        return nameBuilder.toString();
    }

    @Override
    public String toString() {
        return getStrategyName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CompositeMovementStrategy that = (CompositeMovementStrategy) obj;
        return Objects.equals(strategies, that.strategies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strategies);
    }
}
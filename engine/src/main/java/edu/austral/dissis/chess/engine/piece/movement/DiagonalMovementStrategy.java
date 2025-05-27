package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import java.util.ArrayList;
import java.util.List;

public final class DiagonalMovementStrategy implements MovementStrategy {

    @Override
    public List<Position> getValidMoves(Position from, Board board, Color color) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] direction : directions) {
            moves.addAll(getMovesInDirection(from, board, color, direction[0], direction[1]));
        }

        return moves;
    }

    private List<Position> getMovesInDirection(Position from, Board board, Color color,
                                               int deltaRow, int deltaColumn) {
        List<Position> moves = new ArrayList<>();
        Position current = from.add(deltaRow, deltaColumn);

        while (current.isValid()) {
            if (board.isEmpty(current)) {
                moves.add(current);
            } else {
                if (board.isOpponentPiece(current, color)) {
                    moves.add(current);
                }
                break;
            }
            current = current.add(deltaRow, deltaColumn);
        }

        return moves;
    }
}
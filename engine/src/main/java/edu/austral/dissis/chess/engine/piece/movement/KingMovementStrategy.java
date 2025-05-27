package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import java.util.ArrayList;
import java.util.List;

public final class KingMovementStrategy implements MovementStrategy {

    private static final int[][] KING_MOVES = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    @Override
    public List<Position> getValidMoves(Position from, Board board, Color color) {
        List<Position> moves = new ArrayList<>();

        for (int[] move : KING_MOVES) {
            Position target = from.add(move[0], move[1]);
            if (isValidMove(target, board, color)) {
                moves.add(target);
            }
        }

        return moves;
    }

    private boolean isValidMove(Position position, Board board, Color color) {
        return position.isValid() &&
                (board.isEmpty(position) || board.isOpponentPiece(position, color));
    }
}
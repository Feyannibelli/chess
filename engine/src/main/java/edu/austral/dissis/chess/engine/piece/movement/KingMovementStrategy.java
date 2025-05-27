package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.movement.special.CastlingStrategy;
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

        addBasicKingMoves(moves, from, board, color);
        addCastlingMoves(moves, from, board, color);

        return moves;
    }

    private void addBasicKingMoves(List<Position> moves, Position from, Board board, Color color) {
        for (int[] move : KING_MOVES) {
            Position target = from.add(move[0], move[1]);
            if (isValidBasicMove(target, board, color)) {
                moves.add(target);
            }
        }
    }

    private void addCastlingMoves(List<Position> moves, Position from, Board board, Color color) {
        List<Position> castlingMoves = CastlingStrategy.getCastlingMoves(from, board, color);
        moves.addAll(castlingMoves);
    }

    private boolean isValidBasicMove(Position position, Board board, Color color) {
        return position.isValid() && canMoveTo(position, board, color);
    }

    private boolean canMoveTo(Position position, Board board, Color color) {
        return board.isEmpty(position) || board.isOpponentPiece(position, color);
    }
}
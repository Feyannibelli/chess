package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import java.util.ArrayList;
import java.util.List;

public final class PawnMovementStrategy implements MovementStrategy {

    @Override
    public List<Position> getValidMoves(Position from, Board board, Color color) {
        List<Position> moves = new ArrayList<>();
        int direction = getDirection(color);

        moves.addAll(getForwardMoves(from, board, direction));
        moves.addAll(getCaptureMoves(from, board, color, direction));

        return moves;
    }

    private int getDirection(Color color) {
        return color == Color.WHITE ? -1 : 1;
    }

    private List<Position> getForwardMoves(Position from, Board board, int direction) {
        List<Position> moves = new ArrayList<>();
        Position oneStep = from.add(direction, 0);

        if (oneStep.isValid() && board.isEmpty(oneStep)) {
            moves.add(oneStep);

            if (isInitialPosition(from)) {
                Position twoSteps = from.add(direction * 2, 0);
                if (twoSteps.isValid() && board.isEmpty(twoSteps)) {
                    moves.add(twoSteps);
                }
            }
        }

        return moves;
    }

    private List<Position> getCaptureMoves(Position from, Board board, Color color, int direction) {
        List<Position> moves = new ArrayList<>();
        int[] columns = {-1, 1};

        for (int col : columns) {
            Position capturePos = from.add(direction, col);
            if (capturePos.isValid() && board.isOpponentPiece(capturePos, color)) {
                moves.add(capturePos);
            }
        }

        return moves;
    }

    private boolean isInitialPosition(Position position) {
        return position.row() == 1 || position.row() == 6;
    }
}

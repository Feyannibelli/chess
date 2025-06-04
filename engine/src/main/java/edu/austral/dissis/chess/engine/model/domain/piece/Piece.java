package edu.austral.dissis.chess.engine.model.domain.piece;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.movement.MovementStrategy;

public record Piece(PieceType type, Color color, MovementStrategy movementStrategy) {

    public boolean canMoveTo(Position from, Position to, Board board) {
        return movementStrategy.canMoveTo(from, to, board, color);
    }
}
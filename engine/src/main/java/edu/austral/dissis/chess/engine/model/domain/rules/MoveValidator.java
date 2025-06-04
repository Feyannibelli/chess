package edu.austral.dissis.chess.engine.model.domain.rules;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.engine.model.result.Result;

public class MoveValidator {

    public static Result<Void> validateBasicMove(Move move, Board board) {
        if (board.isEmpty(move.from())) {
            return Result.error("No piece at source position");
        }

        Piece piece = board.getPieceAt(move.from()).orElseThrow();
        if (piece.color() != move.player().color()) {
            return Result.error("Cannot move opponent's piece");
        }

        if (!isValidPosition(move.to())) {
            return Result.error("Move is out of bounds");
        }

        if (!piece.canMoveTo(move.from(), move.to(), board)) {
            return Result.error("Invalid move for this piece");
        }

        return Result.success(null);
    }

    private static boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < 8 &&
                position.column() >= 0 && position.column() < 8;
    }
}
package edu.austral.dissis.chess.engine.model.piece.movement.special;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.game.Move;

import java.util.Optional;

public interface SpecialMoveHandler {

    default boolean isValidSpecialMove(Position from, Position to, Board board, Piece piece) {
        return false;
    }

    default Board executeSpecialMove(Move move, Board board) {
        return board;
    }

    default Optional<SpecialMoveType> getSpecialMoveType(Position from, Position to, Piece piece, Board board, Optional<Move> lastMove) {
        return Optional.empty();
    }

    default boolean requiresUserInput(Position from, Position to, Piece piece, Board board) {
        return false;
    }

    default String getSpecialMoveDescription(Move move) {
        return move.specialMoveType()
                .map(SpecialMoveType::getDisplayName)
                .orElse("Special Move");
    }
}
package edu.austral.dissis.chess.engine.model.domain.rules;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.game.GameState;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.result.Result;

public class StandardChessRules implements GameRules {

    @Override
    public Result<Void> validateMove(Move move, Board board) {
        Result<Void> basicValidation = MoveValidator.validateBasicMove(move, board);
        if (basicValidation.isError()) {
            return basicValidation;
        }

        Board newBoard = board.movePiece(move.from(), move.to());
        if (CheckDetector.isInCheck(newBoard, move.player().color())) {
            return Result.error("Move would leave king in check");
        }

        return Result.success(null);
    }

    @Override
    public GameState updateGameState(Board board, Color currentPlayerColor) {
        if (CheckDetector.isCheckmate(board, currentPlayerColor)) {
            return GameState.CHECKMATE;
        }

        if (CheckDetector.isStalemate(board, currentPlayerColor)) {
            return GameState.STALEMATE;
        }

        if (CheckDetector.isInCheck(board, currentPlayerColor)) {
            return GameState.CHECK;
        }

        return GameState.ONGOING;
    }

    @Override
    public boolean isGameOver(GameState gameState) {
        return gameState == GameState.CHECKMATE ||
                gameState == GameState.STALEMATE ||
                gameState == GameState.DRAW;
    }
}
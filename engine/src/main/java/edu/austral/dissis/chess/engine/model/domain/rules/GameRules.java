package edu.austral.dissis.chess.engine.model.domain.rules;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.game.GameState;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.result.Result;

public interface GameRules {
    Result<Void> validateMove(Move move, Board board);
    GameState updateGameState(Board board, Color currentPlayerColor);
    boolean isGameOver(GameState gameState);
}
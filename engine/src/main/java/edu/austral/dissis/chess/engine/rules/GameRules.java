package edu.austral.dissis.chess.engine.rules;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.game.Move;
import edu.austral.dissis.chess.engine.game.Result;

public interface GameRules {
    boolean isValidMove(Move move, Board board);
    Result checkGameResult(Board board);
    Board applyMove(Move move, Board board);
}
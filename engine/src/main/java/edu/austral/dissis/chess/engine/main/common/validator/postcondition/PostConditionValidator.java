package edu.austral.dissis.chess.engine.main.common.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;

public interface PostConditionValidator {

  PostConditionResult validate(GameState gameState, Board updatedBoard);
}

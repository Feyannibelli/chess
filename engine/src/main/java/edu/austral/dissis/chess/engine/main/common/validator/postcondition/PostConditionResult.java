package edu.austral.dissis.chess.engine.main.common.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.board.Board;

public sealed interface PostConditionResult permits ResultInvalid, ResultValid {
  Board getBoard();
}

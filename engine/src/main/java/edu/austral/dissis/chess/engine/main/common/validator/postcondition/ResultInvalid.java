package edu.austral.dissis.chess.engine.main.common.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.board.Board;

// invalidacion post resultado representa la razon de invalidacion
public final class ResultInvalid implements PostConditionResult {
  private final String message;

  public ResultInvalid(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public Board getBoard() {
    return null;
  }
}

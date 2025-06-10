package edu.austral.dissis.chess.engine.main.common.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.board.Board;

// valida la post resultados representa el tablero despues de la post
public final class ResultValid implements PostConditionResult {
  private final Board board;

  public ResultValid(Board board) {
    this.board = board;
  }

  @Override
  public Board getBoard() {
    return board;
  }
}

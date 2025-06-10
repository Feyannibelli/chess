package edu.austral.dissis.chess.engine.main.common.board;

public record Position(int row, int column) {
  @Override
  public int row() {
    return row;
  }

  @Override
  public int column() {
    return column;
  }
}

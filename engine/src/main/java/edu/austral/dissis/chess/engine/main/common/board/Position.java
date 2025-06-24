package edu.austral.dissis.chess.engine.main.common.board;

public record Position(int row, int column) {

  public int row() {
    return row;
  }

  public int column() {
    return column;
  }
}

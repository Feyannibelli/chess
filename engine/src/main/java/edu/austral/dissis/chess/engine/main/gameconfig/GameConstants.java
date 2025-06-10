package edu.austral.dissis.chess.engine.main.gameconfig;

public class GameConstants {
  public static final int ROWS = 8;
  public static final int COLUMNS = 8;
  public static final GameType GAME_TYPE = GameType.CHESS;

  public enum GameType {
    CHECKERS,
    CHESS
  }
}

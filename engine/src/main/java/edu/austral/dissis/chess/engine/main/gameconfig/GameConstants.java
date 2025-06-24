package edu.austral.dissis.chess.engine.main.gameconfig;

public class GameConstants {
  public static final int ROWS = 8;
  public static final int COLUMNS = 8;
  public static final int CAPABLANCA_ROWS = 10;
  public static final int CAPABLANCA_COLUMNS = 10;
  public static final GameType DEFAULT_GAME_TYPE = GameType.CHESS;

  public enum GameType {
    CHECKERS,
    CHESS,
    CAPABLANCA
  }

  public static String getGameDisplayName(GameType gameType) {
    return switch (gameType) {
      case CHESS -> "Chess";
      case CHECKERS -> "Checkers";
      case CAPABLANCA -> "Capablanca Chess";
    };
  }

  public static GameType fromString(String gameTypeName) {
    return switch (gameTypeName.toLowerCase()) {
      case "chess" -> GameType.CHESS;
      case "checkers" -> GameType.CHECKERS;
      case "capablanca" -> GameType.CAPABLANCA;
      default -> DEFAULT_GAME_TYPE;
    };
  }
}

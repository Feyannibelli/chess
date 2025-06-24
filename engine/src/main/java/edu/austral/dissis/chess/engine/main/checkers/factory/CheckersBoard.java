package edu.austral.dissis.chess.engine.main.checkers.factory;

import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.COLUMNS;
import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.ROWS;

import edu.austral.dissis.chess.engine.main.checkers.factory.pieceinit.ManInitializer;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class CheckersBoard {

  public static Board createCheckersBoard() {
    Map<Position, Piece> map = new HashMap<>();

    for (int i = 0; i <= 2; i++) {
      int startColumn = (i % 2 == 1) ? 2 : 1;
      for (int j = startColumn; j <= 7; j += 2) {
        map.put(new Position(i, j), new ManInitializer().initialize(Color.WHITE));
      }
    }

    map.put(new Position(1, 0), new ManInitializer().initialize(Color.WHITE));

    for (int i = 5; i <= 7; i++) {
      int startColumn = (i % 2 == 1) ? 2 : 1;
      for (int j = startColumn; j <= 7; j += 2) {
        map.put(new Position(i, j), new ManInitializer().initialize(Color.BLACK));
      }
    }

    map.put(new Position(7, 0), new ManInitializer().initialize(Color.BLACK));
    map.put(new Position(5, 0), new ManInitializer().initialize(Color.BLACK));

    return new BoardImpl(getWidth(8), getHeight(8), map);
  }

  private static int getWidth(int minSize) {
    return Math.max(ROWS, minSize);
  }

  private static int getHeight(int minSize) {
    return Math.max(COLUMNS, minSize);
  }
}

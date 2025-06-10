package edu.austral.dissis.chess.engine.main.chess.factory;

import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.COLUMNS;
import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.ROWS;

import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.BishopInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KingInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KnightInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.PawnInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.RookInitializer;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard {

  public static Board createClassicChessBoard() {
    Map<Position, Piece> map = new HashMap<>();

    map.put(new Position(0, 0), new RookInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 7), new RookInitializer().initialize(Color.WHITE));
    map.put(new Position(7, 0), new RookInitializer().initialize(Color.BLACK));
    map.put(new Position(7, 7), new RookInitializer().initialize(Color.BLACK));

    map.put(new Position(0, 1), new KnightInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 6), new KnightInitializer().initialize(Color.WHITE));
    map.put(new Position(7, 1), new KnightInitializer().initialize(Color.BLACK));
    map.put(new Position(7, 6), new KnightInitializer().initialize(Color.BLACK));

    map.put(new Position(0, 2), new BishopInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 5), new BishopInitializer().initialize(Color.WHITE));
    map.put(new Position(7, 2), new BishopInitializer().initialize(Color.BLACK));
    map.put(new Position(7, 5), new BishopInitializer().initialize(Color.BLACK));

    map.put(new Position(0, 3), new QueenInitializer().initialize(Color.WHITE));
    map.put(new Position(7, 3), new QueenInitializer().initialize(Color.BLACK));

    map.put(new Position(0, 4), new KingInitializer().initialize(Color.WHITE));
    map.put(new Position(7, 4), new KingInitializer().initialize(Color.BLACK));

    for (int i = 0; i < 8; i++) {
      map.put(new Position(1, i), new PawnInitializer().initialize(Color.WHITE));
      map.put(new Position(6, i), new PawnInitializer().initialize(Color.BLACK));
    }

    return new BoardImpl(getWidth(8), getHeight(8), map);
  }

  private static int getWidth(int minSize) {
    return Math.max(ROWS, minSize);
  }

  private static int getHeight(int minSize) {
    return Math.max(COLUMNS, minSize);
  }
}

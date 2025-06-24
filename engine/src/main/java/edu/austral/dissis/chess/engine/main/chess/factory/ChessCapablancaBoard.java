package edu.austral.dissis.chess.engine.main.chess.factory;

import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.CAPABLANCA_COLUMNS;
import static edu.austral.dissis.chess.engine.main.gameconfig.GameConstants.CAPABLANCA_ROWS;

import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.BishopInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KingInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KnightInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.PawnInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.RookInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.variant.ArchbishopInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.variant.ChancellorInitializer;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class ChessCapablancaBoard {

  public static Board createCapablancaChessBoard() {
    Map<Position, Piece> map = new HashMap<>();

    // Rooks at corners
    map.put(new Position(0, 0), new RookInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 9), new RookInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 0), new RookInitializer().initialize(Color.BLACK));
    map.put(new Position(9, 9), new RookInitializer().initialize(Color.BLACK));

    // Knights
    map.put(new Position(0, 1), new KnightInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 8), new KnightInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 1), new KnightInitializer().initialize(Color.BLACK));
    map.put(new Position(9, 8), new KnightInitializer().initialize(Color.BLACK));

    // Bishops
    map.put(new Position(0, 2), new BishopInitializer().initialize(Color.WHITE));
    map.put(new Position(0, 7), new BishopInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 2), new BishopInitializer().initialize(Color.BLACK));
    map.put(new Position(9, 7), new BishopInitializer().initialize(Color.BLACK));

    // Archbishop (Bishop + Knight)
    map.put(new Position(0, 3), new ArchbishopInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 3), new ArchbishopInitializer().initialize(Color.BLACK));

    // Chancellor (Rook + Knight)
    map.put(new Position(0, 6), new ChancellorInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 6), new ChancellorInitializer().initialize(Color.BLACK));

    // Queen
    map.put(new Position(0, 4), new QueenInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 4), new QueenInitializer().initialize(Color.BLACK));

    // King
    map.put(new Position(0, 5), new KingInitializer().initialize(Color.WHITE));
    map.put(new Position(9, 5), new KingInitializer().initialize(Color.BLACK));

    // Pawns
    for (int i = 0; i < 10; i++) {
      map.put(new Position(1, i), new PawnInitializer().initialize(Color.WHITE));
      map.put(new Position(8, i), new PawnInitializer().initialize(Color.BLACK));
    }

    return new BoardImpl(CAPABLANCA_ROWS, CAPABLANCA_COLUMNS, map);
  }
}

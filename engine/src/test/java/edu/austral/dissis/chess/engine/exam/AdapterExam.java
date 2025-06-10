package edu.austral.dissis.chess.engine.exam;

import static edu.austral.dissis.chess.engine.main.common.piece.PieceType.BISHOP;
import static edu.austral.dissis.chess.engine.main.common.piece.PieceType.KNIGHT;
import static edu.austral.dissis.chess.engine.main.common.piece.PieceType.PAWN;

import edu.austral.dissis.chess.engine.main.chess.factory.ChessGame;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.BishopInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KingInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.KnightInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.PawnInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.RookInitializer;
import edu.austral.dissis.chess.engine.main.chess.validator.turn.ChessTurnValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameStateType;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.TestSize;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AdapterExam {

  public Movement toMovement(TestPosition from, TestPosition to) {
    Position fromPosition = fromTestPositiontoToPosition(from);
    Position toPosition = fromTestPositiontoToPosition(to);
    return new Movement(fromPosition, toPosition);
  }

  public static Position fromTestPositiontoToPosition(TestPosition testPosition) {
    return new Position(testPosition.getRow() - 1, testPosition.getCol() - 1);
  }

  public static TestPiece fromPieceToTestPiece(Piece piece) {
    char pieceTypeSymbol =
        switch (piece.getType()) {
          case PAWN -> 'P';
          case KNIGHT -> 'N';
          case BISHOP -> 'B';
          case ROOK -> 'R';
          case QUEEN -> 'Q';
          case KING -> 'K';
          default -> throw new IllegalArgumentException("Unknown piece name: " + piece.getType());
        };

    char playerColorSymbol =
        switch (piece.getColor()) {
          case WHITE -> 'W';
          case BLACK -> 'B';
        };

    return new TestPiece(pieceTypeSymbol, playerColorSymbol);
  }

  public TestBoard convertToTestBoard(Board board) {
    int size = 8;
    Map<TestPosition, TestPiece> testPiecesMap = new HashMap<>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Piece classicChessPiece = board.getPieceByPosition(new Position(j, i));
        if (classicChessPiece != null) {
          TestPosition position = new TestPosition(j + 1, i + 1);
          TestPiece testPiece = fromPieceToTestPiece(classicChessPiece);
          testPiecesMap.put(position, testPiece);
        }
      }
    }
    return new TestBoard(new TestSize(8, 8), testPiecesMap);
  }

  public BoardImpl fromTestBoardToBoard(TestBoard testBoard) {
    Map<Position, Piece> map = new HashMap<>();

    for (Map.Entry<TestPosition, TestPiece> entry : testBoard.getPieces().entrySet()) {
      TestPosition testPosition = entry.getKey();
      TestPiece testPiece = entry.getValue();

      Position position = new Position(testPosition.getRow() - 1, testPosition.getCol() - 1);
      Piece piece = createPiece(testPiece.getPieceTypeSymbol(), testPiece.getPlayerColorSymbol());
      map.put(position, piece);
    }

    return new BoardImpl(testBoard.getSize().getCols(), testBoard.getSize().getRows(), map);
  }

  public static Piece createPiece(char pieceTypeSymbol, char playerColorSymbol) {
    Color player = playerColorSymbol == 'W' ? Color.WHITE : Color.BLACK;
    switch (pieceTypeSymbol) {
      case 'P':
        {
          PawnInitializer pawn = new PawnInitializer();
          return pawn.initialize(player, String.valueOf(PAWN));
        }
      case 'R':
        {
          RookInitializer rook = new RookInitializer();
          return rook.initialize(player, String.valueOf(PieceType.ROOK));
        }
      case 'N':
        {
          KnightInitializer knight = new KnightInitializer();
          return knight.initialize(player, String.valueOf(KNIGHT));
        }
      case 'B':
        {
          BishopInitializer bishop = new BishopInitializer();
          return bishop.initialize(player, String.valueOf(BISHOP));
        }
      case 'Q':
        {
          QueenInitializer queen = new QueenInitializer();
          return queen.initialize(player, String.valueOf(PieceType.QUEEN));
        }
      case 'K':
        {
          KingInitializer king = new KingInitializer();
          return king.initialize(player, String.valueOf(PieceType.KING));
        }
      default:
        return null;
    }
  }

  public GameState toGame(TestBoard testBoard) {
    System.out.println(testBoard);

    GameState gameState = ChessGame.createChessNormalGame();

    TurnValidator turnValidator = new ChessTurnValidator(Color.WHITE);

    Board boardImp = fromTestBoardToBoard(testBoard);

    return new GameStateImpl(
        new LinkedList<>(Collections.singletonList(boardImp)),
        gameState.getWinCondition(),
        turnValidator,
        gameState.getListPreConditions(),
        gameState.getListPostConditions(),
        GameStateType.NORMAL);
  }
}

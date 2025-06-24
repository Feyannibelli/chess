package edu.austral.dissis.chess.engine.main.adapter;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.FinishGameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.InvalidMoveGameStateImpl;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.gui.BoardSize;
import edu.austral.dissis.chess.gui.ChessPiece;
import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.gui.UndoState;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Adapter {
  GameController gameController;

  public Adapter(GameState initialState) {
    this.gameController = new GameController(initialState);
  }

  public GameState movePiece(Movement movement) {
    gameController.makeMove(movement);
    return gameController.getGameState();
  }

  public MoveResult move(@NotNull Move move) {
    Movement parsedMove = parseMove(move);
    gameController.makeMove(parsedMove);
    GameState result = gameController.getGameState();

    if (result instanceof GameStateImpl successfulMoveGameState) {
      return updateState(successfulMoveGameState);
    } else if (result instanceof InvalidMoveGameStateImpl invalidMoveGameState) {
      return new InvalidMove(invalidMoveGameState.errorMessage());
    } else if (result instanceof FinishGameStateImpl finishGameState) {
      return new GameOver(parseColor(finishGameState.getTurnManager().getTurn()));
    } else {
      throw new IllegalStateException("Unexpected game state: " + result);
    }
  }

  public InitialState init() {
    return new InitialState(
        getBoardSize(gameController.getGameState()),
        getPieces(gameController.getGameState()),
        getTurn(gameController.getGameState()));
  }

  public MoveResult updateState(GameState gameState) {
    gameController.setGameState(gameState);
    return new NewGameState(getPieces(gameState), nextTurn(gameState), new UndoState(true, true));
  }

  public List<ChessPiece> getPieces(GameState gameState) {
    List<edu.austral.dissis.chess.engine.main.common.board.Position> positions =
        gameState.getCurrentBoard().getOccupiedPositions();

    List<ChessPiece> chessPieces = new ArrayList<>();
    for (edu.austral.dissis.chess.engine.main.common.board.Position position : positions) {
      Piece piece = gameState.getCurrentBoard().getPieceByPosition(position);
      chessPieces.add(
          new ChessPiece(
              piece.getId(),
              parseColor(piece.getColor()),
              parsePosition(position),
              piece.getType().toString().toLowerCase()));
    }
    return chessPieces;
  }

  public PlayerColor getTurn(GameState gameState) {
    return parseColor(gameState.getCurrentTurn());
  }

  private PlayerColor nextTurn(GameState gameState) {
    return parseColor(gameState.getCurrentTurn());
  }

  public BoardSize getBoardSize(GameState gameState) {
    Board board = gameState.getCurrentBoard();
    return new BoardSize(board.getWidth(), board.getHeight());
  }

  private Movement parseMove(Move move) {
    edu.austral.dissis.chess.engine.main.common.board.Position from =
        new edu.austral.dissis.chess.engine.main.common.board.Position(
            move.getFrom().getRow() - 1, move.getFrom().getColumn() - 1);
    edu.austral.dissis.chess.engine.main.common.board.Position to =
        new edu.austral.dissis.chess.engine.main.common.board.Position(
            move.getTo().getRow() - 1, move.getTo().getColumn() - 1);
    return new Movement(from, to);
  }

  public PlayerColor parseColor(Color color) {
    return switch (color) {
      case WHITE -> PlayerColor.WHITE;
      case BLACK -> PlayerColor.BLACK;
    };
  }

  private Position parsePosition(
      edu.austral.dissis.chess.engine.main.common.board.Position position) {
    return new Position(position.row() + 1, position.column() + 1);
  }

  public NewGameState redo() {
    gameController.redoMove();
    return new NewGameState(
        getPieces(gameController.getGameState()),
        getTurn(gameController.getGameState()),
        new UndoState(
            gameController.getGameState().canUndo(), gameController.getGameState().canRedo()));
  }

  public NewGameState undo() {
    gameController.undoMove();
    return new NewGameState(
        getPieces(gameController.getGameState()),
        getTurn(gameController.getGameState()),
        new UndoState(
            gameController.getGameState().canUndo(), gameController.getGameState().canRedo()));
  }

  public GameState getGameState() {
    return gameController.getGameState();
  }

  public void setGameState(GameState gameState) {
    gameController.setGameState(gameState);
  }
}

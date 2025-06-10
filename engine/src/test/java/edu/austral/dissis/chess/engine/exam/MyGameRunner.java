package edu.austral.dissis.chess.engine.exam;

import static edu.austral.dissis.chess.engine.main.chess.factory.ChessGame.createChessNormalGame;
import static edu.austral.dissis.chess.engine.main.common.game.GameStateType.BLACK_CHECKMATE;
import static edu.austral.dissis.chess.engine.main.common.game.GameStateType.INVALID_MOVE;
import static edu.austral.dissis.chess.engine.main.common.game.GameStateType.WHITE_CHECKMATE;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.FinishGameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.game.BlackCheckMate;
import edu.austral.dissis.chess.test.game.TestGameRunner;
import edu.austral.dissis.chess.test.game.TestMoveDraw;
import edu.austral.dissis.chess.test.game.TestMoveFailure;
import edu.austral.dissis.chess.test.game.TestMoveResult;
import edu.austral.dissis.chess.test.game.TestMoveSuccess;
import edu.austral.dissis.chess.test.game.WhiteCheckMate;
import org.jetbrains.annotations.NotNull;

public class MyGameRunner implements TestGameRunner {
  GameState gameState;
  AdapterExam adapter;

  public MyGameRunner() {
    this.gameState = createChessNormalGame();
    this.adapter = new AdapterExam();
  }

  @NotNull
  @Override
  public TestMoveResult executeMove(
      @NotNull TestPosition testPosition, @NotNull TestPosition testPosition1) {
    Movement movement = adapter.toMovement(testPosition, testPosition1);
    GameState newGameState = gameState.movePiece(movement);
    TestMoveResult result;

    if (newGameState instanceof GameStateImpl) {
      if (movementIsValid(newGameState, gameState)) {
        gameState = newGameState;
        result = new TestMoveSuccess(this);
      } else {
        result = new TestMoveFailure(adapter.convertToTestBoard(gameState.getCurrentBoard()));
      }
      return result;
    } else if (newGameState instanceof FinishGameStateImpl) {
      if (newGameState.getGameStateType() == WHITE_CHECKMATE) {
        result = new WhiteCheckMate(adapter.convertToTestBoard(newGameState.getCurrentBoard()));
        return result;
      } else if (newGameState.getGameStateType() == BLACK_CHECKMATE) {
        result = new BlackCheckMate(adapter.convertToTestBoard(newGameState.getCurrentBoard()));
        return result;
      }
      result = new TestMoveDraw(adapter.convertToTestBoard(newGameState.getCurrentBoard()));
      return result;
    } else if (newGameState.getGameStateType() == INVALID_MOVE) {
      result = new TestMoveFailure(adapter.convertToTestBoard(newGameState.getCurrentBoard()));
      return result;
    }
    throw new IllegalStateException("Unexpected value: " + newGameState.getClass());
  }

  private boolean movementIsValid(GameState newGameState, GameState oldGameState) {
    Board oldBoard = oldGameState.getCurrentBoard();
    Board newBoard = newGameState.getCurrentBoard();
    return (oldBoard != newBoard);
  }

  @NotNull
  @Override
  public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
    gameState = adapter.toGame(testBoard);
    return this;
  }

  @NotNull
  @Override
  public TestMoveResult undo() {
    GameState game = gameState.undo();
    if (game == null) {
      return new TestMoveFailure(adapter.convertToTestBoard(gameState.getCurrentBoard()));
    } else {
      gameState = game;
      return new TestMoveSuccess(this);
    }
  }

  @NotNull
  @Override
  public TestMoveResult redo() {
    GameState game = gameState.redo();
    if (game == null) {
      return new TestMoveFailure(adapter.convertToTestBoard(gameState.getCurrentBoard()));
    } else {
      gameState = game;
      return new TestMoveSuccess(this);
    }
  }

  @NotNull
  @Override
  public TestBoard getBoard() {
    return adapter.convertToTestBoard(gameState.getCurrentBoard());
  }
}

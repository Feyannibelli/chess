package edu.austral.dissis.chess.engine.main.common.game;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.WinCondition;
import java.util.List;

public record InvalidMoveGameStateImpl(GameState gameState, String errorMessage)
    implements GameState {

  @Override
  public List<Board> getBoards() {
    return gameState.getBoards();
  }

  @Override
  public Board getCurrentBoard() {
    return gameState.getCurrentBoard();
  }

  @Override
  public Color getCurrentTurn() {
    return gameState.getCurrentTurn();
  }

  @Override
  public GameState movePiece(Movement movement) {
    return gameState.movePiece(movement);
  }

  @Override
  public TurnValidator getTurnManager() {
    return gameState.getTurnManager();
  }

  @Override
  public List<Validator> getListPreConditions() {
    return gameState.getListPreConditions();
  }

  @Override
  public List<PostConditionValidator> getListPostConditions() {
    return gameState.getListPostConditions();
  }

  @Override
  public WinCondition getWinCondition() {
    return gameState.getWinCondition();
  }

  @Override
  public GameState gameState() {
    return this;
  }

  @Override
  public GameStateType getGameStateType() {
    return GameStateType.INVALID_MOVE;
  }

  @Override
  public GameState undo() {
    return null;
  }

  @Override
  public GameState redo() {
    return null;
  }

  @Override
  public boolean canUndo() {
    return false;
  }

  @Override
  public boolean canRedo() {
    return false;
  }
}

package edu.austral.dissis.chess.engine.main.common.game;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.WinCondition;
import java.util.List;

public class FinishGameStateImpl implements GameState {

  private final List<Board> boards;
  private final WinCondition winCondition;
  private final TurnValidator turnManager;
  private final List<Validator> preConditions;
  private final List<PostConditionValidator> postConditions;
  private final GameStateType gameStateType;

  public FinishGameStateImpl(
      List<Board> boards,
      WinCondition winCondition,
      TurnValidator turnManager,
      List<Validator> preConditions,
      List<PostConditionValidator> postConditions,
      GameStateType gameStateType) {
    this.boards = boards;
    this.winCondition = winCondition;
    this.turnManager = turnManager;
    this.preConditions = preConditions;
    this.postConditions = postConditions;
    this.gameStateType = gameStateType;
  }

  @Override
  public List<Board> getBoards() {
    return boards;
  }

  @Override
  public Board getCurrentBoard() {
    return boards.getLast();
  }

  @Override
  public Color getCurrentTurn() {
    return turnManager.getTurn();
  }

  @Override
  public GameState movePiece(Movement movement) {
    return this;
  }

  @Override
  public TurnValidator getTurnManager() {
    return turnManager;
  }

  @Override
  public List<Validator> getListPreConditions() {
    return preConditions;
  }

  @Override
  public List<PostConditionValidator> getListPostConditions() {
    return postConditions;
  }

  @Override
  public WinCondition getWinCondition() {
    return winCondition;
  }

  @Override
  public GameState gameState() {
    return this;
  }

  @Override
  public GameStateType getGameStateType() {
    return gameStateType;
  }

  @Override
  public GameState undo() {
    throw new UnsupportedOperationException("Undo not available in finished game state");
  }

  @Override
  public GameState redo() {
    throw new UnsupportedOperationException("Redo not available in finished game state");
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

package edu.austral.dissis.chess.engine.main.common.game;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.WinCondition;
import java.util.List;

public interface GameState {

  List<Board> getBoards();

  Board getCurrentBoard();

  Color getCurrentTurn();

  GameState movePiece(Movement movement);

  TurnValidator getTurnManager();

  List<Validator> getListPreConditions();

  List<PostConditionValidator> getListPostConditions();

  WinCondition getWinCondition();

  GameState gameState();

  GameStateType getGameStateType();

  GameState undo();

  GameState redo();

  boolean canUndo();

  boolean canRedo();
}

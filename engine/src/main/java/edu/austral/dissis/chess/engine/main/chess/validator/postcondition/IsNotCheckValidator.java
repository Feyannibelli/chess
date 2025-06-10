package edu.austral.dissis.chess.engine.main.chess.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameStateType;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.ArrayList;
import java.util.List;

public class IsNotCheckValidator implements Validator {
  private final CheckValidator checkValidator;

  public IsNotCheckValidator() {
    checkValidator = new CheckValidator();
  }

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    GameState newGameState = simulateMove(movement, gameState);
    Color kingColor = gameState.getCurrentTurn();
    if (checkValidator.inCheck(newGameState, kingColor)) {
      return new ValidatorResponse.ValidatorResultInvalid("Check! Don't move!");
    } else {
      return new ValidatorResponse.ValidatorResultValid("You are not in check");
    }
  }

  private GameState simulateMove(Movement movement, GameState gameState) {
    Board newBoard = gameState.getCurrentBoard().update(movement);
    List<Board> newBoards = new ArrayList<>(gameState.getBoards());
    newBoards.add(newBoard);
    return new GameStateImpl(
        newBoards,
        gameState.getWinCondition(),
        gameState.getTurnManager(),
        gameState.getListPreConditions(),
        gameState.getListPostConditions(),
        GameStateType.NORMAL);
  }
}

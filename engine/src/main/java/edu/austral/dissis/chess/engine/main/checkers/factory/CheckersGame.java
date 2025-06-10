package edu.austral.dissis.chess.engine.main.checkers.factory;

import edu.austral.dissis.chess.engine.main.checkers.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.checkers.validator.postcondition.HasEatenValidator;
import edu.austral.dissis.chess.engine.main.checkers.validator.turn.CheckersTurnManager;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameStateType;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PromotionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.ExtinctionCondition;
import java.util.List;

public class CheckersGame {

  public static GameState createCheckersNormalGame() {
    Board board = CheckersBoard.createCheckersBoard();
    return new GameStateImpl(
        List.of(board),
        new ExtinctionCondition(),
        new CheckersTurnManager(Color.WHITE, List.of()),
        List.of(),
        List.of(new HasEatenValidator(), new PromotionValidator(new QueenInitializer())),
        GameStateType.NORMAL);
  }
}

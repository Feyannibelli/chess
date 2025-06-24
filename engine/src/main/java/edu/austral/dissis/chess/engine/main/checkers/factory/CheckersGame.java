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

    // Inicializar el turno con BLACK y sin movimientos forzados al inicio
    CheckersTurnManager initialTurnManager = new CheckersTurnManager(Color.BLACK, List.of());

    return new GameStateImpl(
        List.of(board),
        new ExtinctionCondition(),
        initialTurnManager,
        List.of(),
        List.of(new HasEatenValidator(), new PromotionValidator(new QueenInitializer())),
        GameStateType.NORMAL);
  }
}

package edu.austral.dissis.chess.engine.main.chess.factory;

import edu.austral.dissis.chess.engine.main.chess.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.chess.validator.postcondition.CastleLeftPostCondition;
import edu.austral.dissis.chess.engine.main.chess.validator.postcondition.CastleRightPostCondition;
import edu.austral.dissis.chess.engine.main.chess.validator.postcondition.IsNotCheckValidator;
import edu.austral.dissis.chess.engine.main.chess.validator.turn.ChessTurnValidator;
import edu.austral.dissis.chess.engine.main.chess.validator.wincondition.CheckMateValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameStateType;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PromotionValidator;
import java.util.List;

public class ChessGame {

  public static GameState createChessNormalGame() {
    Board board = ChessBoard.createClassicChessBoard();
    return new GameStateImpl(
        List.of(board),
        new CheckMateValidator(),
        new ChessTurnValidator(Color.WHITE),
        List.of(new IsNotCheckValidator()),
        List.of(
            new PromotionValidator(new QueenInitializer()),
            new CastleRightPostCondition(),
            new CastleLeftPostCondition()),
        GameStateType.NORMAL);
  }
}

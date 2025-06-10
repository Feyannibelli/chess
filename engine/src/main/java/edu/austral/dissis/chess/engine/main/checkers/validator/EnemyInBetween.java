package edu.austral.dissis.chess.engine.main.checkers.validator;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// esta clase valida si hay un enemigo en medio de un movimiento
public class EnemyInBetween implements Validator {

  @Override
  public ValidatorResponse validate(Movement movement, GameState gameState) {
    Position middlePosition = getMiddlePosition(movement);
    Piece middlePiece = gameState.getCurrentBoard().getPieceByPosition(middlePosition);

    if (isEnemy(middlePiece, gameState.getCurrentTurn())) {
      return new ValidatorResponse.ValidatorResultValid("Es enemigo");
    } else {
      return new ValidatorResponse.ValidatorResultInvalid("no es enemigo");
    }
  }

  private Position getMiddlePosition(Movement move) {
    int midRow = (move.from().row() + move.to().row()) / 2;
    int midColumn = (move.from().column() + move.to().column()) / 2;
    return new Position(midRow, midColumn);
  }

  private boolean isEnemy(Piece piece, Color currentTurn) {
    return piece != null && piece.getColor() != currentTurn;
  }
}

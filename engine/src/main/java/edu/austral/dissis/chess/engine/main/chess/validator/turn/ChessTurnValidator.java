package edu.austral.dissis.chess.engine.main.chess.validator.turn;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

public class ChessTurnValidator implements TurnValidator {
  private Color current;

  public ChessTurnValidator(Color current) {
    this.current = current;
  }

  @Override
  public Color getTurn() {
    return current;
  }

  @Override
  public TurnValidator nextTurn(GameState gameState) {
    Color nextColor = (current == Color.WHITE) ? Color.BLACK : Color.WHITE;
    return new ChessTurnValidator(nextColor);
  }

  @Override
  public ValidatorResponse validateTurn(Movement movement, GameState gameState) {
    Piece pieceToMove = getPiece(movement, gameState);
    if (pieceToMove != null && pieceToMove.getColor() == this.current) {
      return new ValidatorResponse.ValidatorResultValid("Es tu turno.");
    }
    return new ValidatorResponse.ValidatorResultInvalid("No es tu turno.");
  }

  private Piece getPiece(Movement movement, GameState gameState) {
    return gameState.getCurrentBoard().getPieceByPosition(movement.from());
  }

  public void setCurrentTurn(Color color) {
    this.current = color;
  }
}

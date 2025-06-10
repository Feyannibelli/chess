package edu.austral.dissis.chess.engine.main.common.piece;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;

// pieza generica
public class Piece {

  private final String id;
  private final Color color;
  private final PieceType type;
  private final Validator validator;
  private final int moveCounter;

  public Piece(String id, Color color, PieceType type, Validator validator, int moveCounter) {
    this.id = id;
    this.color = color;
    this.type = type;
    this.validator = validator;
    this.moveCounter = moveCounter;
  }

  public Piece(String id, Color color, PieceType type, Validator validator) {
    this(id, color, type, validator, 0);
  }

  public Piece incrementMoveCounter() {
    return new Piece(id, color, type, validator, moveCounter + 1);
  }

  public int getMoveCounter() {
    return moveCounter;
  }

  public ValidatorResponse validateMove(Movement movement, GameState gameState) {
    return validator.validate(movement, gameState);
  }

  // utilizo id par identificar una pieza
  public String getId() {
    return id;
  }

  public Color getColor() {
    return color;
  }

  public PieceType getType() {
    return type;
  }

  public Validator getValidator() {
    return validator;
  }

  public Piece withType(PieceType type) {
    return new Piece(id, color, type, this.validator, moveCounter);
  }

  public Piece withValidator(Validator validator) {
    return new Piece(id, color, type, validator, moveCounter);
  }

  public boolean hasMoved() {
    return moveCounter > 0;
  }
}

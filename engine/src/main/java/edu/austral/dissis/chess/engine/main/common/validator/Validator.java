package edu.austral.dissis.chess.engine.main.common.validator;

import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;

// para ser implementado por las reglas y movimientos que necesiten validacion
public interface Validator {
  ValidatorResponse validate(Movement movement, GameState gameState);
}

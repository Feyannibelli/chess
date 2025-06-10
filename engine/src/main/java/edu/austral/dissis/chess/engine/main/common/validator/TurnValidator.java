package edu.austral.dissis.chess.engine.main.common.validator;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;

// implemnetacion para el chess y checkers
public interface TurnValidator {

  Color getTurn();

  TurnValidator nextTurn(GameState gameState);

  // validacion si es mi turnp
  ValidatorResponse validateTurn(Movement movement, GameState gameState);
}

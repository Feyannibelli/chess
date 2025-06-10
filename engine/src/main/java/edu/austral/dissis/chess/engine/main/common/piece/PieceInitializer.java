package edu.austral.dissis.chess.engine.main.common.piece;

import edu.austral.dissis.chess.engine.main.common.Color;

public interface PieceInitializer {

  Piece initialize(Color color);

  Piece initialize(Color color, String id);
}

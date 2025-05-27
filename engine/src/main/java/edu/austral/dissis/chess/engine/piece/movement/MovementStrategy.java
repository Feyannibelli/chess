package edu.austral.dissis.chess.engine.piece.movement;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import java.util.List;

public interface MovementStrategy {
    List<Position> getValidMoves(Position from, Board board, Color color);
}
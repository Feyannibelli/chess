package edu.austral.dissis.chess.engine.main.common.board;

import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import java.util.List;
import java.util.Map;

public interface Board {

  int getWidth();

  int getHeight();

  Piece getPieceByPosition(Position position);

  Position getPositionByPiece(Piece piece);

  Map<Position, Piece> getPiecesPositions();

  Board update(Movement movement);

  List<Position> getOccupiedPositions();

  Board removePieceByPosition(Position position);

  List<Piece> getPieces();

  Board updatePieceByPosition(Position position, Piece piece);
}

package edu.austral.dissis.chess.engine.main.common.board;

import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

// esta clase es la representacion de un tablero del juego, contiene largo ancho y piezas
public class BoardImpl implements Board {
  private final int width;
  private final int height;
  private final Map<Position, Piece> piecePositions;

  public BoardImpl(int width, int height, Map<Position, Piece> piecePositions) {
    this.width = width;
    this.height = height;
    this.piecePositions = new HashMap<>(piecePositions);
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  @NotNull
  public Piece getPieceByPosition(@NotNull Position position) {
    return piecePositions.get(position);
  }

  @Override
  @NotNull
  public Position getPositionByPiece(@NotNull Piece piece) {
    for (Position position : piecePositions.keySet()) {
      if (piecePositions.get(position).getId().equals(piece.getId())) {
        return position;
      }
    }
    throw new NoSuchElementException("Piece not found on the board");
  }

  @Override
  public Map<Position, Piece> getPiecesPositions() {
    return piecePositions;
  }

  @Override
  public Board update(Movement movement) {
    if (isPositionWithinBounds(movement.from()) || isPositionWithinBounds(movement.to())) {
      throw new IllegalArgumentException("Position out of bounds");
    }

    Map<Position, Piece> newPiecePositions = new HashMap<>(piecePositions);
    Piece piece = piecePositions.get(movement.from());
    newPiecePositions.remove(movement.from());
    newPiecePositions.put(movement.to(), piece.incrementMoveCounter());

    return new BoardImpl(width, height, newPiecePositions);
  }

  private boolean isPositionWithinBounds(Position position) {
    return position.row() < 0
        || position.row() >= height
        || position.column() < 0
        || position.column() >= width;
  }

  @Override
  public List<Position> getOccupiedPositions() {
    return piecePositions.keySet().stream().toList();
  }

  @Override
  public Board removePieceByPosition(Position position) {
    Map<Position, Piece> newPiecePositions = new HashMap<>(piecePositions);
    if (!newPiecePositions.containsKey(position)) {
      throw new IllegalArgumentException("No piece at the specified position.");
    }
    newPiecePositions.remove(position);
    return new BoardImpl(width, height, newPiecePositions);
  }

  @Override
  public List<Piece> getPieces() {
    return piecePositions.values().stream().toList();
  }

  @Override
  public Board updatePieceByPosition(Position position, Piece piece) {
    Map<Position, Piece> newPiecePositions = new HashMap<>(piecePositions);
    newPiecePositions.put(position, piece);
    return new BoardImpl(width, height, newPiecePositions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        Piece piece = getPieceByPosition(new Position(row, col));
        if (piece != null) {
          sb.append(piece.getColor().toString().charAt(0))
              .append(piece.getType().toString().charAt(0))
              .append(" ");
        } else {
          sb.append(" - ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}

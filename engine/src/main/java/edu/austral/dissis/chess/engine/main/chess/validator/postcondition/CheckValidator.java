package edu.austral.dissis.chess.engine.main.chess.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckValidator {

  public boolean inCheck(GameState gameState, Color kingColor) {
    Board board = gameState.getCurrentBoard();
    Position kingPosition = getKingPosition(board, kingColor);
    if (kingPosition == null) {
      throw new NoSuchElementException("King is not on the board.");
    }
    List<Position> enemyPositions = getEnemyPositions(board, kingColor);
    for (Position enemyPosition : enemyPositions) {
      if (pieceAttacksKing(gameState, enemyPosition, kingPosition)) {
        return true;
      }
    }
    return false;
  }

  private Position getKingPosition(Board board, Color kingColor) {
    for (Position coordinate : board.getOccupiedPositions()) {
      Piece piece = board.getPieceByPosition(coordinate);
      if (isKing(piece) && matchesColor(piece, kingColor)) {
        return coordinate;
      }
    }
    return null;
  }

  private boolean isKing(Piece piece) {
    return piece != null && piece.getType() == PieceType.KING;
  }

  private boolean matchesColor(Piece piece, Color color) {
    return piece != null && piece.getColor() == color;
  }

  private List<Position> getEnemyPositions(Board board, Color kingColor) {
    List<Position> enemyPositions = new ArrayList<>();
    for (Position coordinate : board.getOccupiedPositions()) {
      Piece piece = board.getPieceByPosition(coordinate);
      if (piece != null && piece.getColor() != kingColor) {
        enemyPositions.add(coordinate);
      }
    }
    return enemyPositions;
  }

  private boolean pieceAttacksKing(
      GameState gameState, Position enemyPosition, Position kingPosition) {
    Piece enemy = gameState.getCurrentBoard().getPieceByPosition(enemyPosition);
    if (enemy == null) {
      return false;
    }
    Movement move = new Movement(enemyPosition, kingPosition);
    ValidatorResponse response = enemy.validateMove(move, gameState);
    return response instanceof ValidatorResponse.ValidatorResultValid;
  }
}

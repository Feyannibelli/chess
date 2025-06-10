package edu.austral.dissis.chess.engine.main.checkers.validator.postcondition;

import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionResult;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultInvalid;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultValid;
import java.util.List;
import java.util.NoSuchElementException;

public class HasEatenValidator implements PostConditionValidator {

  @Override
  public PostConditionResult validate(GameState gameState, Board updatedBoard) {
    Board previousBoard = gameState.getCurrentBoard();
    Movement latestMovement = getLatestMovement(previousBoard, updatedBoard);

    if (!isEatingMovement(latestMovement)) {
      return new ResultInvalid("No se ha comido ninguna pieza.");
    }

    Board board = updateEatenBoard(updatedBoard, latestMovement);

    return new ResultValid(board);
  }

  // agarra el ultimo movimiento que se hizo comparando los tableros antes de mover y después de
  // mover
  private Movement getLatestMovement(Board previousBoard, Board updatedBoard) {
    List<Position> previousBoardPieces = previousBoard.getOccupiedPositions();
    List<Position> updatedBoardPieces = updatedBoard.getOccupiedPositions();

    Position positionTo = getPositionTo(previousBoardPieces, updatedBoardPieces);
    Piece piece = updatedBoard.getPieceByPosition(positionTo);

    Position positionFrom = previousBoard.getPositionByPiece(piece);

    return new Movement(positionFrom, positionTo);
  }

  private Position getPositionTo(
      List<Position> previousBoardPieces, List<Position> updatedBoardPieces) {
    for (Position position : updatedBoardPieces) {
      if (!previousBoardPieces.contains(position)) {
        return position;
      }
    }
    throw new NoSuchElementException(
        "No se ha encontrado la posición de la pieza que se ha comido.");
  }

  private boolean isEatingMovement(Movement latestMovement) {
    return Math.abs(latestMovement.from().row() - latestMovement.to().row()) == 2;
  }

  private Board updateEatenBoard(Board updatedBoard, Movement latestMovement) {
    Position positionToRemove = getEatenPosition(latestMovement);
    return updatedBoard.removePieceByPosition(positionToRemove);
  }

  private Position getEatenPosition(Movement latestMovement) {
    // para agarrar la posicion de la pieza que hay que sacar
    int row = latestMovement.from().row() + getRowSense(latestMovement);
    int column = latestMovement.from().column() + getColumnSense(latestMovement);
    return new Position(row, column);
  }

  private int getRowSense(Movement latestMovement) {
    if (latestMovement.from().row() < latestMovement.to().row()) {
      return 1;
    } else if (latestMovement.from().row() > latestMovement.to().row()) {
      return -1;
    } else {
      return 0;
    }
  }

  private int getColumnSense(Movement latestMovement) {
    if (latestMovement.from().column() < latestMovement.to().column()) {
      return 1;
    } else if (latestMovement.from().column() > latestMovement.to().column()) {
      return -1;
    } else {
      return 0;
    }
  }
}

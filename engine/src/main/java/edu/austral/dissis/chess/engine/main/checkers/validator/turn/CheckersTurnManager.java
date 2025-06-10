package edu.austral.dissis.chess.engine.main.checkers.validator.turn;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.ArrayList;
import java.util.List;

public class CheckersTurnManager implements TurnValidator {
  private final Color color;
  private final List<Movement> possibleMoves;

  public CheckersTurnManager(Color color, List<Movement> possibleMoves) {
    this.color = color;
    this.possibleMoves = possibleMoves;
  }

  @Override
  public Color getTurn() {
    return color;
  }

  @Override
  public TurnValidator nextTurn(GameState gameState) {
    if (canCapture(gameState) && hasEatenMove(gameState)) {
      return nextTurnWithCapture(gameState);
    } else {
      return nextTurnWithoutCapture();
    }
  }

  @Override
  public ValidatorResponse validateTurn(Movement movement, GameState gameState) {
    Piece pieceToMove = getPiece(movement, gameState);

    if (isNotAvailableMove(movement)) {
      return new ValidatorResponse.ValidatorResultInvalid(
          "Mover una pieza que pueda capturar y que haya capturado.");
    }
    if (pieceToMove != null) {
      if (isSameColor(pieceToMove.getColor(), color)) {
        return new ValidatorResponse.ValidatorResultValid("Es tu turno");
      }
    }
    return new ValidatorResponse.ValidatorResultInvalid("No es tu turno.");
  }

  private boolean hasEatenMove(GameState gameState) {
    Board board = gameState.getCurrentBoard();
    if (hasMove(gameState)) {
      return false;
    }
    Board previousBoard = getPreviousBoard(gameState);
    Movement movement = getLatestMovement(previousBoard, board);
    Piece previousPiece = previousBoard.getPieceByPosition(movement.from());
    if (previousPiece == null) {
      return false;
    }
    Color previousColor = previousPiece.getColor();
    return previousBoardHasMorePieces(previousBoard, board) && isSameColor(previousColor, color);
  }

  private boolean hasMove(GameState gameState) {
    return gameState.getBoards().size() < 2;
  }

  private Board getPreviousBoard(GameState gameState) {
    return gameState.getBoards().get(gameState.getBoards().size() - 2);
  }

  private boolean isSameColor(Color previousColor, Color color) {
    return previousColor == color;
  }

  private boolean previousBoardHasMorePieces(Board previousBoard, Board board) {
    return previousBoard.getOccupiedPositions().size() > board.getOccupiedPositions().size();
  }

  private TurnValidator nextTurnWithCapture(GameState gameState) { // this method
    Board previousBoard = getPreviousBoard(gameState);
    Movement movement = getLatestMovement(previousBoard, gameState.getCurrentBoard());
    List<Movement> newPossibleMoves = getPossibleEatenMovesByPosition(movement.to(), gameState);

    if (!newPossibleMoves.isEmpty()) {
      return new CheckersTurnManager(color, newPossibleMoves);
    } else {
      return nextTurnWithoutCapture();
    }
  }

  private TurnValidator nextTurnWithoutCapture() {
    if (color == Color.WHITE) {
      return new CheckersTurnManager(Color.BLACK, List.of());
    } else {
      return new CheckersTurnManager(Color.WHITE, List.of());
    }
  }

  private boolean canCapture(GameState gameState) {
    Board board = gameState.getCurrentBoard();
    if (gameState.getBoards().size() < 2) {
      return false;
    }
    Board previousBoard = gameState.getBoards().get(gameState.getBoards().size() - 2);
    Movement movement = getLatestMovement(previousBoard, board);
    Piece piece = board.getPieceByPosition(movement.to());
    if (piece == null) {
      return false;
    }
    List<Movement> possibleEatenMoves = getPossibleEatenMovesByPosition(movement.to(), gameState);

    return !possibleEatenMoves.isEmpty();
  }

  private List<Movement> getPossibleEatenMovesByPosition(Position pos, GameState gameState) {
    List<Movement> possibleEatenMoves = new ArrayList<>();
    List<Movement> possibleMoves = getPossibleMovesByPosition(pos, gameState);
    for (Movement move : possibleMoves) {
      if (isEatingMovement(move, gameState)) {
        possibleEatenMoves.add(move);
      }
    }
    return possibleEatenMoves;
  }

  private boolean isNotAvailableMove(Movement movement) {
    return !possibleMoves.isEmpty() && !possibleMoves.contains(movement);
  }

  private Piece getPiece(Movement movement, GameState gameState) {
    return gameState.getCurrentBoard().getPieceByPosition(movement.from());
  }

  private Movement getLatestMovement(Board previousBoard, Board updatedBoard) {
    List<Position> previousBoardPieces = previousBoard.getOccupiedPositions();
    List<Position> updatedBoardPieces = updatedBoard.getOccupiedPositions();

    Position positionTo = findDestination(previousBoardPieces, updatedBoardPieces);
    Piece piece = updatedBoard.getPieceByPosition(positionTo);

    Position positionFrom = previousBoard.getPositionByPiece(piece);

    if (positionFrom == null) {
      throw new IllegalStateException(
          "The piece is not found at the expected position on the previous board.");
    }

    return new Movement(positionFrom, positionTo);
  }

  private Position findDestination(
      List<Position> previousBoardPieces, List<Position> updatedBoardPieces) {
    return updatedBoardPieces.stream()
        .filter(position -> !previousBoardPieces.contains(position))
        .findFirst()
        .orElse(null);
  }

  private boolean isEatingMovement(Movement move, GameState gameState) {
    Board board = gameState.getCurrentBoard();
    Piece piece = board.getPieceByPosition(move.from());

    return piece.validateMove(move, gameState) instanceof ValidatorResponse.ValidatorResultValid;
  }

  private List<Movement> getPossibleMovesByPosition(Position position, GameState gameState) {
    List<Movement> possibleMoves = new ArrayList<>();
    int row = position.row();
    int column = position.column();
    List<Position> possiblePositions = getPossiblePositions(row, column);
    int rows = gameState.getCurrentBoard().getHeight();
    int columns = gameState.getCurrentBoard().getWidth();
    List<Position> validatedPossiblePositions = findValidPosition(possiblePositions, rows, columns);
    for (Position posiblePosition : validatedPossiblePositions) {
      possibleMoves.add(new Movement(position, posiblePosition));
    }
    return possibleMoves;
  }

  private List<Position> findValidPosition(List<Position> posiblePositions, int rows, int columns) {
    List<Position> validPositions = new ArrayList<>();
    for (Position pos : posiblePositions) {
      if (pos.row() >= 0 && pos.row() < rows && pos.column() >= 0 && pos.column() < columns) {
        validPositions.add(pos);
      }
    }
    return validPositions;
  }

  private List<Position> getPossiblePositions(int row, int column) {
    List<Position> possiblePositions = new ArrayList<>();
    possiblePositions.add(new Position(row + 2, column + 2));
    possiblePositions.add(new Position(row + 2, column - 2));
    possiblePositions.add(new Position(row - 2, column + 2));
    possiblePositions.add(new Position(row - 2, column - 2));
    return possiblePositions;
  }
}

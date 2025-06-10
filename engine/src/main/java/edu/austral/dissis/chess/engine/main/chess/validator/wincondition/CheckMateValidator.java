package edu.austral.dissis.chess.engine.main.chess.validator.wincondition;

import edu.austral.dissis.chess.engine.main.chess.validator.postcondition.CheckValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.GameStateType;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.WinCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckMateValidator implements WinCondition {

  @Override
  public boolean isWin(GameState gameState) {
    Color kingColor = gameState.getTurnManager().nextTurn(gameState).getTurn();
    Board board1 = gameState.getCurrentBoard();
    List<Position> positionsOfAlliedKingPieces = getPositionsByColor(board1, kingColor);
    List<Movement> possibleMoves = getPossibleMoves(positionsOfAlliedKingPieces, gameState);
    for (Movement pos : possibleMoves) {
      if (moveIsNotCheck(pos, gameState, kingColor)) {
        return false;
      }
    }
    if (gameState.getCurrentTurn() == Color.WHITE) {
      GameStateType gameStateType = GameStateType.WHITE_CHECKMATE;
    } else {
      GameStateType gameStateType = GameStateType.BLACK_CHECKMATE;
    }
    return true;
  }

  @Override
  public boolean isDraw(GameState gameState) {
    List<Piece> pieces = gameState.getCurrentBoard().getPieces();
    return pieces.size() == 1
        && pieces.stream().allMatch(piece -> piece.getType() == PieceType.KING);
  }

  private List<Position> getPositionsByColor(Board board, Color color) {
    List<Position> occupiedPositions = new ArrayList<>();
    for (Position position : board.getOccupiedPositions()) {
      Piece piece = board.getPieceByPosition(position);
      if (piece != null && piece.getColor() == color) {
        occupiedPositions.add(position);
      }
    }
    return occupiedPositions;
  }

  private List<Movement> getPossibleMoves(List<Position> positions, GameState gameState) {
    List<Movement> possibleMoves = new ArrayList<>();
    for (Position pos : positions) {
      possibleMoves.addAll(getPieceValidMoves(pos, gameState));
    }
    return possibleMoves;
  }

  private List<Movement> getPieceValidMoves(Position occupiedPosition, GameState gameState) {
    Piece piece = gameState.getCurrentBoard().getPieceByPosition(occupiedPosition);
    if (piece == null) {
      throw new NoSuchElementException("No existing piece.");
    }
    List<Movement> validMoves = new ArrayList<>();
    for (int row = 0; row < gameState.getCurrentBoard().getWidth(); row++) {
      for (int column = 0; column < gameState.getCurrentBoard().getHeight(); column++) {
        Position positionTo = new Position(row, column);
        Movement movement = new Movement(occupiedPosition, positionTo);
        ValidatorResponse validator = piece.validateMove(movement, gameState);
        if (validator instanceof ValidatorResponse.ValidatorResultValid) {
          validMoves.add(movement);
        }
      }
    }
    return validMoves;
  }

  private boolean moveIsNotCheck(Movement movement, GameState gameState, Color kingColor) {
    GameState newGameState = simulateMove(movement, gameState);
    CheckValidator checkValidator = new CheckValidator();
    return !checkValidator.inCheck(newGameState, kingColor);
  }

  private GameState simulateMove(Movement movement, GameState gameState) {
    Board newBoard = gameState.getCurrentBoard().update(movement);
    List<Board> newBoards = new ArrayList<>(gameState.getBoards());
    newBoards.add(newBoard);
    GameStateType gameStateType = GameStateType.NORMAL;
    return new GameStateImpl(
        newBoards,
        gameState.getWinCondition(),
        gameState.getTurnManager(),
        gameState.getListPreConditions(),
        gameState.getListPostConditions(),
        gameStateType);
  }
}

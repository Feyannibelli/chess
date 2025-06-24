package edu.austral.dissis.chess.engine.main.checkers.validator;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.List;
import java.util.Optional;

public final class MandatoryCaptureValidator implements Validator {

  @Override
  public ValidatorResponse validate(final Movement movement, final GameState gameState) {
    final Color currentTurn = gameState.getCurrentTurn();
    final List<Movement> availableCaptures = findAllAvailableCaptures(gameState, currentTurn);

    return validateMandatoryCapture(movement, availableCaptures);
  }

  private ValidatorResponse validateMandatoryCapture(
      final Movement movement, final List<Movement> availableCaptures) {
    if (availableCaptures.isEmpty()) {
      return new ValidatorResponse.ValidatorResultValid("No hay capturas obligatorias");
    }

    if (isCaptureMovement(movement)) {
      return new ValidatorResponse.ValidatorResultValid("Captura obligatoria realizada");
    }

    return new ValidatorResponse.ValidatorResultInvalid("Debes realizar una captura disponible");
  }

  private List<Movement> findAllAvailableCaptures(
      final GameState gameState, final Color currentTurn) {
    final List<Position> playerPieces = getPlayerPieces(gameState.getCurrentBoard(), currentTurn);

    return playerPieces.stream()
        .flatMap(position -> getPossibleCaptures(position, gameState).stream())
        .filter(capture -> isValidCapture(capture, gameState))
        .toList();
  }

  private List<Position> getPlayerPieces(final Board board, final Color playerColor) {
    return board.getOccupiedPositions().stream()
        .filter(position -> belongsToPlayer(board, position, playerColor))
        .toList();
  }

  private boolean belongsToPlayer(
      final Board board, final Position position, final Color playerColor) {
    return Optional.ofNullable(board.getPieceByPosition(position))
        .map(piece -> piece.getColor() == playerColor)
        .orElse(false);
  }

  private List<Movement> getPossibleCaptures(final Position from, final GameState gameState) {
    return generateCapturePositions(from).stream()
        .filter(to -> isValidPosition(to, gameState.getCurrentBoard()))
        .map(to -> new Movement(from, to))
        .toList();
  }

  private List<Position> generateCapturePositions(final Position from) {
    final int row = from.row();
    final int column = from.column();

    return List.of(
        new Position(row + 2, column + 2),
        new Position(row + 2, column - 2),
        new Position(row - 2, column + 2),
        new Position(row - 2, column - 2));
  }

  private boolean isValidPosition(final Position position, final Board board) {
    return position.row() >= 0
        && position.row() < board.getHeight()
        && position.column() >= 0
        && position.column() < board.getWidth();
  }

  private boolean isValidCapture(final Movement capture, final GameState gameState) {
    return Optional.ofNullable(gameState.getCurrentBoard().getPieceByPosition(capture.from()))
        .map(piece -> piece.validateMove(capture, gameState))
        .map(response -> response instanceof ValidatorResponse.ValidatorResultValid)
        .orElse(false);
  }

  private boolean isCaptureMovement(final Movement movement) {
    final int rowDistance = Math.abs(movement.from().row() - movement.to().row());
    final int columnDistance = Math.abs(movement.from().column() - movement.to().column());

    return rowDistance == 2 && columnDistance == 2;
  }
}

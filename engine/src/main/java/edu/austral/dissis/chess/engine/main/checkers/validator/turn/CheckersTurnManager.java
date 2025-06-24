package edu.austral.dissis.chess.engine.main.checkers.validator.turn;

import edu.austral.dissis.chess.engine.main.checkers.validator.MandatoryCaptureValidator;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import java.util.List;
import java.util.Optional;

public final class CheckersTurnManager implements TurnValidator {
  private final Color color;
  private final List<Movement> restrictedMoves;
  private final MandatoryCaptureValidator mandatoryCaptureValidator;

  public CheckersTurnManager(final Color color, final List<Movement> restrictedMoves) {
    this.color = color;
    this.restrictedMoves = List.copyOf(restrictedMoves);
    this.mandatoryCaptureValidator = new MandatoryCaptureValidator();
  }

  @Override
  public Color getTurn() {
    return color;
  }

  @Override
  public TurnValidator nextTurn(final GameState gameState) {
    if (shouldContinueCapturing(gameState)) {
      return createContinuingCaptureManager(gameState);
    }
    return createNextPlayerManager();
  }

  @Override
  public ValidatorResponse validateTurn(final Movement movement, final GameState gameState) {
    final ValidatorResponse pieceValidation = validatePieceOwnership(movement, gameState);
    if (pieceValidation instanceof ValidatorResponse.ValidatorResultInvalid) {
      return pieceValidation;
    }

    final ValidatorResponse restrictionValidation = validateMoveRestrictions(movement);
    if (restrictionValidation instanceof ValidatorResponse.ValidatorResultInvalid) {
      return restrictionValidation;
    }

    return mandatoryCaptureValidator.validate(movement, gameState);
  }

  private ValidatorResponse validatePieceOwnership(
      final Movement movement, final GameState gameState) {
    return Optional.ofNullable(getPieceAt(movement.from(), gameState))
        .map(piece -> validatePieceColor(piece))
        .orElse(
            new ValidatorResponse.ValidatorResultInvalid("No hay pieza en la posición inicial"));
  }

  private ValidatorResponse validatePieceColor(final Piece piece) {
    if (piece.getColor() == color) {
      return new ValidatorResponse.ValidatorResultValid("Pieza válida del jugador actual");
    }
    return new ValidatorResponse.ValidatorResultInvalid("No es tu turno");
  }

  private ValidatorResponse validateMoveRestrictions(final Movement movement) {
    if (hasRestrictedMoves() && !isAllowedMove(movement)) {
      return new ValidatorResponse.ValidatorResultInvalid("Debes continuar capturando");
    }
    return new ValidatorResponse.ValidatorResultValid("Movimiento permitido");
  }

  private boolean shouldContinueCapturing(final GameState gameState) {
    return hasRecentCapture(gameState) && canContinueCapturing(gameState);
  }

  private boolean hasRecentCapture(final GameState gameState) {
    if (gameState.getBoards().size() < 2) {
      return false;
    }

    final Board current = gameState.getCurrentBoard();
    final Board previous = getPreviousBoard(gameState);

    return hasFewerPieces(current, previous) && wasLastMoveByCurrentPlayer(gameState);
  }

  private boolean canContinueCapturing(final GameState gameState) {
    final Optional<Movement> lastMovement = getLastMovement(gameState);
    return lastMovement
        .map(movement -> findPossibleCaptures(movement.to(), gameState))
        .map(captures -> !captures.isEmpty())
        .orElse(false);
  }

  private TurnValidator createContinuingCaptureManager(final GameState gameState) {
    final List<Movement> continuingCaptures =
        getLastMovement(gameState)
            .map(movement -> findPossibleCaptures(movement.to(), gameState))
            .orElse(List.of());

    return new CheckersTurnManager(color, continuingCaptures);
  }

  private TurnValidator createNextPlayerManager() {
    final Color nextColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    return new CheckersTurnManager(nextColor, List.of());
  }

  private Piece getPieceAt(final Position position, final GameState gameState) {
    return gameState.getCurrentBoard().getPieceByPosition(position);
  }

  private boolean hasRestrictedMoves() {
    return !restrictedMoves.isEmpty();
  }

  private boolean isAllowedMove(final Movement movement) {
    return restrictedMoves.contains(movement);
  }

  private Board getPreviousBoard(final GameState gameState) {
    final List<Board> boards = gameState.getBoards();
    return boards.get(boards.size() - 2);
  }

  private boolean hasFewerPieces(final Board current, final Board previous) {
    return current.getOccupiedPositions().size() < previous.getOccupiedPositions().size();
  }

  private boolean wasLastMoveByCurrentPlayer(final GameState gameState) {
    return getLastMovement(gameState)
        .flatMap(
            movement ->
                Optional.ofNullable(
                    getPreviousBoard(gameState).getPieceByPosition(movement.from())))
        .map(piece -> piece.getColor() == color)
        .orElse(false);
  }

  private Optional<Movement> getLastMovement(final GameState gameState) {
    if (gameState.getBoards().size() < 2) {
      return Optional.empty();
    }

    final Board current = gameState.getCurrentBoard();
    final Board previous = getPreviousBoard(gameState);

    return findMovementBetweenBoards(previous, current);
  }

  private Optional<Movement> findMovementBetweenBoards(final Board previous, final Board current) {
    final Optional<Position> destination = findNewPosition(previous, current);

    return destination.flatMap(
        to -> {
          final Piece movedPiece = current.getPieceByPosition(to);
          final Position from = previous.getPositionByPiece(movedPiece);
          return Optional.ofNullable(from).map(f -> new Movement(f, to));
        });
  }

  private Optional<Position> findNewPosition(final Board previous, final Board current) {
    return current.getOccupiedPositions().stream()
        .filter(position -> !previous.getOccupiedPositions().contains(position))
        .findFirst();
  }

  private List<Movement> findPossibleCaptures(final Position from, final GameState gameState) {
    return generateCapturePositions(from).stream()
        .filter(to -> isValidPosition(to, gameState.getCurrentBoard()))
        .map(to -> new Movement(from, to))
        .filter(capture -> isValidCapture(capture, gameState))
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
}

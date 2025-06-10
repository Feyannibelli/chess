package edu.austral.dissis.chess.engine.main.common.game;

import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.validator.TurnValidator;
import edu.austral.dissis.chess.engine.main.common.validator.Validator;
import edu.austral.dissis.chess.engine.main.common.validator.ValidatorResponse;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionResult;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.PostConditionValidator;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultInvalid;
import edu.austral.dissis.chess.engine.main.common.validator.postcondition.ResultValid;
import edu.austral.dissis.chess.engine.main.common.validator.wincondition.WinCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameStateImpl implements GameState {
  private final List<Board> boards;
  private final WinCondition winCondition;
  private final TurnValidator turnManager;
  private final List<Validator> preConditions;
  private final List<PostConditionValidator> postConditions;
  private GameStateType gameStateType;
  private final Stack<GameState> undoStack;
  private final Stack<GameState> redoStack;

  public GameStateImpl(
      List<Board> boards,
      WinCondition winCondition,
      TurnValidator turnManager,
      List<Validator> preConditions,
      List<PostConditionValidator> postConditions,
      GameStateType gameStateType) {
    this.boards = boards;
    this.winCondition = winCondition;
    this.turnManager = turnManager;
    this.preConditions = preConditions;
    this.postConditions = postConditions;
    this.gameStateType = gameStateType;
    this.undoStack = new Stack<>();
    this.redoStack = new Stack<>();
  }

  public GameStateImpl(
      List<Board> boards,
      WinCondition winCondition,
      TurnValidator turnManager,
      List<Validator> preConditions,
      List<PostConditionValidator> postConditions,
      GameStateType gameStateType,
      Stack<GameState> undoStack,
      Stack<GameState> redoStack) {
    this.boards = boards;
    this.winCondition = winCondition;
    this.turnManager = turnManager;
    this.preConditions = preConditions;
    this.postConditions = postConditions;
    this.gameStateType = gameStateType;
    this.undoStack = undoStack;
    this.redoStack = redoStack;
  }

  @Override
  public List<Board> getBoards() {
    return boards;
  }

  @Override
  public Board getCurrentBoard() {
    return boards.getLast();
  }

  @Override
  public GameState movePiece(Movement movement) {
    System.out.println("Initial Board State:\n" + getCurrentBoard());
    System.out.println("Attempting to move piece from " + movement.from() + " to " + movement.to());

    // valida el turno del jugador actual
    ValidatorResponse turnResponse = validateTurn(movement);
    if (turnResponse instanceof ValidatorResponse.ValidatorResultInvalid) {
      return invalidMove((ValidatorResponse.ValidatorResultInvalid) turnResponse);
    }

    // valida los movimientos de las piezas en el tablero actual antes de realizar el movimiento
    ValidatorResponse pieceMoveResponse = validatePieceMove(movement);
    if (pieceMoveResponse instanceof ValidatorResponse.ValidatorResultInvalid) {
      return invalidMove((ValidatorResponse.ValidatorResultInvalid) pieceMoveResponse);
    }

    // validar las precondiciones antes de realizar el movimiento de la pieza en el tablero actual
    ValidatorResponse preConditionResponse = validatePreConditions(movement);
    if (preConditionResponse instanceof ValidatorResponse.ValidatorResultInvalid) {
      return invalidMove((ValidatorResponse.ValidatorResultInvalid) preConditionResponse);
    }

    this.undoStack.push(this);
    this.redoStack.clear();

    // validar las postCondiciones
    Board boardAfterMove = this.getCurrentBoard().update(movement);
    System.out.println("Board After Move Update:\n" + boardAfterMove);
    PostConditionResult postConditionResponse = validatePostConditions(boardAfterMove);

    GameState gamePostConditions =
        updateGameStateAfterPostConditions(postConditionResponse, boardAfterMove);
    System.out.println("Board After Post Conditions:\n" + gamePostConditions.getCurrentBoard());

    GameStateType gameState = this.getGameStateType();
    if (getWinCondition().isWin(gamePostConditions)) {
      if (gamePostConditions.getCurrentTurn() == Color.WHITE) {
        gameState = GameStateType.WHITE_CHECKMATE;
      } else if (gamePostConditions.getCurrentTurn() == Color.BLACK) {
        gameState = GameStateType.BLACK_CHECKMATE;
      }
      return finishedGame(gamePostConditions, gameState);
    } else if (getWinCondition().isDraw(gamePostConditions)) {
      gameState = GameStateType.DRAW;
      return finishedGame(gamePostConditions, gameState);
    }

    return new GameStateImpl(
        gamePostConditions.getBoards(),
        this.getWinCondition(),
        this.getTurnManager().nextTurn(gamePostConditions),
        this.getListPreConditions(),
        this.getListPostConditions(),
        gameState,
        this.undoStack,
        this.redoStack);
  }

  @Override
  public TurnValidator getTurnManager() {
    return turnManager;
  }

  @Override
  public List<Validator> getListPreConditions() {
    return preConditions;
  }

  @Override
  public List<PostConditionValidator> getListPostConditions() {
    return postConditions;
  }

  @Override
  public WinCondition getWinCondition() {
    return winCondition;
  }

  @Override
  public Color getCurrentTurn() {
    return turnManager.getTurn();
  }

  private ValidatorResponse validateTurn(Movement movement) {
    return turnManager.validateTurn(movement, this);
  }

  private GameState invalidMove(ValidatorResponse.ValidatorResultInvalid response) {
    return new InvalidMoveGameStateImpl(this, response.message());
  }

  private ValidatorResponse validatePreConditions(Movement movement) {
    for (Validator preCondition : getListPreConditions()) {
      ValidatorResponse res = preCondition.validate(movement, this);
      if (res instanceof ValidatorResponse.ValidatorResultInvalid) {
        return res;
      }
    }
    return new ValidatorResponse.ValidatorResultValid("OK");
  }

  private ValidatorResponse validatePieceMove(Movement movement) {
    Board board = getCurrentBoard();
    Piece piece = board.getPieceByPosition(movement.from());
    if (piece == null) {
      return new ValidatorResponse.ValidatorResultInvalid("No piece at this position.");
    }
    return piece.validateMove(movement, this);
  }

  private PostConditionResult validatePostConditions(Board board) {
    for (PostConditionValidator postCondition : getListPostConditions()) {
      PostConditionResult postConditionResponse = postCondition.validate(this, board);
      if (postConditionResponse instanceof ResultValid) {
        return postConditionResponse;
      }
    }
    return new ResultInvalid("All post conditions failed.");
  }

  private GameState updateGameStateAfterPostConditions(
      PostConditionResult postConditionResponse, Board boardAux) {
    if (postConditionResponse instanceof ResultValid) {
      Board board = postConditionResponse.getBoard();
      List<Board> newBoards = new ArrayList<>(this.getBoards());
      newBoards.add(board);
      return new GameStateImpl(
          newBoards,
          this.getWinCondition(),
          this.getTurnManager(),
          this.getListPreConditions(),
          this.getListPostConditions(),
          this.gameStateType = GameStateType.NORMAL,
          this.undoStack,
          this.redoStack);
    } else {
      List<Board> newBoards = new ArrayList<>(this.getBoards());
      newBoards.add(boardAux);
      return new GameStateImpl(
          newBoards,
          this.getWinCondition(),
          this.getTurnManager(),
          this.getListPreConditions(),
          this.getListPostConditions(),
          this.gameStateType,
          this.undoStack,
          this.redoStack);
    }
  }

  private GameState finishedGame(GameState gamePostConditions, GameStateType gameStateType) {
    return new FinishGameStateImpl(
        gamePostConditions.getBoards(),
        gamePostConditions.getWinCondition(),
        gamePostConditions.getTurnManager(),
        gamePostConditions.getListPreConditions(),
        gamePostConditions.getListPostConditions(),
        gameStateType);
  }

  @Override
  public GameState gameState() {
    return this;
  }

  @Override
  public GameStateType getGameStateType() {
    return this.gameStateType;
  }

  @Override
  public GameState undo() {
    if (undoStack.isEmpty()) {
      return new GameStateImpl(
          this.getBoards(),
          this.getWinCondition(),
          this.getTurnManager(),
          this.getListPreConditions(),
          this.getListPostConditions(),
          this.getGameStateType());
    } else {
      redoStack.push(this);
      GameState previousState = undoStack.pop();
      return new GameStateImpl(
          previousState.getBoards(),
          previousState.getWinCondition(),
          previousState.getTurnManager(),
          previousState.getListPreConditions(),
          previousState.getListPostConditions(),
          previousState.getGameStateType(),
          undoStack,
          redoStack);
    }
  }

  @Override
  public GameState redo() {
    if (this.redoStack.isEmpty()) {
      return new GameStateImpl(
          this.getBoards(),
          this.getWinCondition(),
          this.getTurnManager(),
          this.getListPreConditions(),
          this.getListPostConditions(),
          this.getGameStateType());
    } else {
      undoStack.push(this);
      GameState nextState = redoStack.pop();
      return new GameStateImpl(
          nextState.getBoards(),
          nextState.getWinCondition(),
          nextState.getTurnManager(),
          nextState.getListPreConditions(),
          nextState.getListPostConditions(),
          nextState.getGameStateType(),
          undoStack,
          redoStack);
    }
  }

  @Override
  public boolean canUndo() {
    return !undoStack.isEmpty();
  }

  @Override
  public boolean canRedo() {
    return !redoStack.isEmpty();
  }
}

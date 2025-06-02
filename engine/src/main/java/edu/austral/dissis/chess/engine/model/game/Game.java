package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.piece.Color;

import java.util.Objects;
import java.util.Optional;

//representa el estado de la partida
public final class Game {
    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Player currentPlayer;
    private final GameState gameState;
    private final Optional<Move> lastMove;
    private final int moveCount;

    public Game(Board board, Player whitePlayer, Player blackPlayer, Player currentPlayer, GameState gameState, Optional<Move> lastMove, int moveCount) {
        this.board = Objects.requireNonNull(board, "Board cannot be null");
        this.whitePlayer = Objects.requireNonNull(whitePlayer, "White player cannot be null");
        this.blackPlayer = Objects.requireNonNull(blackPlayer, "Black player cannot be null");
        this.currentPlayer = Objects.requireNonNull(currentPlayer, "Current player cannot be null");
        this.gameState = Objects.requireNonNull(gameState, "Game state cannot be null");
        this.lastMove = Objects.requireNonNull(lastMove, "Last move optional cannot be null");

        if (moveCount < 0) {
            throw new IllegalArgumentException("Move count cannot be negative: " + moveCount);
        }
        this.moveCount = moveCount;

        validatePlayers();
    }

    public Game(Board board, Player whitePlayer, Player blackPlayer) {
        this(board, whitePlayer, blackPlayer, whitePlayer,
                GameState.ACTIVE, Optional.empty(), 0);
    }

    public Game withMove(Move move, Board newBoard, GameState newGameState) {
        Objects.requireNonNull(move, "Move cannot be null");
        Objects.requireNonNull(newBoard, "New board cannot be null");
        Objects.requireNonNull(newGameState, "New game state cannot be null");

        Player nextPlayer = getOpponentPlayer();
        return new Game(newBoard, whitePlayer, blackPlayer,
                nextPlayer, newGameState, Optional.of(move), moveCount + 1);
    }

    public Game withGameState(GameState newGameState) {
        Objects.requireNonNull(newGameState, "Game state cannot be null");
        return new Game(board, whitePlayer, blackPlayer,
                currentPlayer, newGameState, lastMove, moveCount);
    }

    public Player getOpponentPlayer() {
        return currentPlayer.equals(whitePlayer) ? blackPlayer : whitePlayer;
    }

    public boolean isPlayerTurn(Player player) {
        return currentPlayer.equals(player);
    }

    public boolean isColorTurn(Color color) {
        return currentPlayer.color().equals(color);
    }

    public Player getPlayerByColor(Color color) {
        return color.isWhite() ? whitePlayer : blackPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Optional<Move> getLastMove() {
        return lastMove;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private void validatePlayers() {
        if (!whitePlayer.isOpponentOf(blackPlayer)) {
            throw new IllegalArgumentException("Players must have different colors");
        }

        if (!whitePlayer.isWhite()) {
            throw new IllegalArgumentException("White player must have white color");
        }

        if (!blackPlayer.isBlack()) {
            throw new IllegalArgumentException("Black player must have black color");
        }

        if (!currentPlayer.equals(whitePlayer) && !currentPlayer.equals(blackPlayer)) {
            throw new IllegalArgumentException("Current player must be one of the game players");
        }
    }

    @Override
    public String toString() {
        return String.format("Game[Turn: %s, State: %s, Moves: %d]",
                currentPlayer.getDisplayName(), gameState, moveCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Game game = (Game) obj;
        return moveCount == game.moveCount &&
                Objects.equals(board, game.board) &&
                Objects.equals(whitePlayer, game.whitePlayer) &&
                Objects.equals(blackPlayer, game.blackPlayer) &&
                Objects.equals(currentPlayer, game.currentPlayer) &&
                gameState == game.gameState &&
                Objects.equals(lastMove, game.lastMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, whitePlayer, blackPlayer,
                currentPlayer, gameState, lastMove, moveCount);
    }
}
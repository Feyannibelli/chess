package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.board.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the immutable state of a chess game.
 */
public final class GameState {

    /**
     * Possible states of a chess game.
     */
    public enum State {
        ONGOING,
        CHECK,
        CHECKMATE,
        STALEMATE,
        DRAW
    }

    private final Board board;
    private final List<Player> players;
    private final Player currentPlayer;
    private final State state;

    /**
     * Creates a new game state with the specified board, players, current player, and state.
     */
    public GameState(Board board, List<Player> players, Player currentPlayer, State state) {
        this.board = board;
        this.players = Collections.unmodifiableList(new ArrayList<>(players));
        this.currentPlayer = currentPlayer;
        this.state = state;
    }

    /**
     * Gets the board of this game state.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the players in this game state.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the current player in this game state.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the state of this game.
     */
    public State getState() {
        return state;
    }

    /**
     * Gets the player who won the game, if any.
     */
    public Optional<Player> getWinner() {
        if (state == State.CHECKMATE) {
            // The winner is the player who is not the current player
            return players.stream()
                    .filter(player -> !player.equals(currentPlayer))
                    .findFirst();
        }
        return Optional.empty();
    }
}
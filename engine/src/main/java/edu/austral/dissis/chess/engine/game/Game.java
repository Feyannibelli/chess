package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.rules.GameRules;

public final class Game {

    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Player currentPlayer;
    private final GameRules rules;
    private final Result result;

    public Game(Board board, Player whitePlayer, Player blackPlayer,
                Player currentPlayer, GameRules rules) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
        this.rules = rules;
        this.result = rules.checkGameResult(board);
    }

    public Result makeMove(Move move) {
        if (!rules.isValidMove(move, board)) {
            return Result.ongoing();
        }

        Board newBoard = rules.applyMove(move, board);
        Player nextPlayer = getNextPlayer();

        Game newGame = new Game(newBoard, whitePlayer, blackPlayer, nextPlayer, rules);
        return newGame.getResult();
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Result getResult() {
        return result;
    }

    private Player getNextPlayer() {
        return currentPlayer.color() == Color.WHITE ? blackPlayer : whitePlayer;
    }
}
package edu.austral.dissis.chess.engine.model.domain.game;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.rules.GameRules;
import edu.austral.dissis.chess.engine.model.result.Result;

import java.util.List;
import java.util.ArrayList;

public record Game(
        Board board,
        Player currentPlayer,
        Player whitePlayer,
        Player blackPlayer,
        GameState gameState,
        GameRules gameRules,
        List<Move> moveHistory
) {

    public Game {
        moveHistory = List.copyOf(moveHistory);
    }

    public static Game createStandardGame(GameRules gameRules) {
        Player whitePlayer = new Player("White", Color.WHITE);
        Player blackPlayer = new Player("Black", Color.BLACK);

        return new Game(
                Board.createStandardBoard(),
                whitePlayer,
                whitePlayer,
                blackPlayer,
                GameState.ONGOING,
                gameRules,
                new ArrayList<>()
        );
    }

    public Result<Game> makeMove(Move move) {
        if (!isCurrentPlayer(move.player())) {
            return Result.error("Not your turn");
        }

        if (gameRules.isGameOver(gameState)) {
            return Result.error("Game is over");
        }

        Result<Void> validation = gameRules.validateMove(move, board);
        if (validation.isError()) {
            return validation.map(v -> this);
        }

        return executeMove(move);
    }

    private Result<Game> executeMove(Move move) {
        Board newBoard = board.movePiece(move.from(), move.to());
        Player nextPlayer = getOpponent(currentPlayer);
        GameState newState = gameRules.updateGameState(newBoard, nextPlayer.color());

        List<Move> newHistory = new ArrayList<>(moveHistory);
        newHistory.add(move);

        Game newGame = new Game(
                newBoard,
                nextPlayer,
                whitePlayer,
                blackPlayer,
                newState,
                gameRules,
                newHistory
        );

        return Result.success(newGame);
    }

    private boolean isCurrentPlayer(Player player) {
        return currentPlayer.equals(player);
    }

    private Player getOpponent(Player player) {
        return player.equals(whitePlayer) ? blackPlayer : whitePlayer;
    }

    public boolean isGameOver() {
        return gameRules.isGameOver(gameState);
    }

    public int getTurnNumber() {
        return moveHistory.size() + 1;
    }
}
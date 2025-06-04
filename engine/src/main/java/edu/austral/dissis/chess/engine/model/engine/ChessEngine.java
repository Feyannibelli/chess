package edu.austral.dissis.chess.engine.model.engine;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.game.GameState;
import edu.austral.dissis.chess.engine.model.domain.game.Player;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.rules.GameRules;
import edu.austral.dissis.chess.engine.model.domain.rules.StandardChessRules;
import edu.austral.dissis.chess.engine.model.result.Result;

public class ChessEngine {
    private final Board board;
    private final Player currentPlayer;
    private final GameState gameState;
    private final GameRules gameRules;

    public ChessEngine(Board board, Player currentPlayer, GameState gameState, GameRules gameRules) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.gameState = gameState;
        this.gameRules = gameRules;
    }

    public static ChessEngine createStandardGame() {
        Board initialBoard = Board.createStandardBoard();
        Player whitePlayer = new Player("White", Color.WHITE);
        GameRules rules = new StandardChessRules();
        return new ChessEngine(initialBoard, whitePlayer, GameState.ONGOING, rules);
    }

    public Result<ChessEngine> makeMove(Move move) {
        Result<Void> validation = gameRules.validateMove(move, board);
        if (validation.isError()) {
            return Result.error(validation.getErrorMessage());
        }

        Board newBoard = board.movePiece(move.from(), move.to());

        Player nextPlayer = new Player(
                currentPlayer.color() == Color.WHITE ? "Black" : "White",
                currentPlayer.color().opposite()
        );

        GameState newGameState = gameRules.updateGameState(newBoard, nextPlayer.color());

        return Result.success(new ChessEngine(newBoard, nextPlayer, newGameState, gameRules));
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameOver() {
        return gameState == GameState.CHECKMATE ||
                gameState == GameState.STALEMATE ||
                gameState == GameState.DRAW;
    }

    public ChessEngine switchPlayer() {
        Player nextPlayer = new Player(
                currentPlayer.color() == Color.WHITE ? "Black" : "White",
                currentPlayer.color().opposite()
        );
        return new ChessEngine(board, nextPlayer, gameState, gameRules);
    }
}
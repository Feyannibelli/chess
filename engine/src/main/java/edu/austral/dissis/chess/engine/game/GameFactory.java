package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.BoardFactory;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.rules.StandardChessRules;
import edu.austral.dissis.chess.engine.rules.GameRules;

public final class GameFactory {

    public static Game createStandardChessGame(String whitePlayerId, String blackPlayerId) {
        Board board = BoardFactory.createStandardChessBoard();
        Player whitePlayer = createPlayer(whitePlayerId, Color.WHITE);
        Player blackPlayer = createPlayer(blackPlayerId, Color.BLACK);
        GameRules rules = new StandardChessRules();

        return new Game(board, whitePlayer, blackPlayer, whitePlayer, rules);
    }

    public static Game createCustomGame(Board board, Player whitePlayer, Player blackPlayer,
                                        Player startingPlayer, GameRules rules) {
        return new Game(board, whitePlayer, blackPlayer, startingPlayer, rules);
    }

    private static Player createPlayer(String playerId, Color color) {
        return new Player(playerId, color);
    }
}
package edu.austral.dissis.chess.engine.model.game.rules;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.game.Game;
import edu.austral.dissis.chess.engine.model.game.Move;
import edu.austral.dissis.chess.engine.model.game.MoveResult;
import edu.austral.dissis.chess.engine.model.piece.Color;

//define reglas del juego
public interface GameRules {
    MoveResult validateMove(Move move, Game game);

    boolean isKingInCheck(Board board, Color kingColor);

    boolean isCheckmate(Game game, Color color);

    boolean isStalemate(Game game, Color color);

    Position getKingPosition(Board board, Color color);
}

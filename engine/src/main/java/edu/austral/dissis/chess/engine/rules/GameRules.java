package edu.austral.dissis.chess.engine.rules;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.game.GameState;
import edu.austral.dissis.chess.engine.game.Move;
import edu.austral.dissis.chess.engine.game.Result;

//define las reglas del juego
public interface GameRules {

    //valida si un movimiento es legal
    Result<Void> validateMove(Move move, Board board);

    //actualiza el estado del juego despues de un movimiento
    GameState updateGameState(GameState currentState, Move move);

    //verifica si el juego a terminado
    boolean isGameOver(GameState gameState);

    //verifica si hay movimiento validos disponibles para x color
    boolean hasValidMoves(edu.austral.dissis.chess.engine.color.Color color, Board board);
}
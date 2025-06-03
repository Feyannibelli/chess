package edu.austral.dissis.chess.engine.model.game.rules;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.game.*;
import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.Objects;
import java.util.Optional;

//implementacion de reglas clasicas
public final class ClassicChessRules implements GameRules {

    //valida un movimiento segun las reglas
    @Override
    public MoveResult validateMove(Move move, Game game) {
        Objects.requireNonNull(move, "Move cannot be null");
        Objects.requireNonNull(game, "Game cannot be null");

        if (game.getGameState().isGameOver()) {
            return new InvalidMove("Game is already over");
        }

        if (!isCorrectPlayerTurn(move, game)) {
            return new InvalidMove("Not your turn");
        }

        if (!isPieceMovementValid(move, game.getBoard())) {
            return new InvalidMove("Invalid movement for this piece");
        }

        if (wouldLeaveKingInCheck(move, game)) {
            return new InvalidMove("Move would leave king in check");
        }

        Game newGame = executeMove(move, game);
        return new ValidMove(newGame);
    }

    @Override
    public boolean isKingInCheck(Board board, Color kingColor) {
        return CheckValidator.isKingInCheck(board, kingColor);
    }

    @Override
    public boolean isCheckmate(Game game, Color color) {
        return CheckmateValidator.isCheckmate(game, color);
    }

    @Override
    public boolean isStalemate(Game game, Color color) {
        return StalemateValidator.isStalemate(game, color);
    }

    @Override
    public Position getKingPosition(Board board, Color color) {
        return CheckValidator.findKingPosition(board, color);
    }

    //verifica si es el turno correcto
    private boolean isCorrectPlayerTurn(Move move, Game game) {
        return game.isColorTurn(move.piece().color());
    }

    //verifica si el mocimiento es valido
    private boolean isPieceMovementValid(Move move, Board board) {
        return move.piece().canMoveTo(move.from(), move.to(), board);
    }

    //verifica si el movimiento deharia al reu en jaque
    private boolean wouldLeaveKingInCheck(Move move, Game game) {
        Board boardAfterMove = game.getBoard().movePiece(move.from(), move.to());
        return CheckValidator.isKingInCheck(boardAfterMove, move.piece().color());
    }

    //ejecuta un movimiento valido y retorna el nuevo estado del juego
    private Game executeMove(Move move, Game game) {
        Board newBoard = game.getBoard().movePiece(move.from(), move.to());
        GameState newState = determineGameState(newBoard, game.getOpponentPlayer().color());

        return game.withMove(move, newBoard, newState);
    }

    //determina el nuevo estado del juego
    private GameState determineGameState(Board board, Color nextPlayerColor) {
        if (CheckValidator.isKingInCheck(board, nextPlayerColor)) {
            return GameState.CHECK;
        }

        return GameState.ACTIVE;
    }
}
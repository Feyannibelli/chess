package edu.austral.dissis.chess.engine.rules;

import edu.austral.dissis.chess.engine.board.Board;
import edu.austral.dissis.chess.engine.board.Position;
import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.game.GameState;
import edu.austral.dissis.chess.engine.game.Move;
import edu.austral.dissis.chess.engine.game.Result;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceType;

import java.util.Optional;

//reglas estandar del ajedres
public final class StandardChessRules implements GameRules {

    @Override
    public Result<Void> validateMove(Move move, Board board) {
        //verificar que la posicion de origen tenga una pieza
        Optional<Piece> pieceOpt = board.getPieceAt(move.getFrom());
        if (pieceOpt.isEmpty()) {
            return Result.error("No piece at origin position");
        }

        Piece piece = pieceOpt.get();

        //verificar que la pieza pertenezca al jugador actual
        if (!piece.getColor().equals(move.getPlayer().getColor())) {
            return Result.error("Piece does not belong to current player");
        }

        //verificar que el destino este dentro del tablero
        if (!board.isPositionInBounds(move.getTo())) {
            return Result.error("Destination is out of bounds");
        }

        //verificar que la pieza pueda moverse a la posicion destino
        if (!piece.canMoveTo(move.getFrom(), move.getTo(), board)) {
            return Result.error("Invalid move for this piece");
        }

        //verificar que no capture una pieza del mismo color
        Optional<Piece> targetPiece = board.getPieceAt(move.getTo());
        if (targetPiece.isPresent() && targetPiece.get().getColor().equals(piece.getColor())) {
            return Result.error("Cannot capture own piece");
        }

        return Result.success(null);
    }

    @Override
    public GameState updateGameState(GameState currentState, Move move) {
        Board newBoard = executeMove(currentState.getBoard(), move);

        //cambiar al siguiente jugador
        edu.austral.dissis.chess.engine.game.Player nextPlayer = getNextPlayer(currentState);

        //determina el nuevo estado del juego
        GameState.State newState = determineGameState(newBoard, nextPlayer.getColor());

        return new GameState(newBoard, currentState.getPlayers(), nextPlayer, newState);
    }

    @Override
    public boolean isGameOver(GameState gameState) {
        return gameState.getState() == GameState.State.CHECKMATE ||
                gameState.getState() == GameState.State.STALEMATE ||
                gameState.getState() == GameState.State.DRAW;
    }

    @Override
    public boolean hasValidMoves(Color color, Board board) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> entry.getValue().getColor() == color)
                .anyMatch(entry -> hasValidMovesFromPosition(entry.getKey(), board));
    }

    //metodos auxiliares privados
    private Board executeMove(Board board, Move move) {
        Optional<Piece> piece = board.getPieceAt(move.getFrom());
        if (piece.isEmpty()) {
            return board;
        }

        return board.withoutPiece(move.getFrom())
                .withPiece(move.getTo(), piece.get());
    }

    private edu.austral.dissis.chess.engine.game.Player getNextPlayer(GameState currentState) {
        return currentState.getPlayers().stream()
                .filter(player -> !player.equals(currentState.getCurrentPlayer()))
                .findFirst()
                .orElse(currentState.getCurrentPlayer());
    }

    private GameState.State determineGameState(Board board, Color currentPlayerColor) {
        if (isInCheck(board, currentPlayerColor)) {
            if (hasValidMoves(currentPlayerColor, board)) {
                return GameState.State.CHECK;
            } else {
                return GameState.State.CHECKMATE;
            }
        } else if (!hasValidMoves(currentPlayerColor, board)) {
            return GameState.State.STALEMATE;
        }

        return GameState.State.ONGOING;
    }

    private boolean isInCheck(Board board, Color kingColor) {
        Optional<Position> kingPosition = findKingPosition(board, kingColor);
        if (kingPosition.isEmpty()) {
            return false;
        }

        Color opponentColor = kingColor == Color.WHITE ? Color.BLACK : Color.WHITE;

        return board.getPieces().entrySet().stream()
                .filter(entry -> entry.getValue().getColor() == opponentColor)
                .anyMatch(entry -> entry.getValue().canMoveTo(
                        entry.getKey(), kingPosition.get(), board));
    }

    private Optional<Position> findKingPosition(Board board, Color color) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> entry.getValue().getType() == PieceType.KING &&
                        entry.getValue().getColor() == color)
                .map(java.util.Map.Entry::getKey)
                .findFirst();
    }

    private boolean hasValidMovesFromPosition(Position from, Board board) {
        Optional<Piece> pieceOpt = board.getPieceAt(from);
        if (pieceOpt.isEmpty()) {
            return false;
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position to = new Position(row, col);
                if (pieceOpt.get().canMoveTo(from, to, board)) {
                    return true;
                }
            }
        }
        return false;
    }
}

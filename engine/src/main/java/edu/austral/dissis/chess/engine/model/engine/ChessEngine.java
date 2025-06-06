package edu.austral.dissis.chess.engine.model.engine;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.game.Game;
import edu.austral.dissis.chess.engine.model.domain.game.GameSetup;
import edu.austral.dissis.chess.engine.model.domain.game.GameState;
import edu.austral.dissis.chess.engine.model.domain.game.Player;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.engine.model.domain.rules.StandardChessRules;
import edu.austral.dissis.chess.engine.model.result.Result;

import java.util.List;
import java.util.Optional;

public class ChessEngine {
    private final Game game;

    private ChessEngine(Game game) {
        this.game = game;
    }

    public static ChessEngine createStandardGame() {
        Game standardGame = GameSetup.standardChess().createGame();
        return new ChessEngine(standardGame);
    }

    public static ChessEngine createCustomGame(GameSetup setup) {
        Game customGame = setup.createGame();
        return new ChessEngine(customGame);
    }

    public Result<ChessEngine> makeMove(Move move) {
        return game.makeMove(move)
                .map(ChessEngine::new);
    }

    public Result<ChessEngine> makeMove(Position from, Position to) {
        Move move = new Move(from, to, getCurrentPlayer());
        return makeMove(move);
    }

    public List<Position> getValidMoves(Position from) {
        Optional<Piece> piece = game.board().getPieceAt(from);
        if (piece.isEmpty() || piece.get().color() != getCurrentPlayer().color()) {
            return List.of();
        }

        return game.board().getAllPositions().stream()
                .filter(to -> piece.get().canMoveTo(from, to, game.board()))
                .filter(to -> wouldNotLeaveKingInCheck(from, to))
                .toList();
    }

    private boolean wouldNotLeaveKingInCheck(Position from, Position to) {
        Board testBoard = game.board().movePiece(from, to);
        return !edu.austral.dissis.chess.engine.model.domain.rules.CheckDetector
                .isInCheck(testBoard, getCurrentPlayer().color());
    }

    public boolean canMakeMove(Position from, Position to) {
        return getValidMoves(from).contains(to);
    }

    // Getters inmutables
    public Board getBoard() {
        return game.board();
    }

    public Player getCurrentPlayer() {
        return game.currentPlayer();
    }

    public GameState getGameState() {
        return game.gameState();
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }

    public List<Move> getMoveHistory() {
        return game.moveHistory();
    }

    public int getTurnNumber() {
        return game.getTurnNumber();
    }

    public Optional<String> getGameResult() {
        return switch (game.gameState()) {
            case CHECKMATE -> Optional.of(getOpponentColor() + " wins by checkmate");
            case STALEMATE -> Optional.of("Draw by stalemate");
            case DRAW -> Optional.of("Draw");
            default -> Optional.empty();
        };
    }

    private String getOpponentColor() {
        return getCurrentPlayer().color() == Color.WHITE ? "Black" : "White";
    }

    // Para debugging y testing
    public String getBoardString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 7; row >= 0; row--) {
            sb.append(row + 1).append(" ");
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Optional<Piece> piece = game.board().getPieceAt(pos);
                if (piece.isPresent()) {
                    sb.append(getPieceSymbol(piece.get()));
                } else {
                    sb.append(".");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("  a b c d e f g h\n");
        return sb.toString();
    }

    private String getPieceSymbol(Piece piece) {
        String symbol = switch (piece.type()) {
            case KING -> "K";
            case QUEEN -> "Q";
            case ROOK -> "R";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case PAWN -> "P";
        };
        return piece.color() == Color.WHITE ? symbol : symbol.toLowerCase();
    }
}
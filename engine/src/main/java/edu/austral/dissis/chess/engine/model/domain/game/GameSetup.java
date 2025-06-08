package edu.austral.dissis.chess.engine.model.domain.game;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.engine.model.domain.piece.PieceType;
import edu.austral.dissis.chess.engine.model.domain.piece.PieceFactory;
import edu.austral.dissis.chess.engine.model.domain.rules.GameRules;
import edu.austral.dissis.chess.engine.model.domain.rules.StandardChessRules;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class GameSetup {
    private final Map<Position, Piece> pieces;
    private final int boardRows;
    private final int boardColumns;
    private final GameRules gameRules;

    public GameSetup(Map<Position, Piece> pieces, int boardRows, int boardColumns, GameRules gameRules) {
        this.pieces = Map.copyOf(pieces);
        this.boardRows = boardRows;
        this.boardColumns = boardColumns;
        this.gameRules = gameRules;
    }

    public static GameSetup standardChess() {
        return new GameSetup(
                createStandardPieces(),
                8,
                8,
                new StandardChessRules()
        );
    }

    public static GameSetup customGame(Map<Position, Piece> pieces, int rows, int columns, GameRules rules) {
        return new GameSetup(pieces, rows, columns, rules);
    }

    public Game createGame() {
        Board board = new Board(pieces, boardRows, boardColumns);
        Player whitePlayer = new Player("White", Color.WHITE);
        Player blackPlayer = new Player("Black", Color.BLACK);

        return new Game(
                board,
                whitePlayer,
                whitePlayer,
                blackPlayer,
                GameState.ONGOING,
                gameRules,
                new ArrayList<>()
        );
    }

    private static Map<Position, Piece> createStandardPieces() {
        Map<Position, Piece> pieces = new HashMap<>();

        setupBackRank(pieces, 0, Color.WHITE);
        setupPawns(pieces, 1, Color.WHITE);
        setupBackRank(pieces, 7, Color.BLACK);
        setupPawns(pieces, 6, Color.BLACK);

        return pieces;
    }

    private static void setupBackRank(Map<Position, Piece> pieces, int row, Color color) {
        pieces.put(new Position(row, 0), PieceFactory.createPiece(PieceType.ROOK, color));
        pieces.put(new Position(row, 1), PieceFactory.createPiece(PieceType.KNIGHT, color));
        pieces.put(new Position(row, 2), PieceFactory.createPiece(PieceType.BISHOP, color));
        pieces.put(new Position(row, 3), PieceFactory.createPiece(PieceType.QUEEN, color));
        pieces.put(new Position(row, 4), PieceFactory.createPiece(PieceType.KING, color));
        pieces.put(new Position(row, 5), PieceFactory.createPiece(PieceType.BISHOP, color));
        pieces.put(new Position(row, 6), PieceFactory.createPiece(PieceType.KNIGHT, color));
        pieces.put(new Position(row, 7), PieceFactory.createPiece(PieceType.ROOK, color));
    }

    private static void setupPawns(Map<Position, Piece> pieces, int row, Color color) {
        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(row, col), PieceFactory.createPiece(PieceType.PAWN, color));
        }
    }
}
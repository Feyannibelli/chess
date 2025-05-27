package edu.austral.dissis.chess.engine.board;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceFactory;
import java.util.HashMap;
import java.util.Map;

public final class BoardFactory {

    public static Board createStandardChessBoard() {
        Map<Position, Piece> pieces = new HashMap<>();

        setupPawns(pieces);
        setupMajorPieces(pieces);

        return new ChessBoard(pieces);
    }

    private static void setupPawns(Map<Position, Piece> pieces) {
        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(1, col), PieceFactory.createPawn(Color.WHITE));
            pieces.put(new Position(6, col), PieceFactory.createPawn(Color.BLACK));
        }
    }

    private static void setupMajorPieces(Map<Position, Piece> pieces) {
        setupBackRank(pieces, 0, Color.WHITE);
        setupBackRank(pieces, 7, Color.BLACK);
    }

    private static void setupBackRank(Map<Position, Piece> pieces, int row, Color color) {
        pieces.put(new Position(row, 0), PieceFactory.createRook(color));
        pieces.put(new Position(row, 1), PieceFactory.createKnight(color));
        pieces.put(new Position(row, 2), PieceFactory.createBishop(color));
        pieces.put(new Position(row, 3), PieceFactory.createQueen(color));
        pieces.put(new Position(row, 4), PieceFactory.createKing(color));
        pieces.put(new Position(row, 5), PieceFactory.createBishop(color));
        pieces.put(new Position(row, 6), PieceFactory.createKnight(color));
        pieces.put(new Position(row, 7), PieceFactory.createRook(color));
    }
}

package edu.austral.dissis.chess.engine.board;

import edu.austral.dissis.chess.engine.color.Color;
import edu.austral.dissis.chess.engine.piece.Piece;
import edu.austral.dissis.chess.engine.piece.PieceFactory;
import edu.austral.dissis.chess.engine.piece.PieceType;

import java.util.HashMap;
import java.util.Map;

public final class BoardFactory {

    private final PieceFactory pieceFactory;

    public BoardFactory(PieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
    }

    public Board createStandardBoard() {
        return createBoard(8, 8, createStandardPieces());
    }

    public Board createEmptyBoard(int rows, int columns) {
        return new Board(rows, columns, new HashMap<>());
    }

    public Board createBoard(int rows, int columns, Map<Position, Piece> pieces) {
        return new Board(rows, columns, pieces);
    }

    private Map<Position, Piece> createStandardPieces() {
        Map<Position, Piece> pieces = new HashMap<>();

        // Peones blancos
        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(1, col),
                    pieceFactory.createPiece(PieceType.PAWN, Color.WHITE));
        }

        // Peones negros
        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(6, col),
                    pieceFactory.createPiece(PieceType.PAWN, Color.BLACK));
        }

        // Piezas blancas (fila 0)
        pieces.put(new Position(0, 0), pieceFactory.createPiece(PieceType.ROOK, Color.WHITE));
        pieces.put(new Position(0, 1), pieceFactory.createPiece(PieceType.KNIGHT, Color.WHITE));
        pieces.put(new Position(0, 2), pieceFactory.createPiece(PieceType.BISHOP, Color.WHITE));
        pieces.put(new Position(0, 3), pieceFactory.createPiece(PieceType.QUEEN, Color.WHITE));
        pieces.put(new Position(0, 4), pieceFactory.createPiece(PieceType.KING, Color.WHITE));
        pieces.put(new Position(0, 5), pieceFactory.createPiece(PieceType.BISHOP, Color.WHITE));
        pieces.put(new Position(0, 6), pieceFactory.createPiece(PieceType.KNIGHT, Color.WHITE));
        pieces.put(new Position(0, 7), pieceFactory.createPiece(PieceType.ROOK, Color.WHITE));

        // Piezas negras (fila 7)
        pieces.put(new Position(7, 0), pieceFactory.createPiece(PieceType.ROOK, Color.BLACK));
        pieces.put(new Position(7, 1), pieceFactory.createPiece(PieceType.KNIGHT, Color.BLACK));
        pieces.put(new Position(7, 2), pieceFactory.createPiece(PieceType.BISHOP, Color.BLACK));
        pieces.put(new Position(7, 3), pieceFactory.createPiece(PieceType.QUEEN, Color.BLACK));
        pieces.put(new Position(7, 4), pieceFactory.createPiece(PieceType.KING, Color.BLACK));
        pieces.put(new Position(7, 5), pieceFactory.createPiece(PieceType.BISHOP, Color.BLACK));
        pieces.put(new Position(7, 6), pieceFactory.createPiece(PieceType.KNIGHT, Color.BLACK));
        pieces.put(new Position(7, 7), pieceFactory.createPiece(PieceType.ROOK, Color.BLACK));

        return pieces;
    }
}

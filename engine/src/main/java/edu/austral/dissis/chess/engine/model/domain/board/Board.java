// Board.java
package edu.austral.dissis.chess.engine.model.domain.board;

import edu.austral.dissis.chess.engine.model.domain.piece.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private final Map<Position, Piece> pieces;
    private final int rows;
    private final int columns;

    public Board(Map<Position, Piece> pieces, int rows, int columns) {
        this.pieces = Map.copyOf(pieces);
        this.rows = rows;
        this.columns = columns;
    }

    public static Board createStandardBoard() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(0, 0), createPiece(PieceType.ROOK, Color.WHITE));
        pieces.put(new Position(0, 1), createPiece(PieceType.KNIGHT, Color.WHITE));
        pieces.put(new Position(0, 2), createPiece(PieceType.BISHOP, Color.WHITE));
        pieces.put(new Position(0, 3), createPiece(PieceType.QUEEN, Color.WHITE));
        pieces.put(new Position(0, 4), createPiece(PieceType.KING, Color.WHITE));
        pieces.put(new Position(0, 5), createPiece(PieceType.BISHOP, Color.WHITE));
        pieces.put(new Position(0, 6), createPiece(PieceType.KNIGHT, Color.WHITE));
        pieces.put(new Position(0, 7), createPiece(PieceType.ROOK, Color.WHITE));

        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(1, col), createPiece(PieceType.PAWN, Color.WHITE));
        }

        pieces.put(new Position(7, 0), createPiece(PieceType.ROOK, Color.BLACK));
        pieces.put(new Position(7, 1), createPiece(PieceType.KNIGHT, Color.BLACK));
        pieces.put(new Position(7, 2), createPiece(PieceType.BISHOP, Color.BLACK));
        pieces.put(new Position(7, 3), createPiece(PieceType.QUEEN, Color.BLACK));
        pieces.put(new Position(7, 4), createPiece(PieceType.KING, Color.BLACK));
        pieces.put(new Position(7, 5), createPiece(PieceType.BISHOP, Color.BLACK));
        pieces.put(new Position(7, 6), createPiece(PieceType.KNIGHT, Color.BLACK));
        pieces.put(new Position(7, 7), createPiece(PieceType.ROOK, Color.BLACK));

        for (int col = 0; col < 8; col++) {
            pieces.put(new Position(6, col), createPiece(PieceType.PAWN, Color.BLACK));
        }

        return new Board(pieces, 8, 8);
    }

    private static Piece createPiece(PieceType type, Color color) {
        return PieceFactory.createPiece(type, color);
    }

    public Optional<Piece> getPieceAt(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Square getSquareAt(Position position) {
        return new Square(position, getPieceAt(position));
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    public boolean isOccupied(Position position) {
        return pieces.containsKey(position);
    }

    public Board movePiece(Position from, Position to) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);

        Piece piece = newPieces.remove(from);
        if (piece != null) {
            newPieces.put(to, piece);
        }

        return new Board(newPieces, rows, columns);
    }

    public List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }

    public List<Piece> getAllPieces() {
        return new ArrayList<>(pieces.values());
    }

    public List<Position> getPiecePositions(Color color) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().color() == color)
                .map(Map.Entry::getKey)
                .toList();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
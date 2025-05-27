package edu.austral.dissis.chess.engine.board;

import edu.austral.dissis.chess.engine.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ChessBoard implements Board {

    private final Map<Position, Piece> pieces;

    public ChessBoard(Map<Position, Piece> pieces) {
        this.pieces = Map.copyOf(pieces);
    }

    public ChessBoard() {
        this(new HashMap<>());
    }

    @Override
    public Optional<Piece> getPieceAt(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    @Override
    public Board withPieceAt(Position position, Piece piece) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.put(position, piece);
        return new ChessBoard(newPieces);
    }

    @Override
    public Board withoutPieceAt(Position position) {
        Map<Position, Piece> newPieces = new HashMap<>(pieces);
        newPieces.remove(position);
        return new ChessBoard(newPieces);
    }

    @Override
    public Map<Position, Piece> getAllPieces() {
        return Map.copyOf(pieces);
    }
}
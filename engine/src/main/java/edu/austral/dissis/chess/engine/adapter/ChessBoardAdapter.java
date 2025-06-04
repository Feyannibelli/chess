package edu.austral.dissis.chess.engine.adapter;

import edu.austral.dissis.chess.engine.model.domain.board.Board;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.gui.BoardState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChessBoardAdapter {

    public static BoardState toBoardState(Board board) {
        Map<edu.austral.dissis.chess.gui.Position, edu.austral.dissis.chess.gui.ChessPiece> pieces = new HashMap<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);
                Optional<Piece> piece = board.getPieceAt(position);

                if (piece.isPresent()) {
                    edu.austral.dissis.chess.gui.Position guiPosition = PositionAdapter.toGui(position);
                    edu.austral.dissis.chess.gui.ChessPiece guiPiece = ChessPieceAdapter.toGui(piece.get());
                    pieces.put(guiPosition, guiPiece);
                }
            }
        }

        return new BoardState(pieces, board.getRows(), board.getColumns());
    }
}
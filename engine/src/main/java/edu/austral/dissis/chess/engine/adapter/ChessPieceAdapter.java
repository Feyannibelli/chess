package edu.austral.dissis.chess.engine.adapter;

import edu.austral.dissis.chess.engine.model.domain.piece.Piece;
import edu.austral.dissis.chess.engine.model.domain.piece.PieceType;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.gui.ChessPiece;
import edu.austral.dissis.chess.gui.PlayerColor;

public class ChessPieceAdapter {

    public static ChessPiece toGui(Piece piece) {
        return new ChessPiece(
                piece.type().name().toLowerCase(),
                colorToGui(piece.color())
        );
    }

    public static PlayerColor colorToGui(Color color) {
        return switch (color) {
            case WHITE -> PlayerColor.WHITE;
            case BLACK -> PlayerColor.BLACK;
        };
    }
}
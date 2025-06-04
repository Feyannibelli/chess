package edu.austral.dissis.chess.engine.adapter;

import edu.austral.dissis.chess.engine.model.domain.board.Position;

public class PositionAdapter {

    public static Position fromGui(edu.austral.dissis.chess.gui.Position guiPosition) {
        return new Position(guiPosition.getRow(), guiPosition.getColumn());
    }

    public static edu.austral.dissis.chess.gui.Position toGui(Position position) {
        return new edu.austral.dissis.chess.gui.Position(position.row(), position.column());
    }
}
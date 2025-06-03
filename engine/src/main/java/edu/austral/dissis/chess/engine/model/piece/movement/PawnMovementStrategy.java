package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Color;

//movimiento peon, una casilla comer diagonal
public final class PawnMovementStrategy implements MovementStrategy {

    @Override
    public boolean canMoveTo(Position from, Position to, Board board, Color color) {
        if (from.equals(to)) {
            return false;
        }

        int direction = color.getPawnDirection();
        int rowDiff = to.row() - from.row();
        int colDiff = Math.abs(to.column() - from.column());

        //movimiento para adelante
        if (colDiff == 0) {
            return canMoveForward(from, to, board, direction, rowDiff);
        }

        //captura en diagonal
        if (colDiff == 1 && rowDiff == direction) {
            return board.getPiece(to)
                    .map(piece -> !piece.color().equals(color))
                    .orElse(false);
        }

        return false;
    }

    private boolean canMoveForward(Position from, Position to, Board board,
                                   int direction, int rowDiff) {
        //paso simple
        if (rowDiff == direction) {
            return board.getPiece(to).isEmpty();
        }

        //paso doble desde posicon inicial
        if (rowDiff == 2 * direction && from.row() == Color.WHITE.getPawnStartRow()) {
            return board.getPiece(to).isEmpty() &&
                    board.getPiece(new Position(from.row() + direction, from.column())).isEmpty();
        }

        if (rowDiff == 2 * direction && from.row() == Color.BLACK.getPawnStartRow()) {
            return board.getPiece(to).isEmpty() &&
                    board.getPiece(new Position(from.row() + direction, from.column())).isEmpty();
        }

        return false;
    }
}
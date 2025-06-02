package edu.austral.dissis.chess.engine.model.piece.movement.special;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.PieceType;
import edu.austral.dissis.chess.engine.model.game.Move;

import java.util.Optional;

//logica del movimiento
public final class EnPassantHandler implements SpecialMoveHandler {

    //verifica si un movimiento es valido
    public boolean canCaptureEnPassant(Position from, Position to, Board board, Piece piece, Optional<Move> lastMove) {
        if (!isPawnPiece(piece)) {
            return false;
        }

        if (!isValidEnPassantMove(from, to, piece)) {
            return false;
        }

        if (lastMove.isEmpty()) {
            return false;
        }

        return isValidEnPassantTarget(from, to, lastMove.get(), board);
    }

    //posicion del peon
    public Position getCapturedPawnPosition(Position capturePosition, Piece attackingPiece) {
        int direction = getForwardDirection(attackingPiece);
        return capturePosition.offset(-direction, 0);
    }

    //verifica si la pieza es un peon
    private boolean isPawnPiece(Piece piece) {
        return piece != null && piece.type() == PieceType.PAWN;
    }

    //verifica el movimiento la forma
    private boolean isValidEnPassantMove(Position from, Position to, Piece piece) {
        int rowDiff = to.row() - from.row();
        int colDiff = Math.abs(to.column() - from.column());
        int expectedDirection = getForwardDirection(piece);

        return rowDiff == expectedDirection && colDiff == 1;
    }

    //verifica su el ultimo movimiento lo permite
    private boolean isValidEnPassantTarget(Position from, Position to, Move lastMove, Board board) {
        if (!lastMove.piece().type().isPawn()) {
            return false;
        }

        if (!wasPawnDoubleMove(lastMove)) {
            return false;
        }

        Position expectedEnemyPosition = getCapturedPawnPosition(to,
                board.getPieceAt(from).orElseThrow());

        return lastMove.to().equals(expectedEnemyPosition) &&
                from.row() == lastMove.to().row();
    }

    //verifica si el ultimo movimiento fue un doble
    private boolean wasPawnDoubleMove(Move move) {
        int rowDiff = Math.abs(move.to().row() - move.from().row());
        return rowDiff == 2;
    }

    //direccion del avance
    private int getForwardDirection(Piece piece) {
        return piece.isWhite() ? 1 : -1;
    }

    //verifica si el peon esta en fila correcta
    public boolean isInEnPassantRank(Position position, Piece piece) {
        if (piece.isWhite()) {
            return position.row() == 4;
        } else {
            return position.row() == 3;
        }
    }
}

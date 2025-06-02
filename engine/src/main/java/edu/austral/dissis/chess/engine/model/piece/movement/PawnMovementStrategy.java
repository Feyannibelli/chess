package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.HashSet;
import java.util.Set;

//movimiento peon
public final class PawnMovementStrategy implements MovementStrategy {

    //obtiene todas las posiciones validas
    @Override
    public Set<Position> getPosibleMoves(Position from, Board board, Piece piece) {
        Set<Position> validMoves = new HashSet<>();

        addForwardMoves(from, board, piece, validMoves);
        addCaptureMoves(from, board, piece, validMoves);

        return validMoves;
    }

    //veridica si el movimiento es calido para el peon
    @Override
    public boolean isValidMove(Position from, Position to, Board board, Piece piece) {
        if (!board.isValidPosition(to)) {
            return false;
        }

        return canMoveForward(from, to, board, piece) || canCapture(from, to, board, piece);
    }

    //movimiento hacia delante
    private void addForwardMoves(Position from, Board board, Piece piece, Set<Position> validMoves) {
        int direction = getForwardDirection(piece);
        Position oneSquareForward = from.offset(direction, 0);

        if (board.isValidPosition(oneSquareForward) && board.isEmpty(oneSquareForward)) {
            validMoves.add(oneSquareForward);

            if (!piece.hasMoved()) {
                Position twoSquaresForward = from.offset(2 * direction, 0);
                if (board.isValidPosition(twoSquaresForward) && board.isEmpty(twoSquaresForward)) {
                    validMoves.add(twoSquaresForward);
                }
            }
        }
    }

    //movimiento de captura
    private void addCaptureMoves(Position from, Board board, Piece piece, Set<Position> validMoves) {
        int direction = getForwardDirection(piece);
        Position leftCapture = from.offset(direction, -1);
        Position rightCapture = from.offset(direction, 1);

        if (canCapture(from, leftCapture, board, piece)) {
            validMoves.add(leftCapture);
        }

        if (canCapture(from, rightCapture, board, piece)) {
            validMoves.add(rightCapture);
        }
    }

    //verifica si el peon puede moverse hacia adelante
    private boolean canMoveForward(Position from, Position to, Board board, Piece piece) {
        int direction = getForwardDirection(piece);
        int rowDiff = to.row() - from.row();
        int colDiff = to.column() - from.column();

        if (colDiff != 0) {
            return false;
        }

        if (rowDiff != direction && rowDiff != 2 * direction) {
            return false;
        }

        if (rowDiff == 2 * direction && piece.hasMoved()) {
            return false;
        }

        return board.isEmpty(to);
    }

    //verifica captura
    private boolean canCapture(Position from, Position to, Board board, Piece piece) {
        if (!board.isValidPosition(to)) {
            return false;
        }

        int direction = getForwardDirection(piece);
        int rowDiff = to.row() - from.row();
        int colDiff = Math.abs(to.column() - from.column());

        if (rowDiff != direction || colDiff != 1) {
            return false;
        }

        return board.getPieceAt(to)
                .map(targetPiece -> piece.isOpponentOf(targetPiece))
                .orElse(false);
    }

    //obtiene la direccion
    private int getForwardDirection(Piece piece) {
        return piece.isWhite() ? 1 : -1;
    }
}

package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.HashSet;
import java.util.Set;

//movimiento para caballo
public final class KnightMovementStrategy implements MovementStrategy {

    //movimiento en L
    private static final int[][] KNIGHT_MOVES = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2},  {1, 2},  {2, -1},  {2, 1}
    };

    //obtiene las posiciones validas del caballo
    @Override
    public Set<Position> getPosibleMoves(Position from, Board board, Piece piece) {
        Set<Position> validMoves = new HashSet<>();

        for (int[] move : KNIGHT_MOVES) {
            Position to = from.offset(move[0], move[1]);

            if (isValidMove(from, to, board, piece)) {
                validMoves.add(to);
            }
        }

        return validMoves;
    }

    //verifica movimiento valido
    @Override
    public boolean isValidMove(Position from, Position to, Board board, Piece piece) {
        if (!board.isValidPosition(to)) {
            return false;
        }

        if (!isKnightMove(from, to)) {
            return false;
        }

        return canOccupySquare(to, board, piece);
    }

    //verifica si corresponde a un caballo
    private boolean isKnightMove(Position from, Position to) {
        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    //verifica si el caballo puede ocupar la casilla
    private boolean canOccupySquare(Position to, Board board, Piece piece) {
        if (board.isEmpty(to)) {
            return true;
        }

        return board.getPieceAt(to)
                .map(targetPiece -> piece.isOpponentOf(targetPiece))
                .orElse(false);
    }
}

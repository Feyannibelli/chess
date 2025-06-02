package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.HashSet;
import java.util.Set;

//movimiento del rey
public final class KingMovementStrategy implements MovementStrategy {

    // Direcciones posibles del rey: 8 direcciones (incluye diagonales)
    private static final int[][] KING_MOVES = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    //obtiene las posiciones validas
    @Override
    public Set<Position> getPosibleMoves(Position from, Board board, Piece piece) {
        Set<Position> validMoves = new HashSet<>();

        for (int[] move : KING_MOVES) {
            Position to = from.offset(move[0], move[1]);

            if (isValidMove(from, to, board, piece)) {
                validMoves.add(to);
            }
        }

        return validMoves;
    }

    //verifica movimientos validos
    @Override
    public boolean isValidMove(Position from, Position to, Board board, Piece piece) {
        if (!board.isValidPosition(to)) {
            return false;
        }

        if (!isKingDistance(from, to)) {
            return false;
        }

        return canOccupySquare(to, board, piece);
    }

    //verifica la distancia entre posiciones
    private boolean isKingDistance(Position from, Position to) {
        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        return rowDiff <= 1 && colDiff <= 1 && (rowDiff != 0 || colDiff != 0);
    }

    private boolean canOccupySquare(Position to, Board board, Piece piece) {
        if (board.isEmpty(to)) {
            return true;
        }

        return board.getPieceAt(to)
                .map(targetPiece -> piece.isOpponentOf(targetPiece))
                .orElse(false);
    }
}
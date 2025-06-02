package edu.austral.dissis.chess.engine.model.piece.movement;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;

import java.util.HashSet;
import java.util.Set;

//movimiento diagonal
public final class DiagonalMovementStrategy implements MovementStrategy {

    //direciones diaginales
    private static final int[][] DIAGONAL_DIRECTIONS = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    //obtiene posiciones validas en movimiento en diagonal
    @Override
    public Set<Position> getPosibleMoves(Position from, Board board, Piece piece) {
        Set<Position> validMoves = new HashSet<>();

        for (int[] direction : DIAGONAL_DIRECTIONS) {
            addMovesInDirection(from, board, piece, direction, validMoves);
        }

        return validMoves;
    }

    //verifica si un movimiento especifco es valido
    @Override
    public boolean isValidMove(Position from, Position to, Board board, Piece piece) {
        if (!board.isValidPosition(to)) {
            return false;
        }

        if (!isDiagonalMove(from, to)) {
            return false;
        }

        if (!isPathClear(from, to, board)) {
            return false;
        }

        return canOccupySquare(to, board, piece);
    }

    //movimientos validos en una direccion
    private void addMovesInDirection(Position from, Board board, Piece piece,
                                     int[] direction, Set<Position> validMoves) {
        int row = from.row();
        int col = from.column();

        while (true) {
            row += direction[0];
            col += direction[1];
            Position to = new Position(row, col);

            if (!board.isValidPosition(to)) {
                break;
            }

            if (board.isEmpty(to)) {
                validMoves.add(to);
            } else {
                if (board.getPieceAt(to).map(piece::isOpponentOf).orElse(false)) {
                    validMoves.add(to);
                }
                break;
            }
        }
    }

    //verifica su el movimiento es diagonal
    private boolean isDiagonalMove(Position from, Position to) {
        int rowDiff = Math.abs(to.row() - from.row());
        int colDiff = Math.abs(to.column() - from.column());

        return rowDiff == colDiff && rowDiff > 0;
    }
    //verifca si el camnino esta libre
    private boolean isPathClear(Position from, Position to, Board board) {
        int rowStep = getStep(from.row(), to.row());
        int colStep = getStep(from.column(), to.column());

        int currentRow = from.row() + rowStep;
        int currentCol = from.column() + colStep;

        while (currentRow != to.row() || currentCol != to.column()) {
            Position currentPos = new Position(currentRow, currentCol);
            if (!board.isEmpty(currentPos)) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    private int getStep(int from, int to) {
        return from < to ? 1 : -1;
    }

    //verifica si la pieza puede ocupar x lugar
    private boolean canOccupySquare(Position to, Board board, Piece piece) {
        if (board.isEmpty(to)) {
            return true;
        }

        return board.getPieceAt(to)
                .map(targetPiece -> piece.isOpponentOf(targetPiece))
                .orElse(false);
    }
}

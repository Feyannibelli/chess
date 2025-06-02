package edu.austral.dissis.chess.engine.model.piece.movement.special;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.PieceType;
import edu.austral.dissis.chess.engine.model.piece.Color;

import java.util.Optional;

//maneja logica de enroque
public final class CastlingHandler implements SpecialMoveHandler {

    //verifica si el enroque es posible
    public boolean canCastle(Position kingPosition, Position targetPosition, Board board, Piece king) {
        if (!isKingPieceValid(king)) {
            return false;
        }

        SpecialMoveType castlingType = determineCastlingType(kingPosition, targetPosition);
        if (castlingType == null) {
            return false;
        }

        return validateCastlingConditions(kingPosition, castlingType, board, king);
    }

    //determina tipo de enroque
    private SpecialMoveType determineCastlingType(Position from, Position to) {
        if (!isKingStartingPosition(from)) {
            return null;
        }

        int columnDiff = to.column() - from.column();

        if (columnDiff == 2) {
            return SpecialMoveType.CASTLING_KINGSIDE;
        } else if (columnDiff == -2) {
            return SpecialMoveType.CASTLING_QUEENSIDE;
        }

        return null;
    }

    //valida condiciones necesarias
    private boolean validateCastlingConditions(Position kingPosition, SpecialMoveType castlingType,
                                               Board board, Piece king) {
        return !king.hasMoved() &&
                hasValidRook(kingPosition, castlingType, board, king.color()) &&
                isPathClear(kingPosition, castlingType, board) &&
                !isKingInCheck(kingPosition, board, king);
    }

    //verifica si existe una torre valida
    private boolean hasValidRook(Position kingPosition, SpecialMoveType castlingType,
                                 Board board, Color kingColor) {
        Position rookPosition = getRookPosition(kingPosition, castlingType);
        Optional<Piece> rookPiece = board.getPieceAt(rookPosition);

        return rookPiece.isPresent() &&
                rookPiece.get().type() == PieceType.ROOK &&
                rookPiece.get().color() == kingColor &&
                !rookPiece.get().hasMoved();
    }

    //obtiene posicion de la torre
    private Position getRookPosition(Position kingPosition, SpecialMoveType castlingType) {
        if (castlingType == SpecialMoveType.CASTLING_KINGSIDE) {
            return new Position(kingPosition.row(), 7); // Torre del lado del rey
        } else {
            return new Position(kingPosition.row(), 0); // Torre del lado de la dama
        }
    }

    //verifica que el camino este rey y torre este libre
    private boolean isPathClear(Position kingPosition, SpecialMoveType castlingType, Board board) {
        int startCol = kingPosition.column();
        int endCol = castlingType == SpecialMoveType.CASTLING_KINGSIDE ? 6 : 1;
        int step = startCol < endCol ? 1 : -1;

        for (int col = startCol + step; col != endCol + step; col += step) {
            Position position = new Position(kingPosition.row(), col);
            if (!board.isEmpty(position)) {
                return false;
            }
        }

        return true;
    }

    private boolean isKingPieceValid(Piece piece) {
        return piece != null && piece.type() == PieceType.KING;
    }

    private boolean isKingStartingPosition(Position position) {
        return position.column() == 4 && (position.row() == 0 || position.row() == 7);
    }

    private boolean isKingInCheck(Position kingPosition, Board board, Piece king) {
        // Implementación simplificada - en una versión completa,
        // se verificaría si alguna pieza enemiga puede atacar al rey
        return false;
    }
}

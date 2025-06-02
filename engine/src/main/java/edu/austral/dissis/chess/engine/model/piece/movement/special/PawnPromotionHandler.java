package edu.austral.dissis.chess.engine.model.piece.movement.special;

import edu.austral.dissis.chess.engine.model.board.Board;
import edu.austral.dissis.chess.engine.model.board.Position;
import edu.austral.dissis.chess.engine.model.piece.Piece;
import edu.austral.dissis.chess.engine.model.piece.PieceType;
import edu.austral.dissis.chess.engine.model.piece.Color;
import edu.austral.dissis.chess.engine.model.piece.movement.CompositeMovementStrategy;
import edu.austral.dissis.chess.engine.model.piece.movement.DiagonalMovementStrategy;
import edu.austral.dissis.chess.engine.model.piece.movement.LinearMovementStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//maneja la logica de promocion
public final class PawnPromotionHandler implements SpecialMoveHandler {

    //piezas validas para la promocion
    private static final List<PieceType> VALID_PROMOTION_PIECES = Arrays.asList(
            PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT
    );

    //verifica si un peon debe ser promovido
    public boolean shouldPromote(Position position, Piece piece, Board board) {
        if (!isPawnPiece(piece)) {
            return false;
        }

        return isPromotionRank(position, piece, board);
    }

    //crea unna nueva pieza promovida
    public Piece createPromotedPiece(PieceType promotionType, Color pawnColor) {
        if (!isValidPromotionType(promotionType)) {
            throw new IllegalArgumentException("Invalid promotion type: " + promotionType);
        }

        return new Piece(promotionType, pawnColor, createMovementStrategy(promotionType), true);
    }

    //lista de tipos de piezas
    public List<PieceType> getValidPromotionTypes() {
        return List.copyOf(VALID_PROMOTION_PIECES);
    }

    //verifica el tipo de pieza para la promocion
    public boolean isValidPromotionType(PieceType pieceType) {
        return VALID_PROMOTION_PIECES.contains(pieceType);
    }

    //obtiene el tipo de promocion
    public PieceType getDefaultPromotionType() {
        return PieceType.QUEEN;
    }

    //verifica si la pieza es un peon
    private boolean isPawnPiece(Piece piece) {
        return piece != null && piece.type() == PieceType.PAWN;
    }

    //verifica si la posicion esta en la fila
    private boolean isPromotionRank(Position position, Piece piece, Board board) {
        if (piece.isWhite()) {
            return position.row() == board.getDimensions().getMaxRow();
        } else {
            return position.row() == 0;
        }
    }

    //crea un movimiento apopiado para cada tipo
    private CompositeMovementStrategy createMovementStrategy(PieceType pieceType) {
        return switch (pieceType) {
            case QUEEN -> new CompositeMovementStrategy(
                    new LinearMovementStrategy(),
                    new DiagonalMovementStrategy()
            );
            case ROOK -> new CompositeMovementStrategy(Collections.singletonList(new LinearMovementStrategy()));
            case BISHOP -> new CompositeMovementStrategy(Collections.singletonList(new DiagonalMovementStrategy()));
            case KNIGHT -> throw new UnsupportedOperationException("Knight strategy not implemented yet");
            default -> throw new IllegalArgumentException("Invalid piece type for promotion: " + pieceType);
        };
    }

    //verifica si un poen esta cerca de de promocionar
    public boolean isNearPromotion(Position position, Piece piece, Board board) {
        if (!isPawnPiece(piece)) {
            return false;
        }

        if (piece.isWhite()) {
            return position.row() == board.getDimensions().getMaxRow() - 1;
        } else {
            return position.row() == 1;
        }
    }
}
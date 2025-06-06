package edu.austral.dissis.chess.engine.model;

import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
import edu.austral.dissis.chess.engine.model.domain.piece.PieceType;
import edu.austral.dissis.chess.engine.model.engine.ChessEngine;
import edu.austral.dissis.chess.engine.model.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ChessEngineTest {

    private ChessEngine engine;

    @BeforeEach
    void setUp() {
        engine = ChessEngine.createStandardGame();
    }

    @Test
    void testInitialBoardSetup() {
        // Verificar posiciones iniciales de piezas blancas
        assertEquals(PieceType.ROOK,
                engine.getBoard().getPieceAt(new Position(0, 0)).get().type());
        assertEquals(Color.WHITE,
                engine.getBoard().getPieceAt(new Position(0, 0)).get().color());

        assertEquals(PieceType.KING,
                engine.getBoard().getPieceAt(new Position(0, 4)).get().type());

        // Verificar peones blancos
        for (int col = 0; col < 8; col++) {
            assertEquals(PieceType.PAWN,
                    engine.getBoard().getPieceAt(new Position(1, col)).get().type());
        }
    }

    @Test
    void testValidPawnMove() {
        // Movimiento inicial de peón (2 casillas)
        Result<ChessEngine> result = engine.makeMove(
                new Position(1, 4), new Position(3, 4));

        assertTrue(result.isSuccess());
        ChessEngine newEngine = result.getValue();

        // Verificar que el peón se movió
        assertTrue(newEngine.getBoard().isEmpty(new Position(1, 4)));
        assertTrue(newEngine.getBoard().getPieceAt(new Position(3, 4)).isPresent());
        assertEquals(PieceType.PAWN,
                newEngine.getBoard().getPieceAt(new Position(3, 4)).get().type());
    }

    @Test
    void testInvalidMove() {
        // Intentar mover peón 3 casillas
        Result<ChessEngine> result = engine.makeMove(
                new Position(1, 4), new Position(4, 4));

        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("Invalid move"));
    }

    @Test
    void testKnightMovement() {
        // Mover caballo
        Result<ChessEngine> result = engine.makeMove(
                new Position(0, 1), new Position(2, 2));

        assertTrue(result.isSuccess());
        ChessEngine newEngine = result.getValue();

        assertEquals(PieceType.KNIGHT,
                newEngine.getBoard().getPieceAt(new Position(2, 2)).get().type());
    }

    @Test
    void testTurnSwitch() {
        assertEquals(Color.WHITE, engine.getCurrentPlayer().color());

        Result<ChessEngine> result = engine.makeMove(
                new Position(1, 4), new Position(3, 4));

        assertTrue(result.isSuccess());
        assertEquals(Color.BLACK, result.getValue().getCurrentPlayer().color());
    }

    @Test
    void testCannotMoveOpponentPiece() {
        // Intentar mover pieza negra siendo turno blanco
        Result<ChessEngine> result = engine.makeMove(
                new Position(6, 4), new Position(5, 4));

        assertTrue(result.isError());
        assertTrue(result.getErrorMessage().contains("opponent"));
    }

    @Test
    void testGetValidMoves() {
        // Movimientos válidos del peón inicial
        var validMoves = engine.getValidMoves(new Position(1, 4));

        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(new Position(2, 4)));
        assertTrue(validMoves.contains(new Position(3, 4)));
    }

    @Test
    void testKnightValidMoves() {
        // Movimientos válidos del caballo
        var validMoves = engine.getValidMoves(new Position(0, 1));

        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(new Position(2, 0)));
        assertTrue(validMoves.contains(new Position(2, 2)));
    }

    @Test
    void testPositionCreation() {
        Position pos = Position.at(4, 4);
        assertEquals(4, pos.row());
        assertEquals(4, pos.column());

        assertTrue(pos.isValid());
        assertTrue(pos.isValidFor(8, 8));
        assertFalse(pos.isValidFor(4, 4));
    }

    @Test
    void testPositionOperations() {
        Position pos1 = new Position(2, 3);
        Position pos2 = new Position(4, 5);

        assertEquals(4, pos1.manhattanDistance(pos2));
        assertTrue(pos1.isDiagonal(pos2));
        assertFalse(pos1.isLinear(pos2));
    }

    @Test
    void testResultFunctionalOperations() {
        Result<Integer> success = Result.success(5);
        Result<Integer> error = Result.error("Test error");

        // Test map
        Result<String> mapped = success.map(i -> "Value: " + i);
        assertTrue(mapped.isSuccess());
        assertEquals("Value: 5", mapped.getValue());

        // Test flatMap
        Result<Integer> doubled = success.flatMap(i -> Result.success(i * 2));
        assertTrue(doubled.isSuccess());
        assertEquals(10, doubled.getValue());

        // Test getOrElse
        assertEquals(5, success.getOrElse(0));
        assertEquals(0, error.getOrElse(0));
    }
}
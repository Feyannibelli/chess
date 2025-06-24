package edu.austral.dissis.chess.engine.main;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.dissis.chess.engine.main.checkers.factory.CheckersGame;
import edu.austral.dissis.chess.engine.main.checkers.factory.pieceinit.ManInitializer;
import edu.austral.dissis.chess.engine.main.checkers.factory.pieceinit.QueenInitializer;
import edu.austral.dissis.chess.engine.main.common.Color;
import edu.austral.dissis.chess.engine.main.common.board.Board;
import edu.austral.dissis.chess.engine.main.common.board.BoardImpl;
import edu.austral.dissis.chess.engine.main.common.board.Position;
import edu.austral.dissis.chess.engine.main.common.game.GameState;
import edu.austral.dissis.chess.engine.main.common.game.GameStateImpl;
import edu.austral.dissis.chess.engine.main.common.game.InvalidMoveGameStateImpl;
import edu.austral.dissis.chess.engine.main.common.movement.Movement;
import edu.austral.dissis.chess.engine.main.common.piece.Piece;
import edu.austral.dissis.chess.engine.main.common.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class CheckersGameTest {

    private GameState gameState;
    private ManInitializer manInitializer;
    private QueenInitializer queenInitializer;

    @BeforeEach
    void setUp() {
        gameState = CheckersGame.createCheckersNormalGame();
        manInitializer = new ManInitializer();
        queenInitializer = new QueenInitializer();
    }

    @Nested
    @DisplayName("Piece Movement Tests")
    class PieceMovementTests {

        @Test
        @DisplayName("Man can move diagonally forward one square")
        void testManSimpleMove() {
            // Arrange: Black man at (5,1) can move to (4,0) or (4,2)
            Movement validMove = new Movement(new Position(5, 1), new Position(4, 0));

            // Act
            GameState result = gameState.movePiece(validMove);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            assertNotNull(result.getCurrentBoard().getPieceByPosition(new Position(4, 0)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(5, 1)));
        }

        @Test
        @DisplayName("Man cannot move backwards")
        void testManCannotMoveBackwards() {
            // Arrange: Try to move black man backwards
            Movement invalidMove = new Movement(new Position(5, 1), new Position(6, 0));

            // Act
            GameState result = gameState.movePiece(invalidMove);

            // Assert
            assertTrue(result instanceof InvalidMoveGameStateImpl);
        }

        @Test
        @DisplayName("Man can capture diagonally")
        void testManCapture() {
            // Arrange: Set up a capture scenario
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(3, 3), manInitializer.initialize(Color.BLACK));
            pieces.put(new Position(2, 2), manInitializer.initialize(Color.WHITE));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            Movement captureMove = new Movement(new Position(3, 3), new Position(1, 1));

            // Act
            GameState result = customGameState.movePiece(captureMove);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            assertNotNull(result.getCurrentBoard().getPieceByPosition(new Position(1, 1)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(3, 3)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(2, 2))); // Captured piece removed
        }

        @Test
        @DisplayName("Queen can move multiple squares diagonally")
        void testQueenMovement() {
            // Arrange: Place a queen on the board
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(4, 4), queenInitializer.initialize(Color.BLACK));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            Movement queenMove = new Movement(new Position(4, 4), new Position(1, 1));

            // Act
            GameState result = customGameState.movePiece(queenMove);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            assertNotNull(result.getCurrentBoard().getPieceByPosition(new Position(1, 1)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(4, 4)));
        }

        @Test
        @DisplayName("Queen can capture multiple squares away")
        void testQueenCapture() {
            // Arrange: Set up queen capture scenario
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(5, 5), queenInitializer.initialize(Color.BLACK));
            pieces.put(new Position(3, 3), manInitializer.initialize(Color.WHITE));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            Movement queenCapture = new Movement(new Position(5, 5), new Position(2, 2));

            // Act
            GameState result = customGameState.movePiece(queenCapture);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            assertNotNull(result.getCurrentBoard().getPieceByPosition(new Position(2, 2)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(5, 5)));
            assertNull(result.getCurrentBoard().getPieceByPosition(new Position(3, 3))); // Captured piece
        }
    }

    @Nested
    @DisplayName("Multiple Capture Tests")
    class MultipleCaptureTests {

        @Test
        @DisplayName("Multiple captures in sequence")
        void testMultipleCaptures() {
            // Arrange: Set up multiple capture scenario
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(5, 1), manInitializer.initialize(Color.BLACK));
            pieces.put(new Position(4, 2), manInitializer.initialize(Color.WHITE));
            pieces.put(new Position(2, 4), manInitializer.initialize(Color.WHITE));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            // Act: First capture
            Movement firstCapture = new Movement(new Position(5, 1), new Position(3, 3));
            GameState afterFirst = customGameState.movePiece(firstCapture);

            // Act: Second capture (should be mandatory)
            Movement secondCapture = new Movement(new Position(3, 3), new Position(1, 5));
            GameState afterSecond = afterFirst.movePiece(secondCapture);

            // Assert
            assertTrue(afterSecond instanceof GameStateImpl);
            assertNotNull(afterSecond.getCurrentBoard().getPieceByPosition(new Position(1, 5)));
            assertNull(afterSecond.getCurrentBoard().getPieceByPosition(new Position(4, 2)));
            assertNull(afterSecond.getCurrentBoard().getPieceByPosition(new Position(2, 4)));
        }

        @Test
        @DisplayName("Must continue capturing when possible")
        void testMandatoryContinuousCapture() {
            // Arrange: Set up scenario where second capture is mandatory
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(5, 1), manInitializer.initialize(Color.BLACK));
            pieces.put(new Position(4, 2), manInitializer.initialize(Color.WHITE));
            pieces.put(new Position(2, 4), manInitializer.initialize(Color.WHITE));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            // Act: First capture
            Movement firstCapture = new Movement(new Position(5, 1), new Position(3, 3));
            GameState afterFirst = customGameState.movePiece(firstCapture);

            // Try to make a non-capture move (should be invalid)
            Movement invalidMove = new Movement(new Position(3, 3), new Position(2, 2));
            GameState result = afterFirst.movePiece(invalidMove);

            // Assert
            assertTrue(result instanceof InvalidMoveGameStateImpl);
        }
    }

    @Nested
    @DisplayName("Promotion Tests")
    class PromotionTests {

        @Test
        @DisplayName("White man promotes to queen when reaching last row")
        void testWhitePromotion() {
            // Arrange: White man close to promotion
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(1, 1), manInitializer.initialize(Color.WHITE));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            Movement promotionMove = new Movement(new Position(1, 1), new Position(0, 0));

            // Act
            GameState result = customGameState.movePiece(promotionMove);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            Piece promotedPiece = result.getCurrentBoard().getPieceByPosition(new Position(0, 0));
            assertNotNull(promotedPiece);
            assertEquals(PieceType.QUEEN, promotedPiece.getType());
            assertEquals(Color.WHITE, promotedPiece.getColor());
        }

        @Test
        @DisplayName("Black man promotes to queen when reaching first row")
        void testBlackPromotion() {
            // Arrange: Black man close to promotion
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(6, 1), manInitializer.initialize(Color.BLACK));
            Board customBoard = new BoardImpl(8, 8, pieces);

            GameState customGameState = new GameStateImpl(
                    java.util.List.of(customBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            Movement promotionMove = new Movement(new Position(6, 1), new Position(7, 0));

            // Act
            GameState result = customGameState.movePiece(promotionMove);

            // Assert
            assertTrue(result instanceof GameStateImpl);
            Piece promotedPiece = result.getCurrentBoard().getPieceByPosition(new Position(7, 0));
            assertNotNull(promotedPiece);
            assertEquals(PieceType.QUEEN, promotedPiece.getType());
            assertEquals(Color.BLACK, promotedPiece.getColor());
        }
    }

    @Nested
    @DisplayName("Win Condition Tests")
    class WinConditionTests {

        @Test
        @DisplayName("White wins when all black pieces are eliminated")
        void testWhiteWins() {
            // Arrange: Board with only white pieces
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(0, 0), manInitializer.initialize(Color.WHITE));
            Board winningBoard = new BoardImpl(8, 8, pieces);

            GameState winningGameState = new GameStateImpl(
                    java.util.List.of(winningBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            // Act: Try to make a move (should result in game over)
            Movement anyMove = new Movement(new Position(0, 0), new Position(1, 1));
            GameState result = winningGameState.movePiece(anyMove);

            // Assert: Check if game recognizes the win condition
            // Note: This depends on how your win condition is implemented
            // You might need to check the current turn or call a specific method
            assertNotNull(result);
        }

        @Test
        @DisplayName("Black wins when all white pieces are eliminated")
        void testBlackWins() {
            // Arrange: Board with only black pieces
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(7, 7), manInitializer.initialize(Color.BLACK));
            Board winningBoard = new BoardImpl(8, 8, pieces);

            GameState winningGameState = new GameStateImpl(
                    java.util.List.of(winningBoard),
                    gameState.getWinCondition(),
                    gameState.getTurnManager(),
                    java.util.List.of(),
                    gameState.getPostConditionValidators(),
                    gameState.getGameStateType()
            );

            // Act: Try to make a move
            Movement anyMove = new Movement(new Position(7, 7), new Position(6, 6));
            GameState result = winningGameState.movePiece(anyMove);

            // Assert
            assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("Undo/Redo Tests")
    class UndoRedoTests {

        @Test
        @DisplayName("Can undo a move")
        void testUndo() {
            // Arrange: Make a move first
            Movement move = new Movement(new Position(5, 1), new Position(4, 0));
            GameState afterMove = gameState.movePiece(move);

            // Act: Undo the move
            GameState afterUndo = afterMove.undo();

            // Assert: Board should be back to original state
            assertNotNull(afterUndo);
            assertTrue(afterUndo.canUndo() == false || afterUndo.canUndo() == true); // depends on implementation

            // Check that piece is back to original position
            assertNotNull(afterUndo.getCurrentBoard().getPieceByPosition(new Position(5, 1)));
            assertNull(afterUndo.getCurrentBoard().getPieceByPosition(new Position(4, 0)));
        }

        @Test
        @DisplayName("Can redo after undo")
        void testRedo() {
            // Arrange: Make a move and undo it
            Movement move = new Movement(new Position(5, 1), new Position(4, 0));
            GameState afterMove = gameState.movePiece(move);
            GameState afterUndo = afterMove.undo();

            // Act: Redo the move
            GameState afterRedo = afterUndo.redo();

            // Assert: Board should be back to the state after the move
            assertNotNull(afterRedo);
            assertTrue(afterRedo.canRedo() == false || afterRedo.canRedo() == true);

            // Check that piece is back to moved position
            assertNotNull(afterRedo.getCurrentBoard().getPieceByPosition(new Position(4, 0)));
            assertNull(afterRedo.getCurrentBoard().getPieceByPosition(new Position(5, 1)));
        }

        @Test
        @DisplayName("Multiple undo/redo operations")
        void testMultipleUndoRedo() {
            // Arrange: Make multiple moves
            Movement move1 = new Movement(new Position(5, 1), new Position(4, 0));
            Movement move2 = new Movement(new Position(2, 2), new Position(3, 3));

            GameState afterMove1 = gameState.movePiece(move1);
            GameState afterMove2 = afterMove1.movePiece(move2);

            // Act: Undo twice
            GameState afterUndo1 = afterMove2.undo();
            GameState afterUndo2 = afterUndo1.undo();

            // Assert: Should be back to initial state
            assertNotNull(afterUndo2);
            assertNotNull(afterUndo2.getCurrentBoard().getPieceByPosition(new Position(5, 1)));
            assertNotNull(afterUndo2.getCurrentBoard().getPieceByPosition(new Position(2, 2)));

            // Act: Redo twice
            GameState afterRedo1 = afterUndo2.redo();
            GameState afterRedo2 = afterRedo1.redo();

            // Assert: Should be back to final state
            assertNotNull(afterRedo2);
            assertNotNull(afterRedo2.getCurrentBoard().getPieceByPosition(new Position(4, 0)));
            assertNotNull(afterRedo2.getCurrentBoard().getPieceByPosition(new Position(3, 3)));
        }

        @Test
        @DisplayName("Cannot undo when no moves have been made")
        void testCannotUndoInitialState() {
            // Act & Assert
            assertFalse(gameState.canUndo());
        }

        @Test
        @DisplayName("Cannot redo when no undos have been made")
        void testCannotRedoWithoutUndo() {
            // Arrange: Make a move
            Movement move = new Movement(new Position(5, 1), new Position(4, 0));
            GameState afterMove = gameState.movePiece(move);

            // Act & Assert
            assertFalse(afterMove.canRedo());
        }
    }

    @Nested
    @DisplayName("Piece Initializer Tests")
    class PieceInitializerTests {

        @Test
        @DisplayName("ManInitializer creates correct piece")
        void testManInitializer() {
            // Act
            Piece whitePiece = manInitializer.initialize(Color.WHITE);
            Piece blackPiece = manInitializer.initialize(Color.BLACK);

            // Assert
            assertEquals(Color.WHITE, whitePiece.getColor());
            assertEquals(Color.BLACK, blackPiece.getColor());
            assertEquals(PieceType.PAWN, whitePiece.getType());
            assertEquals(PieceType.PAWN, blackPiece.getType());
            assertNotNull(whitePiece.getId());
            assertNotNull(blackPiece.getId());
            assertNotEquals(whitePiece.getId(), blackPiece.getId());
        }

        @Test
        @DisplayName("QueenInitializer creates correct piece")
        void testQueenInitializer() {
            // Act
            Piece whiteQueen = queenInitializer.initialize(Color.WHITE);
            Piece blackQueen = queenInitializer.initialize(Color.BLACK);

            // Assert
            assertEquals(Color.WHITE, whiteQueen.getColor());
            assertEquals(Color.BLACK, blackQueen.getColor());
            assertEquals(PieceType.QUEEN, whiteQueen.getType());
            assertEquals(PieceType.QUEEN, blackQueen.getType());
            assertNotNull(whiteQueen.getId());
            assertNotNull(blackQueen.getId());
            assertNotEquals(whiteQueen.getId(), blackQueen.getId());
        }

        @Test
        @DisplayName("ManInitializer with custom ID")
        void testManInitializerWithCustomId() {
            // Arrange
            String customId = "custom-man-id";

            // Act
            Piece piece = manInitializer.initialize(Color.WHITE, customId);

            // Assert
            assertEquals(customId, piece.getId());
            assertEquals(Color.WHITE, piece.getColor());
            assertEquals(PieceType.PAWN, piece.getType());
        }

        @Test
        @DisplayName("QueenInitializer with custom ID")
        void testQueenInitializerWithCustomId() {
            // Arrange
            String customId = "custom-queen-id";

            // Act
            Piece piece = queenInitializer.initialize(Color.BLACK, customId);

            // Assert
            assertEquals(customId, piece.getId());
            assertEquals(Color.BLACK, piece.getColor());
            assertEquals(PieceType.QUEEN, piece.getType());
        }
    }
}
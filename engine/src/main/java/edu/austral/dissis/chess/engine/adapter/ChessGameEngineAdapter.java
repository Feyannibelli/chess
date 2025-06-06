package edu.austral.dissis.chess.engine.adapter;

import edu.austral.dissis.chess.engine.model.engine.ChessEngine;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.result.Result;
import edu.austral.dissis.chess.gui.*;

import java.util.List;
import java.util.Optional;

public class ChessGameEngineAdapter implements GameEngine {
    private ChessEngine chessEngine;

    public ChessGameEngineAdapter() {
        this.chessEngine = ChessEngine.createStandardGame();
    }

    @Override
    public MoveResult applyMove(edu.austral.dissis.chess.gui.Move move) {
        try {
            Position from = PositionAdapter.fromGui(move.getFrom());
            Position to = PositionAdapter.fromGui(move.getTo());

            Result<ChessEngine> result = chessEngine.makeMove(from, to);

            return result
                    .map(newEngine -> {
                        chessEngine = newEngine;
                        return createMoveResult();
                    })
                    .getOrElseGet(errorMsg ->
                            new MoveResult(NewGameState.ONGOING, Optional.of(errorMsg)));

        } catch (Exception e) {
            return new MoveResult(NewGameState.ONGOING, Optional.of(e.getMessage()));
        }
    }

    private MoveResult createMoveResult() {
        NewGameState gameState = mapGameState();
        Optional<String> message = chessEngine.getGameResult();
        return new MoveResult(gameState, message);
    }

    private NewGameState mapGameState() {
        return switch (chessEngine.getGameState()) {
            case ONGOING -> NewGameState.ONGOING;
            case CHECK -> NewGameState.ONGOING; // Check is still ongoing
            case CHECKMATE, STALEMATE, DRAW -> NewGameState.FINISHED;
        };
    }

    @Override
    public BoardState getCurrent() {
        return ChessBoardAdapter.toBoardState(chessEngine.getBoard());
    }

    @Override
    public List<edu.austral.dissis.chess.gui.Position> validMoves(
            edu.austral.dissis.chess.gui.Position position) {
        Position internalPosition = PositionAdapter.fromGui(position);

        return chessEngine.getValidMoves(internalPosition)
                .stream()
                .map(PositionAdapter::toGui)
                .toList();
    }

    @Override
    public void init() {
        this.chessEngine = ChessEngine.createStandardGame();
    }

    // Métodos adicionales para debugging
    public String getGameInfo() {
        return String.format(
                "Turn: %d, Current player: %s, Game state: %s",
                chessEngine.getTurnNumber(),
                chessEngine.getCurrentPlayer().id(),
                chessEngine.getGameState()
        );
    }

    public boolean isGameOver() {
        return chessEngine.isGameOver();
    }

    public List<Move> getMoveHistory() {
        return chessEngine.getMoveHistory();
    }
}
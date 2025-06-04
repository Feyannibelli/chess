package edu.austral.dissis.chess.engine.adapter;

import edu.austral.dissis.chess.engine.model.engine.ChessEngine;
import edu.austral.dissis.chess.engine.model.domain.board.Position;
import edu.austral.dissis.chess.engine.model.domain.movement.Move;
import edu.austral.dissis.chess.engine.model.domain.piece.Color;
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

            Move chessMove = new Move(from, to, chessEngine.getCurrentPlayer());
            Result<ChessEngine> result = chessEngine.makeMove(chessMove);

            if (result.isSuccess()) {
                chessEngine = result.getValue();
                return new MoveResult(NewGameState.ONGOING, Optional.empty());
            } else {
                return new MoveResult(NewGameState.ONGOING, Optional.of(result.getErrorMessage()));
            }
        } catch (Exception e) {
            return new MoveResult(NewGameState.ONGOING, Optional.of(e.getMessage()));
        }
    }

    @Override
    public BoardState getCurrent() {
        return ChessBoardAdapter.toBoardState(chessEngine.getBoard());
    }

    @Override
    public List<edu.austral.dissis.chess.gui.Position> validMoves(edu.austral.dissis.chess.gui.Position position) {
        // TODO: Implementar cuando tengamos las estrategias de movimiento
        return List.of();
    }

    @Override
    public void init() {
        this.chessEngine = ChessEngine.createStandardGame();
    }
}
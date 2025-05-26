package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.board.Position;


//representacion de los movimientos
public final class Move {
    private final Position from;
    private final Position to;
    private final Player player;

    //genera un nueco movimiento con la posicion, destino y jugador
    public Move(Position from, Position to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
    }

    //consigue donde se movio la pieza
    public Position getFrom() {
        return from;
    }

    //consigue el destino de la pieza al lugar donde quiere moverse
    public Position getTo() {
        return to;
    }

    //consihue le jugador que realizo el movimiento
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                ", player=" + player.getId() +
                '}';
    }
}
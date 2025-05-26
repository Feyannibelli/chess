package edu.austral.dissis.chess.engine.game;

import edu.austral.dissis.chess.engine.color.Color;

import java.util.Objects;

//representacion del jugador
public final class Player {
    private final String id;
    private final Color color;

    //crea un jugador con un id y color especifico
    public Player(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    //consigue el id del jugador
    public String getId() {
        return id;
    }

    //consigue el color del jugador
    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
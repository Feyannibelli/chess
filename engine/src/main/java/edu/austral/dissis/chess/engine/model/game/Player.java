package edu.austral.dissis.chess.engine.model.game;

import edu.austral.dissis.chess.engine.model.piece.Color;

//representa a un jugador con id y color especifico
public record Player(String id, Color color) {

    public Player {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Player id cannot be null or empty");
        }
        if (color == null) {
            throw new IllegalArgumentException("Player color cannot be null");
        }
    }

    //crea jugador B con id
    public static Player white(String id) {
        return new Player(id, Color.WHITE);
    }

    //crea jugador N con id
    public static Player black(String id) {
        return new Player(id, Color.BLACK);
    }

    //devuelve si el juagdor tiene piezas blancas
    public boolean isWhite() {
        return color.isWhite();
    }

    //devuelve si el jugador tiene piezas negras
    public boolean isBlack() {
        return color.isBlack();
    }

    @Override
    public String toString() {
        return id + " (" + color.name().toLowerCase() + ")";
    }
}
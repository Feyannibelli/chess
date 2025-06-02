package edu.austral.dissis.chess.engine.model.board;

import java.util.Objects;

// representa una posicion en el tablero, coordenadas de filas y columnas
public record Position(int row, int column) {

    //parametros de entrada
    public Position {
        if(row < 0 || column < 0) {
            throw new IllegalArgumentException("Row and column must be non-negative: row=" + row + ", column=" + column);
        }
    }

   //verifica si las posiciones son validas dentro de las dimensiones dadas
   public boolean isValid(BoardDimensions dimensions) {
        return row < dimensions.rows() && column < dimensions.columns();
   }

   //crea nuevas posiciones apartides de los deltas dados
    public Position offset(int deltaRow, int deltaColumn) {
        return new Position(row + deltaRow, column + deltaColumn);
    }

    //representa textual de a1 a2 etc etc
    @Override
    public String toString(){
        char file = (char) ('a' + column);
        return "" + file + (row + 1);
    }
}
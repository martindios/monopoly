package monopoly.casilla.accion;

import monopoly.casilla.Casilla;
import partida.Jugador;

public abstract class Accion extends Casilla {

    /**********Constructor**********/
    public Accion(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }
}

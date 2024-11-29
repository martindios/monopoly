package monopoly.casilla.accion;

import monopoly.casilla.Casilla;
import partida.Jugador;

public abstract class Accion extends Casilla {

    public Accion(String nombre, String tipo, int posicion, Jugador duenho) {
        super(nombre, tipo, posicion, duenho);
    }
}

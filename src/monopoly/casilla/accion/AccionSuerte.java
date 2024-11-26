package monopoly.casilla.accion;

import partida.Jugador;

public class AccionSuerte extends Accion{

    public AccionSuerte(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Suerte", posicion, duenho);
    }

}

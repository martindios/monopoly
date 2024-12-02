package monopoly.casilla.accion;

import partida.Jugador;

public class AccionSuerte extends Accion{

    public AccionSuerte(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {return true;}

}

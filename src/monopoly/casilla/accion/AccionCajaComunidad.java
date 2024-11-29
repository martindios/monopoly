package monopoly.casilla.accion;

import partida.Jugador;

public class AccionCajaComunidad extends Accion{

    public AccionCajaComunidad(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Comunidad", posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {return true;}

}

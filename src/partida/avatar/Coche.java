package partida.avatar;

import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Coche extends Avatar{

    public Coche(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Coche", jugador, lugar, avCreados);
    }
}

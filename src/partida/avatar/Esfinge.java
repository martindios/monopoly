package partida.avatar;

import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Esfinge extends Avatar{

    public Esfinge(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Esfinge", jugador, lugar, avCreados);
    }
}

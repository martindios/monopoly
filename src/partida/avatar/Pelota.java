package partida.avatar;

import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Pelota extends Avatar{

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Pelota", jugador, lugar, avCreados);
    }
}

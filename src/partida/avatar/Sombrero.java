package partida.avatar;

import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Sombrero extends Avatar{

    public Sombrero(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Sombrero", jugador, lugar, avCreados);
    }
}

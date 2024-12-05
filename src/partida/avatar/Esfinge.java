package partida.avatar;

import monopoly.Tablero;
import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Esfinge extends Avatar{

    public Esfinge(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    @Override
    public String infoAvatar() {
        return """
                {
                    Id: %s,
                    Tipo: Esfinge,
                    Casilla: %s,
                    Jugador: %s
                }""".formatted(this.getId(), this.getLugar().getNombre(), this.getJugador().getNombre());
    }

    @Override
    public void moverJugador(Jugador jugadorActual, int valorTirada, Tablero tablero) throws Exception{}

}

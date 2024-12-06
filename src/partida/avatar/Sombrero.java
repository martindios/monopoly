package partida.avatar;

import monopoly.Tablero;
import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public class Sombrero extends Avatar{

    public Sombrero(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    @Override
    public String infoAvatar() {
        return """
                {
                    Id: %s,
                    Tipo: Sombrero,
                    Casilla: %s,
                    Jugador: %s
                }""".formatted(this.getId(), this.getLugar().getNombre(), this.getJugador().getNombre());
    }

    @Override
    public void moverJugador(Jugador jugadorActual, int valorTirada, Tablero tablero) throws Exception{}
}

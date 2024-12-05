package partida.avatar;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.casilla.Casilla;
import monopoly.excepcion.excepcionCarcel.ExcepcionJugadorEnCarcel;
import partida.Jugador;

import java.util.ArrayList;

public class Coche extends Avatar{

    public Coche(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    @Override
    public String infoAvatar() {
        return """
                {
                    Id: %s,
                    Tipo: Coche,
                    Casilla: %s,
                    Jugador: %s
                }""".formatted(this.getId(), this.getLugar().getNombre(), this.getJugador().getNombre());
    }

    /*Método con la lógica del movimiento avanzado Coche*/
    @Override
    public void moverJugador(Jugador jugadorActual, int valorTirada, Tablero tablero) throws Exception {
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            throw new ExcepcionJugadorEnCarcel(", no puede avanzar.");
        }

        if(valorTirada > 4 && Juego.getSaltoMovimiento() > 0) {
            Juego.setTirado(false);
            avatarActual.moverAvanzado(tablero.getPosiciones(), valorTirada);
            if (jugadorActual.getEnCarcel()) {
                Juego.setSaltoMovimiento(0);
                return;
            }
            Juego.setSaltoMovimiento(Juego.getSaltoMovimiento() - 1);
            if(Juego.getSaltoMovimiento() == 0) {
                Juego.setTirado(!Juego.isDadosDobles());
            }
        }

        if (valorTirada <= 4) {
            avatarActual.moverAvanzado(tablero.getPosiciones(), -valorTirada);
            jugadorActual.setNoPuedeTirarDados(2);
            Juego.setSaltoMovimiento(0);
            Juego.setTirado(true);
            if (jugadorActual.getEnCarcel()) {
                Juego.setSaltoMovimiento(0);
            }
        }
    }
}

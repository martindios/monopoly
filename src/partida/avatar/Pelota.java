package partida.avatar;

import monopoly.Juego;
import monopoly.Tablero;
import monopoly.casilla.Casilla;
import monopoly.excepcion.ExcepcionMovimientosAvanzados;
import monopoly.excepcion.excepcionCarcel.ExcepcionJugadorEnCarcel;
import partida.Jugador;
import java.util.ArrayList;

public class Pelota extends Avatar{

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    /*Método con la lógica del movimiento avanzado Pelota*/
    @Override
    public void moverJugador(Jugador jugadorActual, int valorTirada, Tablero tablero) throws Exception {
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            throw new ExcepcionJugadorEnCarcel(", no puede avanzar.");
        }

        if (valorTirada > 4) {
            avatarActual.moverAvanzado(tablero.getPosiciones(), 5);
            if (jugadorActual.getEnCarcel()) {
                Juego.setSaltoMovimiento(0);
                return;
            }

            Juego.setSaltoMovimiento(valorTirada - 5);

        } else {
            avatarActual.moverAvanzado(tablero.getPosiciones(), -1);
            if (jugadorActual.getEnCarcel()) {
                Juego.setSaltoMovimiento(0);
                return;
            }

            Juego.setSaltoMovimiento(-valorTirada + 1);
        }
    }

    /*Método para avanzar con el modo avanzado Pelota*/
    public void avanzar(Jugador jugadorActual, Tablero tablero) throws Exception {
        Avatar avatarActual = jugadorActual.getAvatar();
        if(Juego.getSaltoMovimiento() == 0) {
            throw new ExcepcionMovimientosAvanzados("No hay ningún movimiento pendiente.");
        }
        Juego.setSeHaMovido(true);
        if(Juego.getSaltoMovimiento() > 0) {
            if(Juego.getSaltoMovimiento() == 1) {
                avatarActual.moverAvanzado(tablero.getPosiciones(), 1);
                Juego.setSaltoMovimiento(0);
            } else {
                avatarActual.moverAvanzado(tablero.getPosiciones(), 2);
                if(jugadorActual.getEnCarcel()) {
                    Juego.setSaltoMovimiento(0);
                } else {
                    Juego.setSaltoMovimiento(Juego.getSaltoMovimiento() - 2);
                }
            }
        } else {
            if(Juego.getSaltoMovimiento() == -1) {
                avatarActual.moverAvanzado(tablero.getPosiciones(), -1);
                Juego.setSaltoMovimiento(0);
            }
            else {
                avatarActual.moverAvanzado(tablero.getPosiciones(), -2);
                Juego.setSaltoMovimiento(Juego.getSaltoMovimiento() + 2);
            }
        }
    }

    @Override
    public String infoAvatar() {
        return """
                {
                    Id: %s,
                    Tipo: Pelota,
                    Casilla: %s,
                    Jugador: %s
                }""".formatted(this.getId(), this.getLugar().getNombre(), this.getJugador().getNombre());
    }
}

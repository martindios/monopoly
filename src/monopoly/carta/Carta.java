package monopoly.carta;

import monopoly.ConsolaNormal;
import monopoly.Tablero;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import monopoly.excepcion.excepcionConseguirDinero.ExcepcionConseguirDineroCaja;
import partida.Jugador;
import java.util.ArrayList;

public abstract class Carta {

    /**********Atributos**********/
    private String descripcion;
    private int idCarta;
    public static final ConsolaNormal consolaNormal = new ConsolaNormal();

    /**********Constructores**********/
    /**
     * Constructor de la clase Carta.
     * Inicializa una carta con una descripción y un identificador.
     *
     * @param descripcion La descripción de la carta.
     * @param idCarta El identificador de la carta.
     */
    public Carta(String descripcion, int idCarta) {
        this.descripcion = descripcion;
        this.idCarta = idCarta;
    }

    public Carta() {
    }

    /**********Getters**********/
    public String getDescripcion() {
        return descripcion;
    }

    public int getIdCarta() {
        return idCarta;
    }

    /**********Método**********/
    public abstract void accion(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores,
                                int idCarta) throws ExcepcionCarcel, ExcepcionConseguirDineroCaja;
}

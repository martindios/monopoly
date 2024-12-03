package monopoly.carta;


import monopoly.Baraja;
import monopoly.ConsolaNormal;
import monopoly.Tablero;
import partida.Jugador;
import java.util.ArrayList;

public abstract class Carta {

    /**********Atributos**********/
    private String descripcion;
    private String tipo;
    private int idCarta;
    public static final ConsolaNormal consolaNormal = new ConsolaNormal();



    /**
     * Constructor de la clase Carta.
     * Inicializa una carta con una descripción, tipo y un identificador.
     *
     * @param descripcion La descripción de la carta.
     * @param tipo El tipo de carta [Suerte, Comunidad].
     * @param idCarta El identificador de la carta.
     */
    public Carta(String descripcion, String tipo, int idCarta) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idCarta = idCarta;
    }

    public Carta() {
    }

    public abstract void accion(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores, int idCarta);

    /**********Getters**********/
    public String getDescripcion() {
        return descripcion;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public String getTipo() {
        return tipo;
    }
}

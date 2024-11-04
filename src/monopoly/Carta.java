package monopoly;

public class Carta {

    /**********Atributos**********/
    private String descripcion;
    private String tipo;
    private int idCarta;

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

package monopoly.casilla.propiedad;

import monopoly.Edificio;
import partida.Jugador;

import java.util.ArrayList;

public class Solar extends Propiedad{
    private ArrayList<Edificio> edificios; //Edificios construidos en la casilla

    public Solar(String nombre, int posicion, float valor, Jugador duenho){
        super(nombre, "Solar", posicion, valor, duenho);
        this.setImpuesto(valor * 0.1f);
        this.setImpuestoInicial(this.getImpuesto());
        this.edificios = new ArrayList<Edificio>();
    }

    //getter para devolver la lista de los edificios construídos en la casilla
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    /**
     * Obtiene el número de edificios de un tipo específico en la lista de edificios.
     *
     * @param edificios La lista de edificios a verificar.
     * @param tipoEdificio El tipo de edificio a contar.
     * @return El número de edificios del tipo especificado.
     */
    public int getNumEdificios(ArrayList<Edificio> edificios, String tipoEdificio) {
        int numEdificios = 0;
        for (Edificio edificio : edificios) {
            if (edificio.getTipo().contains(tipoEdificio)) {
                numEdificios++;
            }
        }
        return numEdificios;
    }

    public boolean evaluarSolar(Jugador jugador, Jugador banca) {
        if (this.getDuenho().equals(banca)) {
            return true;
        } else {
            return (jugador.getFortuna() > this.getImpuesto());
        }
    }

    /**
     * Calcula y devuelve el alquiler que debe pagar el jugador actual por el solar.
     * Si el dueño del solar posee todo el grupo de color, el alquiler se duplica.
     *
     * @param jugadorActual El jugador que debe pagar el alquiler.
     * @param banca El jugador que representa a la banca.
     * @return El alquiler que debe pagar el jugador actual.
     */
    public float pagarAlquilerSolar(Jugador jugadorActual, Jugador banca) {
        float alquiler = 0;
        Jugador duenhoSolar = this.getDuenho();
        if (this.getGrupo().esDuenhoGrupo(duenhoSolar)) { // Comprobar si el dueño del solar es dueño de todo el grupo de color
            alquiler = 2 * this.getImpuesto();
        } else {
            alquiler = this.getImpuesto();
        }
        return alquiler;
    }

    public String infoCasillaPropiedad() {
        float valor = this.getValor();
        float impuestoInicial = this.getImpuestoInicial();
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
                "grupo: " + this.getGrupo().getNombreGrupo() + ",\n" +
                "propietario: " + this.getDuenho().getNombre() + ",\n" +
                "valor: " + valor + ",\n" +
                "alquiler: " + this.getImpuesto() + ",\n" +
                "Hipoteca: " + this.getHipoteca() + ",\n" +
                "valor hotel: " + valor * 0.6 + ",\n" +
                "valor casa: " + valor * 0.6 + ",\n" +
                "valor piscina: " + valor * 0.4 + ",\n" +
                "pista de deporte: " + valor * 1.25 + ",\n" +
                "alquiler una casa: " + impuestoInicial * 5 + ",\n" +
                "alquiler dos casas: " + impuestoInicial * 15 + ",\n" +
                "alquiler tres casas: " + impuestoInicial * 35 + ",\n" +
                "alquiler cuatro casas: " + impuestoInicial * 50 + ",\n" +
                "alquiler hotel: " + impuestoInicial * 70 + ",\n" +
                "alquiler piscina: " + impuestoInicial * 25 + ",\n" +
                "alquiler pista de deporte: " + impuestoInicial * 25 + ",\n" +
                "Edificios construídos: " + listaArray(this.getEdificios());
    }

}

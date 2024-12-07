package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;
import partida.Jugador;

public class Edificio {

    /**********Atributos**********/
    private String idEdificio; //Variable para almacenar el id del edificio, servirá como nombre.
    private float valor; //Valor del edificio (valor de compra).
    private Solar casilla; //Casilla en la que está construído el edificio
    private Jugador propietario; //Almacenamos el propietario del edificio

    /**********Constructor**********/
    public Edificio(String tipo, Solar casilla, int contador) {
        this.idEdificio = generarIdEdificio(tipo, contador);
        this.casilla = casilla;
        propietario = casilla.getDuenho();
    }

    /**********Getters**********/
    public String getIdEdificio() {
        return idEdificio;
    }

    public Solar getCasilla() {
        return casilla;
    }

    public float getValor() {
        return valor;
    }

    /**********Setters**********/

    public void setCasilla(Solar casilla) {
        this.casilla = casilla;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    /**********Métodos**********/

    public String infoEdificio() {
        return """
                {
                \tid: %s,
                \tPropietario: %s,
                \tCasilla: %s,
                \tGrupo: %s,
                \tCoste: %.2f
                }
                """.formatted(idEdificio, propietario.getNombre(), casilla.getNombre(), casilla.getGrupo().getNombreGrupo(), valor);
    }

    public String generarIdEdificio(String tipo, int contadorId){
        return tipo.concat("-").concat(String.valueOf(contadorId));
    }

}

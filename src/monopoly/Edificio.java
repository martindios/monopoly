package monopoly;

import partida.Jugador;

public class Edificio {

    /**********Atributos**********/
    private String tipo; //Tipo de edificio (Casa, Hotel, Piscina, Deporte).
    private String idEdificio; //Variable para almacenar el id del edificio, servirá como nombre.
    private float valor; //Valor del edificio (valor de compra).
    private Casilla casilla; //Casilla en la que está construído el edificio
    private float impuesto; //Factor que multiplica el valor de la casilla (Me refiero, se a casa incrementa un 5% (por ej) o alquiler do solar, aquí almacenamos 0.05)
    private Jugador propietario; //Almacenamos el propietario del edificio

    /**********Constructor**********/
    public Edificio(String tipo, Casilla casilla, int contador) {
        this.tipo = tipo;
        this.idEdificio = generarIdEdificio(this.tipo, contador);
        this.casilla = casilla;
        switch (tipo) {
            case "Casa":
                this.valor = casilla.getValor() * 0.6f;
                //Impuesto, xa que varía en función do número de casas que hay
                break;
            case "Hotel":
                this.valor = casilla.getValor() * 0.6f;
                this.impuesto = casilla.getImpuesto() * 70;
        }
        propietario = casilla.getDuenho();
    }

    /**********Getters**********/
    public String getTipo() {
        return tipo;
    }

    public String getIdEdificio() {
        return idEdificio;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public float getValor() {
        return valor;
    }

    /**********Setters**********/

    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;

    }

    /**********Métodos**********/

    public String infoEdificio() {
        return """
                {
                id: %s,
                Propietario: %s,
                Casilla: %s,
                Grupo: %s,
                Coste: %.2f
                }
                """.formatted(idEdificio, propietario.getNombre(), casilla.getNombre(), casilla.getGrupo().getNombreGrupo(), valor);
    }

    public String generarIdEdificio(String tipo, int contadorId){
        return tipo.concat("-").concat(String.valueOf(contadorId));
    }

}

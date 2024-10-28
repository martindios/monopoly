package monopoly;

import partida.Avatar;
import partida.Jugador;

import java.util.ArrayList;

public class Edificios {

    /**********Atributos**********/
    private String tipo; //Tipo de edificio (Casa, Hotel, Piscina, Deporte).
    private String idEdificio; //Variable para almacenar el id del edificio, servirá como nombre.
    private float valor; //Valor del edificio (valor de compra).
    private Casilla casilla; //Casilla en la que está construído el edificio
    private float impuesto; //Factor que multiplica el valor de la casilla (Me refiero, se a casa incrementa un 5% (por ej) o alquiler do solar, aquí almacenamos 0.05)

    public Edificios(String tipo, Casilla casilla) {
        this.tipo = tipo;
        //this.idEdificio = generarIdEdificio(this.tipo);
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
    }
}

package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;

public class Hotel extends Edificio{

    /**********Constructor**********/
    public Hotel(Solar casilla, int contador) {
        super("Hotel", casilla, contador);
        this.setValor(casilla.getValor() * 0.6f);
    }
}

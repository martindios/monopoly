package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;

public class PistaDeporte extends Edificio{

    /**********Constructor**********/
    public PistaDeporte(Solar casilla, int contador) {
        super("PistaDeporte", casilla, contador);
        this.setValor(casilla.getValor() * 1.25f);
    }
}

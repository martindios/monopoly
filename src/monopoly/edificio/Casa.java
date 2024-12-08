package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;

public class Casa extends Edificio{

    /**********Constructor**********/
    public Casa(Solar casilla, int contador) {
        super("Casa", casilla,  contador);
        this.setValor(casilla.getValor() *0.6f);
    }
}
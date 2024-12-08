package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;

public class Piscina extends Edificio{

    /**********Constructor**********/
    public Piscina(Solar casilla, int contador) {
        super("Piscina", casilla, contador);
        this.setValor(casilla.getValor() * 0.4f);
    }
}

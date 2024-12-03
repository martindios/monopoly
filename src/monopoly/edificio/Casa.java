package monopoly.edificio;

import monopoly.casilla.propiedad.Solar;

public class Casa extends Edificio{

    public Casa(Solar casilla, int contador) {
        super("Casa", casilla,  contador);
        this.setValor(casilla.getValor() *0.6f);
    }
}

/*
switch (tipo) {
            case "Casa":
                this.valor = casilla.getValor() * 0.6f;
                //Impuesto, xa que varía en función do número de casas que hay
                break;
            case "Hotel":
                this.valor = casilla.getValor() * 0.6f;
                this.impuesto = casilla.getImpuesto() * 70;
        }
 */

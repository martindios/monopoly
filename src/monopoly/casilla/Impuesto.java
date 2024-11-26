package monopoly.casilla;

import partida.Jugador;

public class Impuesto extends Casilla {

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, "Impuestos", posicion, duenho);
        this.setImpuesto(impuesto);
    }

    public boolean evaluarImpuesto(Jugador jugador, Jugador banca) {
        return jugador.getFortuna() > this.getImpuesto();
    }

    public String infoCasillaImpuesto() {
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
                "impuesto: " + this.getImpuesto();
    }

}
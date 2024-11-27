package monopoly.casilla;

import partida.Jugador;

public class Impuesto extends Casilla {

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, "Impuestos", posicion, duenho);
        this.setImpuesto(impuesto);
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        return jugador.getFortuna() > this.getImpuesto();
    }

    @Override
    public String infoCasilla() {
        System.out.println(super.infoCasilla());
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
                "impuesto: " + this.getImpuesto();
    }

}
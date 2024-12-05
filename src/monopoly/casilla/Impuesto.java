package monopoly.casilla;

import partida.Jugador;

public class Impuesto extends Casilla {

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, posicion, duenho);
        this.setImpuesto(impuesto);
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        return jugador.getFortuna() > this.getImpuesto();
    }

    @Override
    public String infoCasilla() throws Exception {
        consolaNormal.imprimir(super.infoCasilla());
        return "Tipo: Impuestos,\n" +
                "impuesto: " + this.getImpuesto();
    }

}
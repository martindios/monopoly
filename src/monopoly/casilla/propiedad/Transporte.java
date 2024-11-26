package monopoly.casilla.propiedad;

import partida.Jugador;

import static monopoly.Valor.SUMA_VUELTA;

public class Transporte extends Propiedad{

    public Transporte(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, "Transporte", posicion, valor, duenho);
        this.setImpuesto(SUMA_VUELTA);
    }

    public boolean evaluarTransporte(Jugador jugador, Jugador banca) {
        if(this.getDuenho().equals(banca)) {
            return true;
        }
        return switch (this.getDuenho().getNumTransportes()) {
            case 1 -> jugador.getFortuna() > this.getImpuesto() * 0.25f;
            case 2 -> jugador.getFortuna() > this.getImpuesto() * 0.5f;
            case 3 -> jugador.getFortuna() > this.getImpuesto() * 0.75f;
            case 4 -> jugador.getFortuna() > this.getImpuesto();
            default -> false; // No se debería llegar aquí
        };
    }

    public float pagarAlquilerTransporte(Jugador jugadorActual, Jugador banca) {
        float alquiler = 0;
        Jugador duenhoTransporte = this.getDuenho();
        alquiler = switch (duenhoTransporte.getNumTransportes()) {
            case 1 -> this.getImpuesto() * 0.25f;
            case 2 -> this.getImpuesto() * 0.5f;
            case 3 -> this.getImpuesto() * 0.75f;
            case 4 -> this.getImpuesto();
            default -> alquiler;
        };
        return alquiler;
    }

}

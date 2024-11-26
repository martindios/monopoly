package monopoly.casilla.propiedad;

import partida.Jugador;

import static monopoly.Valor.SUMA_VUELTA;

public class Servicio extends Propiedad {

    public Servicio(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, "Servicios", posicion, valor, duenho);
        this.setImpuesto(SUMA_VUELTA / 200);
    }

    public boolean evaluarServicio(Jugador jugador, Jugador banca, int tirada) {
        if (this.getDuenho().equals(banca)) {
            return true;
        } else {
            return switch (this.getDuenho().getNumServicios()) {
                case 1 -> jugador.getFortuna() > (this.getImpuesto() * 4 * tirada);
                case 2 -> jugador.getFortuna() > (this.getImpuesto() * 10 * tirada);
                default -> false; // No debería llegar aquí
            };
        }
    }

    public float pagarAlquilerServicio(Jugador jugadorActual, Jugador banca, int tirada) {
        float alquiler = 0;
        Jugador duenhoServicios = this.getDuenho();
        alquiler = switch (duenhoServicios.getNumServicios()) {
            case 1 -> this.getImpuesto() * 4 * tirada;
            case 2 -> this.getImpuesto() * 10 * tirada;
            default -> alquiler;
        };
        return alquiler;
    }


}

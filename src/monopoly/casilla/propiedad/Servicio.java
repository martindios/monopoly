package monopoly.casilla.propiedad;

import partida.Jugador;

import static monopoly.Valor.SUMA_VUELTA;

public class Servicio extends Propiedad {

    /**********Constructor**********/
    public Servicio(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, "Servicios", posicion, valor, duenho);
        this.setImpuesto(SUMA_VUELTA / 200);
    }

    /**********Métodos**********/

    /**
     * Evalúa la casilla actual para determinar si el jugador puede pagar el alquiler.
     *
     * @param jugador El jugador que ha caído en la casilla.
     * @param banca El jugador que representa la banca.
     * @param tirada El valor de la tirada de dados.
     * @return true si el jugador puede pagar el alquiler o si la casilla pertenece a la banca o al propio jugador, false en caso contrario.
     */
    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        if (this.getDuenho().equals(banca) || jugador.equals(getDuenho())) {
            return true;
        } else {
            return switch (this.getDuenho().getNumServicios()) {
                case 1 -> jugador.getFortuna() > (this.getImpuesto() * 4 * tirada);
                case 2 -> jugador.getFortuna() > (this.getImpuesto() * 10 * tirada);
                default -> false; // No debería llegar aquí
            };
        }
    }

    /**
     * Devuelve una representación en formato JSON de la casilla en venta.
     *
     * @return Una cadena de texto en formato JSON con el nombre, tipo y valor de la casilla.
     */
    @Override
    public String casillaEnVenta() {
        return """
                {
                    Nombre: %s,
                    Tipo: %s,
                    Valor: %.2f.
                }""".formatted(getNombre(), getTipo(), getValor());
    }

    @Override
    public float calcularAlquiler(Jugador jugadorActual, Jugador banca, int tirada) {
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

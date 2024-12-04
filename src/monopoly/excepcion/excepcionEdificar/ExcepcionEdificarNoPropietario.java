package monopoly.excepcion.excepcionEdificar;

public class ExcepcionEdificarNoPropietario extends RuntimeException {
    public ExcepcionEdificarNoPropietario() {
        super("El jugador no puede edificar, ya que no es el propietario de la casilla.");
    }
}

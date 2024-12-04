package monopoly.excepcion.excepcionEdificar;

public class ExcepcionEdificarNoDinero extends ExcepcionEdificar {
    public ExcepcionEdificarNoDinero(String message) {
        super("El jugador no tiene dinero suficiente para edificar un/a " + message + ".");
    }
}

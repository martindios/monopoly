package monopoly.excepcion.excepcionEdificar;

public class ExcepcionEdificarNoEdificable extends ExcepcionEdificar {
    public ExcepcionEdificarNoEdificable() {
        super("El jugador no está en una casilla edificable.");
    }
}

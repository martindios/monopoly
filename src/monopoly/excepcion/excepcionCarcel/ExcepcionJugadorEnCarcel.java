package monopoly.excepcion.excepcionCarcel;

public class ExcepcionJugadorEnCarcel extends ExcepcionCarcel {
    public ExcepcionJugadorEnCarcel(String message) {
        super( "El jugador está en la cárcel" + message);
    }
}

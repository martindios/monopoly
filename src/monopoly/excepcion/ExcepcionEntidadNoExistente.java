package monopoly.excepcion;

public class ExcepcionEntidadNoExistente extends Exception {
    public ExcepcionEntidadNoExistente(String message) {
        super("El/La " + message + " no existe.");
    }
}

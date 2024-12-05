package monopoly.excepcion;

public class ExcepcionTratoNoPropiedad extends Exception {
    public ExcepcionTratoNoPropiedad(String message) {
        super("La propiedad no pertenece al jugador que " + message + " el trato. No se formaliza el mismo.");
    }
}

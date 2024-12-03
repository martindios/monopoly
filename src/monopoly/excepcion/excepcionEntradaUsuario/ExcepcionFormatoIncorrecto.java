package monopoly.excepcion.excepcionEntradaUsuario;

public class ExcepcionFormatoIncorrecto extends ExcepcionEntradaUsuario{
    public ExcepcionFormatoIncorrecto(String message) {
        super("El formato correcto es: " + message);
    }
}

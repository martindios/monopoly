package monopoly.excepcion.excepcionConseguirDinero;

public class ExcepcionConseguirDineroCaja extends Exception{
    private final float restante;
    public ExcepcionConseguirDineroCaja(float resto) {
        super("El jugador no tiene dinero para afrontar la deuda.");
        this.restante = resto;
    }

    public float getRestante() {
        return restante;
    }
}

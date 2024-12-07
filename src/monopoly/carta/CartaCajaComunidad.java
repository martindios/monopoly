package monopoly.carta;

import monopoly.Tablero;
import monopoly.casilla.Casilla;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import monopoly.excepcion.excepcionConseguirDinero.ExcepcionConseguirDineroCaja;
import partida.Jugador;

import java.util.ArrayList;

public class CartaCajaComunidad extends Carta{
    public CartaCajaComunidad() {
        super();
    }

    public CartaCajaComunidad(String descripcion, String tipo, int idCarta){
        super(descripcion, tipo, idCarta);
    }

    @Override
    public void accion(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores, int idCarta) throws ExcepcionCarcel, ExcepcionConseguirDineroCaja {
        Casilla bote;
        int dinero;

        switch(idCarta) {
            case 1: /*Paga 500000*/
                dinero = 500000;
                if (jugadorActual.getFortuna() >= dinero) {
                    banca.sumarFortuna(dinero);
                    jugadorActual.sumarFortuna(-dinero);
                    jugadorActual.sumarGastos(dinero);
                } else {
                    float restante = dinero - jugadorActual.getFortuna();
                    throw new ExcepcionConseguirDineroCaja(restante);
                }
                break;
            case 2: /*Ve a la Cárcel*/
                jugadorActual.encarcelar(tablero.getPosiciones());
                break;
            case 3: /*Ir a Salida*/
                jugadorActual.getAvatar().moverBasico(tablero.getPosiciones(), tablero.encontrar_casilla("Salida"), true);
                break;
            case 4: /*Cobra 2000000*/
                dinero = 2000000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
            case 5: /*Paga 1000000€ (se paga a la banca)*/
                dinero = 1000000;
                if (jugadorActual.getFortuna() >= dinero) {
                    bote = tablero.encontrar_casilla("Parking");
                    bote.sumarValor(dinero);
                    banca.sumarFortuna(dinero);
                    jugadorActual.sumarFortuna(-dinero);
                    jugadorActual.sumarGastos(dinero);
                } else {
                    float restante = dinero - jugadorActual.getFortuna();
                    throw new ExcepcionConseguirDineroCaja(restante);
                }
                break;
            case 6: /*200000€ pagar a cada jugador*/
                float gastoTotal = 200000 * jugadores.size();
                if (jugadorActual.getFortuna() >= gastoTotal) {
                    for (Jugador jugador: jugadores) {
                        jugador.sumarFortuna(200000);
                    }
                    jugadorActual.sumarFortuna(-gastoTotal);
                    jugadorActual.sumarGastos(gastoTotal);
                } else {
                    float restante = gastoTotal - jugadorActual.getFortuna();
                    throw new ExcepcionConseguirDineroCaja(restante);
                }
                break;
        }
    }
}

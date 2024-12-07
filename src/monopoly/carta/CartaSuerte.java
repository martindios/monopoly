package monopoly.carta;

import monopoly.Tablero;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import partida.Jugador;

import java.util.ArrayList;


public class CartaSuerte extends Carta{
    public CartaSuerte() {
        super();
    }

    public CartaSuerte(String descripcion, String tipo, int idCarta){
        super(descripcion, tipo, idCarta);
    }

    @Override
    public void accion(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores, int idCarta) throws ExcepcionCarcel {
        int dinero;
        switch(idCarta) {
            case 1: /*Ir a Transportes1*/
                jugadorActual.getAvatar().moverBasico(tablero.getPosiciones(), tablero.encontrar_casilla("Trans1"), true);
                break;
            case 2: /*Ir a Solar15, sin cobrar salida*/
                jugadorActual.getAvatar().moverBasico(tablero.getPosiciones(), tablero.encontrar_casilla("Solar15"), false);
                break;
            case 3: /*Cobra 500000€*/
                dinero = 500000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
            case 4: /*Ir a Solar3*/
                jugadorActual.getAvatar().moverBasico(tablero.getPosiciones(), tablero.encontrar_casilla("Solar3"), true);
                break;
            case 5: /*Ve a la Cárcel*/
                jugadorActual.encarcelar(tablero.getPosiciones());
                break;
            case 6: /*Cobra 1000000€*/
                dinero = 1000000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
        }
    }
}

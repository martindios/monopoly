package monopoly.carta;

import monopoly.Tablero;
import monopoly.casilla.Casilla;
import partida.Jugador;
import monopoly.Baraja;

import java.util.ArrayList;


public class CartaSuerte extends Carta{
    public CartaSuerte() {
        super();
    }

    public CartaSuerte(String descripcion, String tipo, int idCarta){
        super(descripcion, tipo, idCarta);
    }

    @Override
    public void accion(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores) {
        int dinero = 0;
        int idCarta = baraja.escogerCartaSuerte();

        switch(idCarta) {
            case 1: /*Ir a Transportes1*/
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Trans1"), true);
                break;
            case 2: /*Ir a Solar15, sin cobrar salida*/
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Solar15"), false);
                break;
            case 3: /*Cobra 500000€*/
                dinero = 500000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
            case 4: /*Ir a Solar3*/
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Solar3"), true);
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

package monopoly;

import monopoly.casilla.propiedad.Propiedad;
import partida.Jugador;

public class Trato {
    private String idTrato;
    private int numTrato;
    private Jugador jugadorPropone;
    private Jugador jugadorRecibe;
    private float dinero;
    private Propiedad propiedad1;
    private Propiedad propiedad2;

    //Cambiar <propiedad_1> por <propiedad_2>
    public Trato(Jugador jugadorPropone, Jugador jugadorRecibe, Propiedad propiedad1, Propiedad propiedad2, int numero) {
        this.idTrato = generarIdTrato(numero);
        this.numTrato = 1;
        this.jugadorPropone = jugadorPropone;
        this.jugadorRecibe = jugadorRecibe;
        this.dinero = 0;
        this.propiedad1 = propiedad1;
        this.propiedad2 = propiedad2;
    }

    //Cambiar <propiedad_1> por <cantidad_dinero>
    public Trato(Jugador jugadorPropone, Jugador jugadorRecibe, Propiedad propiedad1, float dinero, int numero) {
        this.idTrato = generarIdTrato(numero);
        this.numTrato = 2;
        this.jugadorPropone = jugadorPropone;
        this.jugadorRecibe = jugadorRecibe;
        this.dinero = dinero;
        this.propiedad1 = propiedad1;
        this.propiedad2 = null;
    }

    //Cambiar <cantidad_dinero> por <propiedad_1>
    public Trato(Jugador jugadorPropone, Jugador jugadorRecibe, float dinero, Propiedad propiedad1, int numero) {
        this.idTrato = generarIdTrato(numero);
        this.numTrato = 3;
        this.jugadorPropone = jugadorPropone;
        this.jugadorRecibe = jugadorRecibe;
        this.dinero = dinero;
        this.propiedad1 = propiedad1;
        this.propiedad2 = null;
    }

    //Cambiar <propiedad_1> por <propiedad_2> y <cantidad_dinero>
    public Trato(Jugador jugadorPropone, Jugador jugadorRecibe, Propiedad propiedad1, Propiedad propiedad2, float dinero, int numero) {
        this.idTrato = generarIdTrato(numero);
        this.numTrato = 4;
        this.jugadorPropone = jugadorPropone;
        this.jugadorRecibe = jugadorRecibe;
        this.propiedad1 = propiedad1;
        this.dinero = dinero;
        this.propiedad2 = propiedad2;
    }

    //Cambiar <propiedad_1> y <cantidad_dinero> por <propiedad_2>
    public Trato(Jugador jugadorPropone, Jugador jugadorRecibe, Propiedad propiedad1, float dinero, Propiedad propiedad2, int numero) {
        this.idTrato = generarIdTrato(numero);
        this.numTrato = 5;
        this.jugadorPropone = jugadorPropone;
        this.jugadorRecibe = jugadorRecibe;
        this.propiedad1 = propiedad1;
        this.dinero = dinero;
        this.propiedad2 = propiedad2;
    }

    private String generarIdTrato(int numero){
        return "Trato-".concat(String.valueOf(numero));
    }

}

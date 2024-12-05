package monopoly;

import monopoly.casilla.Casilla;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import partida.Jugador;
import monopoly.carta.Carta;
import monopoly.carta.CartaCajaComunidad;
import monopoly.carta.CartaSuerte;


import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Baraja {
    /**********Atributos**********/
    //private final ArrayList<Carta> baraja;
    private final ArrayList<CartaSuerte> barajaSuerte;
    private final ArrayList<CartaCajaComunidad> barajaCajaComunidad;
    private final CartaSuerte cartaSuerte = new CartaSuerte();
    private final CartaCajaComunidad cartaCajaComunidad = new CartaCajaComunidad();
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();



    /**********Constructor**********/
    public Baraja() {
        //this.baraja = new ArrayList<>();
        this.barajaSuerte = new ArrayList<>();
        this.barajaCajaComunidad = new ArrayList<>();

        barajaSuerte.add(new CartaSuerte("Ve al Transportes1 y coge un avión. " +
                "Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 1));

        barajaSuerte.add(new CartaSuerte("Decides hacer un viaje de placer. " +
                "Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.", "Suerte", 2));

        barajaSuerte.add(new CartaSuerte("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.", "Suerte", 3));

        barajaSuerte.add(new CartaSuerte("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 4));

        barajaSuerte.add(new CartaSuerte("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida " +
                "y sin cobrar la cantidad habitual.", "Suerte", 5));

        barajaSuerte.add(new CartaSuerte("¡Has ganado el bote de la lotería! Recibe 1000000€.", "Suerte", 6));


        barajaCajaComunidad.add(new CartaCajaComunidad("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.", "Comunidad", 1));

        barajaCajaComunidad.add(new CartaCajaComunidad("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y" +
                "sin cobrar la cantidad habitual.", "Comunidad", 2));

        barajaCajaComunidad.add(new CartaCajaComunidad("Colócate en la casilla de Salida. Cobra la cantidad habitual.", "Comunidad", 3));

        barajaCajaComunidad.add(new CartaCajaComunidad("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.", "Comunidad", 4));

        barajaCajaComunidad.add(new CartaCajaComunidad("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.", "Comunidad", 5));

        barajaCajaComunidad.add(new CartaCajaComunidad("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.", "Comunidad", 6));

    }

    /**********Métodos**********/

    public void barajar(ArrayList<? extends Carta> baraja) {
        Collections.shuffle(baraja);
    }

    public void evaluarSuerte(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores) throws ExcepcionCarcel {

        barajar(barajaSuerte);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        consolaNormal.imprimir("Cartas de suerte mezcladas: ");
        for(CartaSuerte carta : barajaSuerte) {
            consolaNormal.imprimir(carta.getDescripcion() + carta.getIdCarta());
        }

        consolaNormal.imprimir("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        consolaNormal.leer(); //Limpiar buffer


        consolaNormal.imprimir(barajaSuerte.get(numCarta-1).getDescripcion());

        int idCarta = barajaSuerte.get(numCarta-1).getIdCarta();

        cartaSuerte.accion(banca, jugadorActual, tablero, jugadores, idCarta);

    }


    public void evaluarCajaComunidad(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores) throws ExcepcionCarcel {

        barajar(barajaCajaComunidad);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        consolaNormal.imprimir("Cartas de suerte mezcladas: ");
        for(CartaCajaComunidad carta : barajaCajaComunidad) {
            consolaNormal.imprimir(carta.getDescripcion() + carta.getIdCarta());
        }

        consolaNormal.imprimir("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        consolaNormal.leer(); //Limpiar buffer

        consolaNormal.imprimir(barajaCajaComunidad.get(numCarta-1).getDescripcion());

        int idCarta = barajaCajaComunidad.get(numCarta-1).getIdCarta();

        cartaCajaComunidad.accion(banca, jugadorActual, tablero, jugadores, idCarta);
    }

    /**
     * Método que solicita al usuario introducir un número dentro de un rango específico.
     * Se encarga de validar que la entrada esté dentro de los límites establecidos y
     * maneja excepciones de entrada inválida. Si la entrada es válida, se devuelve el número.
     *
     * @param min El valor mínimo permitido para la entrada.
     * @param max El valor máximo permitido para la entrada.
     * @return El número introducido por el usuario, dentro del rango especificado.
     */
    public int introducirNum(int min, int max){
        int num = -1;
        while (num < min || num > max) {
            consolaNormal.imprimirSinSalto("Introduce un número del " + min + " al " + max + ": ");
            try {
                num = consolaNormal.leerInt();
                if (num < min || num > max) {
                    consolaNormal.imprimir("Introduzca un número dentro del rango");
                } else {
                    return num;
                }
            } catch (InputMismatchException e) {
                consolaNormal.imprimir("Entrada inválida, introduzca un número");
                consolaNormal.leerPalabra();
            }
        }
        return num;
    }


}



package monopoly;

import monopoly.carta.Carta;
import monopoly.carta.CartaCajaComunidad;
import monopoly.carta.CartaSuerte;
import monopoly.casilla.Casilla;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Baraja {
    /**********Atributos**********/
    //private final ArrayList<Carta> baraja;
    private final ArrayList<CartaSuerte> barajaSuerte;
    private final ArrayList<CartaCajaComunidad> barajaCajaComunidad;

    Scanner scanner = new Scanner(System.in);

    /**********Constructor**********/
    /*
    public Baraja() {
        this.baraja = new ArrayList<>();

        baraja.add(new Carta("Ve al Transportes1 y coge un avión. " +
                "Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 1));

        baraja.add(new Carta("Decides hacer un viaje de placer. " +
                "Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.", "Suerte", 2));

        baraja.add(new Carta("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.", "Suerte", 3));

        baraja.add(new Carta("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 4));

        baraja.add(new Carta("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida " +
                "y sin cobrar la cantidad habitual.", "Suerte", 5));

        baraja.add(new Carta("¡Has ganado el bote de la lotería! Recibe 1000000€.", "Suerte", 6));


        baraja.add(new Carta("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.", "Comunidad", 1));

        baraja.add(new Carta("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y" +
                "sin cobrar la cantidad habitual.", "Comunidad", 2));

        baraja.add(new Carta("Colócate en la casilla de Salida. Cobra la cantidad habitual.", "Comunidad", 3));

        baraja.add(new Carta("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.", "Comunidad", 4));

        baraja.add(new Carta("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.", "Comunidad", 5));

        baraja.add(new Carta("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.", "Comunidad", 6));

    }*/
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

    private void barajar(ArrayList<?> baraja) {
        Collections.shuffle(baraja);
    }

    public int escogerCartaSuerte(){

        barajar(barajaSuerte);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        System.out.println("Cartas de suerte mezcladas: ");
        for(CartaSuerte carta : barajaSuerte) {
            System.out.println(carta.getDescripcion() + carta.getIdCarta());
        }

        System.out.println("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        scanner.nextLine(); //Limpiar buffer

        System.out.println(barajaSuerte.get(numCarta-1).getDescripcion());

        int idCarta = barajaSuerte.get(numCarta-1).getIdCarta(), dinero=0;

        return idCarta;
    }

    public int escogerCartaCajaComunidad(){

        barajar(barajaCajaComunidad);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        System.out.println("Cartas de suerte mezcladas: ");
        for(CartaCajaComunidad carta : barajaCajaComunidad) {
            System.out.println(carta.getDescripcion() + carta.getIdCarta());
        }

        System.out.println("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        scanner.nextLine(); //Limpiar buffer

        System.out.println(barajaCajaComunidad.get(numCarta-1).getDescripcion());

        int idCarta = barajaCajaComunidad.get(numCarta-1).getIdCarta(), dinero=0;

        return idCarta;
    }

    /**
     * Evalúa una carta de "Suerte" para el jugador actual.
     *
     * @param banca         El jugador banca.
     * @param jugadorActual El jugador actual.
     * @param tablero       El tablero del juego.
     */
    /*public void evaluarSuerte(Jugador banca, Jugador jugadorActual, Tablero tablero) {
        Casilla bote;
        int dinero = 0;
        int idCarta = escogerCartaSuerte();

        switch(idCarta) {
            case 1: //Ir a Transportes1
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Trans1"), true);
                break;
            case 2: //Ir a Solar15, sin cobrar salida
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Solar15"), false);
                break;
            case 3: //Cobra 500000€
                dinero = 500000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
            case 4: //Ir a Solar3
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Solar3"), true);
                break;
            case 5: //Ve a la Cárcel
                jugadorActual.encarcelar(tablero.getPosiciones());
                break;
            case 6: //Cobra 1000000€
                dinero = 1000000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
        }
    }*/

    /**
     * Evalúa una carta de "Comunidad" para el jugador actual.
     *
     * @param banca         El jugador banca.
     * @param jugadorActual El jugador actual.
     * @param tablero       El tablero del juego.
     * @param jugadores     La lista de jugadores.
     */
    /*public void evaluarComunidad(Jugador banca, Jugador jugadorActual, Tablero tablero, ArrayList<Jugador> jugadores) {
        Casilla bote;
        int dinero = 0;
        int idCarta = escogerCartaCajaComunidad();

        switch(idCarta) {
            case 1: //Paga 500000
                dinero = 500000;
                if (jugadorActual.getFortuna() >= dinero) {
                    banca.sumarFortuna(-dinero);
                    jugadorActual.sumarFortuna(dinero);
                } else {
                    float restante = dinero - jugadorActual.getFortuna();
                    //menu.conseguirDinero(restante); //CAMBIAR ESTO
                }
                break;
            case 2: //Ve a la Cárcel
                jugadorActual.encarcelar(tablero.getPosiciones());
                break;
            case 3: //Ir a Salida
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Salida"), true);
                break;
            case 4: //Cobra 2000000
                dinero = 2000000;
                banca.sumarFortuna(-dinero);
                jugadorActual.sumarFortuna(dinero);
                jugadorActual.sumarPremiosInversionesOBote(dinero);
                break;
            case 5: //Paga 1000000€ (se paga a la banca)
                dinero = 1000000;
                if (jugadorActual.getFortuna() >= dinero) {
                    bote = tablero.encontrar_casilla("Parking");
                    bote.sumarValor(dinero);
                    banca.sumarFortuna(dinero);
                    jugadorActual.sumarFortuna(-dinero);
                    jugadorActual.sumarGastos(dinero);
                } else {
                    float restante = dinero - jugadorActual.getFortuna();
                    //menu.conseguirDinero(restante); //CAMBIAR ESTO
                }
                break;
            case 6: //200000€ pagar a cada jugador
                float gastoTotal = 200000 * jugadores.size();
                if (jugadorActual.getFortuna() >= gastoTotal) {
                    for (Jugador jugador: jugadores) {
                        jugador.sumarFortuna(200000);
                    }
                    jugadorActual.sumarFortuna(-gastoTotal);
                    jugadorActual.sumarGastos(gastoTotal);
                } else {
                    float restante = gastoTotal - jugadorActual.getFortuna();
                    //menu.conseguirDinero(restante); //CAMBIAR ESTO
                }
                break;
        }
    }*/

    /**
     * Método que solicita al usuario introducir un número dentro de un rango específico.
     * Se encarga de validar que la entrada esté dentro de los límites establecidos y
     * maneja excepciones de entrada inválida. Si la entrada es válida, se devuelve el número.
     *
     * @param min El valor mínimo permitido para la entrada.
     * @param max El valor máximo permitido para la entrada.
     * @return El número introducido por el usuario, dentro del rango especificado.
     */
    private int introducirNum(int min, int max){
        int num = -1;
        while (num < min || num > max) {
            System.out.print("Introduce un número del " + min + " al " + max + ": ");
            try {
                num = scanner.nextInt();
                if (num < min || num > max) {
                    System.out.println("Introduzca un número dentro del rango");
                } else {
                    return num;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, introduzca un número");
                scanner.next();
            }
        }
        return num;
    }

}



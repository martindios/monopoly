package monopoly;

import partida.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import static monopoly.Valor.SUMA_VUELTA;


public class Baraja {

    private ArrayList<Carta> barajaSuerte;
    private ArrayList<Carta> barajaComunidad;
    Scanner scanner = new Scanner(System.in);

    public Baraja() {
        this.barajaSuerte = new ArrayList<>();
        this.barajaComunidad = new ArrayList<>();

        barajaSuerte.add(new Carta("Ve al Transportes1 y coge un avión. " +
                "Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 1));

        barajaSuerte.add(new Carta("Decides hacer un viaje de placer. " +
                "Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.", "Suerte", 2));

        barajaSuerte.add(new Carta("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.", "Suerte", 3));

        barajaSuerte.add(new Carta("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.", "Suerte", 4));

        barajaSuerte.add(new Carta("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida " +
                "y sin cobrar la cantidad habitual.", "Suerte", 5));

        barajaSuerte.add(new Carta("¡Has ganado el bote de la lotería! Recibe 1000000€.", "Suerte", 6));


        barajaComunidad.add(new Carta("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.", "Comunidad", 1));

        barajaComunidad.add(new Carta("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y" +
                "sin cobrar la cantidad habitual.", "Comunidad", 2));

        barajaComunidad.add(new Carta("Colócate en la casilla de Salida. Cobra la cantidad habitual.", "Comunidad", 3));

        barajaComunidad.add(new Carta("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.", "Comunidad", 4));

        barajaComunidad.add(new Carta("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.", "Comunidad", 5));

        barajaComunidad.add(new Carta("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.", "Comunidad", 6));

    }

    public ArrayList<Carta> getBarajaSuerte() {
        return barajaSuerte;
    }

    public void setBarajaSuerte(ArrayList<Carta> barajaSuerte) {
        this.barajaSuerte = barajaSuerte;
    }

    public ArrayList<Carta> getBarajaComunidad() {
        return barajaComunidad;
    }

    public void setBarajaComunidad(ArrayList<Carta> barajaComunidad) {
        this.barajaComunidad = barajaComunidad;
    }

    private void barajar(ArrayList<Carta> baraja) {
        Collections.shuffle(baraja);
    }

    public void evaluarSuerte(Jugador jugadorActual, Tablero tablero) {

        barajar(barajaSuerte);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        System.out.println("Cartas de suerte mezcladas: ");
        for(Carta carta : barajaSuerte) {
            System.out.println(carta.getDescripcion() + carta.getIdCarta());
        }

        System.out.println("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        scanner.nextLine(); //Limpiar buffer

        System.out.println(barajaSuerte.get(numCarta-1).getDescripcion());

        int idCarta = barajaSuerte.get(numCarta-1).getIdCarta();
        switch(idCarta) {
            case 1:
                jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), tablero.encontrar_casilla("Trans1"));
                break;
            case 2: //HACER LUEGO
                break;
            case 3:
                jugadorActual.sumarFortuna(500000);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

    public void evaluarComunidad() {

        barajar(barajaComunidad);

        //DEPURACIÓN (poder ver las cartas mezcladas para poder elegir a conveniencia)
        System.out.println("Cartas de comunidad mezcladas: ");
        for(Carta carta : barajaComunidad) {
            System.out.println(carta.getDescripcion() + carta.getIdCarta());
        }

        System.out.println("-----Número a elegir de carta-----");
        int numCarta = introducirNum(1, 6);
        scanner.nextLine(); //Limpiar buffer

        System.out.println(barajaComunidad.get(numCarta-1).getDescripcion());

        int idCarta = barajaComunidad.get(numCarta-1).getIdCarta();
        switch(idCarta) {
            case 1:
                break;
            case 2:
                break;
        }
    }

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



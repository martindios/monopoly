package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

import partida.*;

//En esta clase no se necesitan getters, setters ya que solo llama a métodos de otras clases

public class Menu {

    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado


    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.


    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        System.out.println("Indica el nombre del jugador.");
        System.out.print("->");
        String nombre = scanner.nextLine();
        //Comprobar jugador

        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) { //getter de getNombre de jugador
                System.out.println(jugador.info());
                return;
            }
        }
        System.out.println("Error. El jugador no existe.");
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {

        //Comprueba que el ID que se pide describir es uno existente
        for (Avatar avatar : avatares) {
            if (avatar.getId().equalsIgnoreCase(ID)) {
                System.out.println(avatar.infoAvatar());
                return;
            }
        }
        System.out.println("Error. El avatar no existe.");
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    public void descCasilla(){
        System.out.println("Introduce el nombre de la casilla a describir:");
        System.out.print("->");
        String casilla = scanner.nextLine();
        //Comprobar si la casilla es correcta

        System.out.println(tablero.describirCasilla(casilla));
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        System.out.println("Jugadores:");
        String toString = "";
        for (Jugador jugador : jugadores) {
            toString += jugador.info();
        }
        System.out.println(toString);
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        System.out.println("Avatares:");
        String toString = "";
        for (Avatar avatar : avatares) {
            toString += avatar.infoAvatar();
        }
        System.out.println(toString);
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}

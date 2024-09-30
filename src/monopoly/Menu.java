package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

import partida.*;

//En esta clase no se necesitan getters, setters ya que solo llama a métodos de otras clases
import java.util.Scanner;
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

    public Menu() {
        this.jugadores = new ArrayList<>();
        this.avatares = new ArrayList<>();
        this.turno = 0;
        this.lanzamientos = 0;
        this.tirado = false;
        this.solvente = true;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.banca = new Jugador();
        this.tablero = new Tablero(banca);

        ejecutarMenu();
    }

    // Metodo que mostra o menu
    private void ejecutarMenu() {
        int opcion = -1;
        Scanner scanner = new Scanner(System.in);

        while (opcion != 0) {
            mostrarMenu();  // Mostramos o menu antes das accions
            opcion = scanner.nextInt();  // Leemos o input
            ejecutarOpcion(opcion);      // Ejecutamos
        }
    }

    // Método que mostra o mnú
    private void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====");
        System.out.println("1. Iniciar Partida");
        System.out.println("2. Describir Jugador");
        System.out.println("3. Describir Avatar");
        System.out.println("4. Describir Casilla");
        System.out.println("5. Lanzar Dados");
        System.out.println("6. Comprar Casilla");
        System.out.println("7. Salir de la Cárcel");
        System.out.println("8. Listar Propiedades en Venta");
        System.out.println("9. Listar Jugadores");
        System.out.println("10. Listar Avatares");
        System.out.println("11. Acabar Turno");
        System.out.println("0. Salir del Juego");
    }

    // Metodo que ten as opcions do xogador
    //Debemos modificar esto (creo) xa que a opcion de iniciar a partida non se debería mostrar en pantalla, debería ser algo auto
    private void ejecutarOpcion(int opcion) {
        Scanner scanner = new Scanner(System.in);

        switch (opcion) {
            case 1:
                iniciarPartida();
                break;
            case 2:
                System.out.println("Introduce el nombre del jugador:");
                String nombreJugador = scanner.nextLine();
                descJugador(new String[]{nombreJugador});
                break;
            case 3:
                System.out.println("Introduce el ID del avatar:");
                String idAvatar = scanner.nextLine();
                descAvatar(idAvatar);
                break;
            case 4:
                System.out.println("Introduce el nombre de la casilla:");
                String nombreCasilla = scanner.nextLine();
                //descCasilla(nombreCasilla);
                break;
            case 5:
                lanzarDados();
                break;
            case 6:
                System.out.println("Introduce el nombre de la casilla a comprar:");
                String nombreCompra = scanner.nextLine();
                comprar(nombreCompra);
                break;
            case 7:
                salirCarcel();
                break;
            case 8:
                listarVenta();
                break;
            case 9:
                listarJugadores();
                break;
            case 10:
                listarAvatares();
                break;
            case 11:
                acabarTurno();
                break;
            case 0:
                System.out.println("¡Gracias por jugar!");
                break;
            default:
                System.out.println("Opción no válida. Por favor, elige una opción válida.");
                break;
        }
    }


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

        //System.out.println(tablero.describirCasilla(casilla));
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

    private void DarAltaJugador(String nombre, String tipoAvatar){
        //Jugador jugador = new Jugador(nombre, tipoAvatar);
        Avatar avatar = new Avatar(avatares);
        //avatar.
        //jugadores.add(jugador);


    }

}

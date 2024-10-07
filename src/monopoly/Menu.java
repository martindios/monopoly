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

        iniciarPartida();
    }



    //SETTERS
    public void setTurno(int turno) {
        this.turno = turno;
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        System.out.println("Iniciando partida");
        System.out.println("Introduce el comando: ");
        String comando = scanner.nextLine();
        analizarComando(comando);

    }
    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    public void analizarComando(String comando) {
        String[] palabrasArray = comando.split(" ");
        if (palabrasArray.length > 0) {
            switch (palabrasArray[0]) {

                case "listar": //listar jugador //listar avatar // listar enventa
                    switch (palabrasArray[1]){
                        case "jugadores":
                            listarJugadores();
                        case "avatares":
                            listarAvatares();
                        case "enventa":
                            listarVenta();
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;

                case "crear":
                    switch (palabrasArray[1]){
                        case "jugador":
                            darAltaJugador(palabrasArray[2], palabrasArray[3]);
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;

                case "jugador":
                    System.out.println("Tiene el turno: " + jugadores.get(turno));
                    break;

                case "lanzar":
                    lanzarDados();
                    break;

                case "acabar":
                    acabarTurno();
                    break;

                case "salir":
                    salirCarcel();
                    break;

                case "describir":
                    switch (palabrasArray[1]){
                        case "jugador":
                            descJugador(palabrasArray);
                            break;
                        case "avatar":
                            descAvatar(palabrasArray[2]);
                        default:
                            descCasilla(palabrasArray[1]);
                            break;
                    }
                    break;

                case "comprar":
                    comprar(palabrasArray[1]);
                    break;

                case "ver":
                    switch (palabrasArray[1]){
                        case "tablero":
                            System.out.println(tablero.toString());
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }

                default:
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        /*System.out.println("Indica el nombre del jugador.");
        System.out.print("->");
        String nombre = scanner.nextLine();
        //Comprobar jugador

        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) { //getter de getNombre de jugador
                System.out.println(jugador.info());
                return;
            }
        }
        System.out.println("Error. El jugador no existe.");*/
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

    public void descCasilla(String NombreCasilla) {
        ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();
        boolean casillaEncontrada = false; // auxiliar

        for (ArrayList<Casilla> fila : casillas) {
            for (Casilla casilla : fila) {
                if (NombreCasilla.equalsIgnoreCase(casilla.getNombre())) {
                    System.out.println(casilla.infoCasilla()); // Llamar al metodo
                    casillaEncontrada = true;
                    break; // Salir do bucle interno ao encontrar a casilla
                }
            }
            if (casillaEncontrada) {
                break; // Salir do bucle externo
            }
        }
        // Se nn se encontra a casilla imprímese por pantalla
        if (!casillaEncontrada) {
            System.out.println("La casilla solicitada no existe.");
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        boolean CasillaEncontrada = false;
        ArrayList<ArrayList<Casilla>> casillas = tablero.getPosiciones();
        Jugador comprador = jugadores.get(turno);
        for(ArrayList<Casilla> fila : casillas) {
            for(Casilla casilla : fila) {
                if(casilla.getNombre().equalsIgnoreCase(nombre)) {
                    CasillaEncontrada = true;
                    casilla.comprarCasilla(comprador, banca);
                }
            }
        }
        if(!CasillaEncontrada) {
            System.out.println("La casilla deseada no existe.");
        }
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
            //toString += jugador.info();
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

    private void darAltaJugador(String nombre, String tipoAvatar){

        Casilla casillaInicio = tablero.encontrar_casilla("Salida"); //Se busca la casilla correspondiente a la salida

        //Se crea el jugador y se añade al array que contiene a todos los participantes de la partida
        Jugador jugadorCreado = new Jugador(nombre, tipoAvatar, casillaInicio, avatares);
        jugadores.add(jugadorCreado);

        //Se muestra por pantalla la información del jugador creado
        System.out.println("{");
        System.out.println("nombre: " + jugadorCreado.getNombre() + ",");
        System.out.println("avatar: " + jugadorCreado.getAvatar().getId());
        System.out.println("}");

    }

}

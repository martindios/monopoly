package monopoly;

import java.util.*;
import partida.*;
import java.util.Scanner;

import static monopoly.Valor.FORTUNA_INICIAL;

/*En esta clase no se necesitan getters, setters ya que solo llama a métodos de otras clases*/
public class Menu {

    /**********Atributos**********/
    private boolean iniciarPartida;

    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private Baraja barajas;
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado
    private int maxJugadores = 0;
    private int jugadoresActuales = 0;
    private boolean finalizarPartida = false;
    private int contadorCasa;
    private int contadorHotel;
    private int contadorPiscina;
    private int contadorPistaDeporte;

    private int saltoMovimiento; //Variable para controlar el movimiento del avatar en modo avanzado
    private boolean seHaMovido; //Booleano para comprobar si el jugador se ha movido en su turno
    private boolean compraMovimientoCoche;

    private boolean dadosDobles;

    /**********Constructor**********/
    public Menu() {
        this.jugadores = new ArrayList<>();
        this.avatares = new ArrayList<>();
        this.barajas = new Baraja();
        this.turno = 0;
        this.lanzamientos = 0;
        this.tirado = false;
        this.solvente = true;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.banca = new Jugador();
        this.saltoMovimiento = 0;
        this.seHaMovido = false;
        this.compraMovimientoCoche = false;
        this.tablero = new Tablero(banca);

        this.iniciarPartida = false;

        preIniciarPartida();
    }

    /**********Métodos**********/



    /*Método para mostrar la pantalla de inicio y crear los jugadores*/
    private void preIniciarPartida() {
        imprimirLogo();

        /*establece el número de jugadores que van a jugar la partida*/
        System.out.println("-----Número de jugadores-----");

        maxJugadores = introducirNum(2, 6);


        crearJugadores();

        iniciarPartida = true;
    }

    public boolean isIniciarPartida() {
        return iniciarPartida;
    }

    /*Método que crea a todos los jugadores que van a jugar*/
    private void crearJugadores() {
        scanner.nextLine(); //Limpiar buffer
        /*Comprobación para que no se exceda el número de jugadores establecido*/
        while (jugadoresActuales < maxJugadores) {
            System.out.print("Introduce el comando: ");
            String comando = scanner.nextLine();
            String[] palabrasArray = comando.split(" ");
            if (palabrasArray.length > 0) {
                if (palabrasArray[0].equals("crear") && palabrasArray[1].equals("jugador")) {
                    if (palabrasArray.length == 4) {
                        //Comprobación de que el jugador no está repetido
                        if (!esJugadorRepetido(palabrasArray[2])) {
                            //Comprobación de que el tipoAvatar es correcto
                            if (esAvatarCorrecto(palabrasArray[3])) {
                                darAltaJugador(palabrasArray[2], palabrasArray[3]);
                                jugadoresActuales++;
                            } else {
                                System.out.println("El avatar introducido no está disponible [Coche, Esfinge, Sombrero, Pelota]");
                            }
                        } else {
                            System.out.println("Jugador ya existente");
                        }
                    } else {
                        System.out.println("El formato correcto es: crear jugador nombre tipoAvatar");
                    }
                } else {
                    System.out.println("Debe primero crear los jugadores para poder jugar");
                }
            }
        }
    }

    /*Método auxiliar para el método 'crearJugadores', saber si el nombre del jugador es repetido o no*/
    private boolean esJugadorRepetido(String nombre) {
        if (jugadores.isEmpty()) {
            return false;
        } else {
            for (Jugador jugador : jugadores) {
                return jugador.getNombre().equals(nombre);
            }
        }
        return true;
    }

    /*Método auxiliar para el método 'crearJugadores', saber si el avatar es del tipo correcto*/
    private boolean esAvatarCorrecto(String tipoAvatar) {
        ArrayList<String> tipoAvatarArray = new ArrayList<>(Arrays.asList("Coche", "Esfinge", "Sombrero", "Pelota"));
        return tipoAvatarArray.contains(tipoAvatar);
    }

    /**
     * Método que registra un nuevo jugador, lo añade a la lista de jugadores
     * e imprime la información del jugador recién creado por pantalla.
     *
     * @param nombre Nombre del jugador a crear.
     * @param tipoAvatar Tipo de avatar que representará al jugador.
     */
    private void darAltaJugador(String nombre, String tipoAvatar){
        Casilla casillaInicio = tablero.encontrar_casilla("Salida"); //Se busca la casilla correspondiente a la salida
        //Se crea el jugador y se añade al array que contiene a todos los participantes de la partida
        Jugador jugadorCreado = new Jugador(nombre, tipoAvatar, casillaInicio, avatares);
        jugadores.add(jugadorCreado);
        //Se muestra por pantalla la información del jugador creado
        System.out.println("{");
        System.out.println("    nombre: " + jugadorCreado.getNombre() + ",");
        System.out.println("    avatar: " + jugadorCreado.getAvatar().getId());
        System.out.println("}");
    }

    /*Método que imprime el logo mediante un for con un efecto visual*/
    private void imprimirLogo() {
        ArrayList<String> array = new ArrayList<>();
        array.add("*************************************************************************************\n");
        array.add(".___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____ \n");
        array.add("|   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   / \n");
        array.add("|  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /  \n");
        array.add("|  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/   \n");
        array.add("|  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |     \n");
        array.add("|__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|     \n");
        array.add("*************************************************************************************\n\n");

        for (String s : array) {
            for (int i = 0; i < s.length(); i++) {
                System.out.print(s.charAt(i));
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("Error en la impresión del logo");
                }
            }
        }
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
package monopoly;

import java.awt.desktop.AppReopenedEvent;
import java.lang.reflect.Array;
import java.util.*;

import partida.*;

//En esta clase no se necesitan getters, setters ya que solo llama a métodos de otras clases
import java.util.Scanner;
public class Menu {

    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado
    int maxJugadores = 0;
    int jugadoresActuales = 0; //Variable auxiliar para limitar el número de jugadores


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

        preIniciarPartida();
    }


    /*"""
                    %s
                    │%10s│%19s│%10s│%19s│%19s│%10s│%19s│%19s│%10s│%19s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│      .___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____       │%19s│
                    │%10s│      |   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   /       │%10s│
                    %s     |  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /    %s
                    │%10s│      |  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/         │%10s│
                    │%10s│      |  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |           │%10s│
                    %s          |__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|       %s
                    │%19s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s
                    │%10s│%19s│%19s│%10s│%19s│%10s│%10s│%19s│%10s│%19s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    %s
                    """*/

    //SETTERS
    public void setTurno(int turno) {
        this.turno = turno;
    }

    private void preIniciarPartida() {
        System.out.println("*************************************************************************************");

        System.out.print(".___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____ \n" +
                "|   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   / \n" +
                "|  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /  \n" +
                "|  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/   \n" +
                "|  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |     \n" +
                "|__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|     \n");
        System.out.println("*************************************************************************************");
        System.out.println("Iniciando partida...");
        while (maxJugadores < 2 || maxJugadores > 6) {
            System.out.print("¿Cuántos jugadores van a ser? [2-6] ");
            try {
                maxJugadores = scanner.nextInt();
                if (maxJugadores < 2 || maxJugadores > 6) {
                    System.out.println("Introduzca un número dentro del rango");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, introduzca un número");
                scanner.next();
            }

        }

        crearJugadores();

        iniciarPartida();
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        while(true) {
            System.out.print("Introduce el comando: ");
            String comando = scanner.nextLine();
            analizarComando(comando);
        }
        //HAY QUE HACER UN SCANNER CLOSE AQUI CUANDO ACABE EL MENÚ, ANTES DE SALIR, AUN NO SE PUEDE PORQUE ESTA EL WHILE TRUE
    }


    private void crearJugadores() {
        scanner.nextLine();
        //Comprobación para que no se exceda el número de jugadores establecido
        while (jugadoresActuales < maxJugadores) {
            System.out.print("Introduce el comando: ");
            String comando = scanner.nextLine();
            String[] palabrasArray = comando.split(" ");
            if (palabrasArray.length > 0) {
                if (palabrasArray[0].equals("crear")) {
                    if (palabrasArray.length == 4) {
                        //Comprobación de que el jugador no está repetido
                        if (!esJugadorRepetido(palabrasArray[2])) {
                            //Comprobación de que el tipoAvatar es correcto
                            if (esAvatarDisponible(palabrasArray[3])) {
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


    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    public void analizarComando(String comando) {
        String[] palabrasArray = comando.split(" ");
        if(palabrasArray.length > 0) {
            switch(palabrasArray[0]) {
                case "crear":
                    System.out.println("Todos los jugadores están registrados");
                    break;
                case "listar": //listar jugador //listar avatar // listar enventa
                    switch (palabrasArray[1]){
                        case "jugadores":
                            listarJugadores();
                            break;
                        case "avatares":
                            listarAvatares();
                            break;
                        case "enventa":
                            listarVenta();
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;

                case "jugador":
                    System.out.println("Tiene el turno: " + (jugadores.get(turno)).getNombre());
                    break;

                case "lanzar":
                    lanzarDados();
                    solvente = jugadores.get(turno).getAvatar().getLugar().evaluarCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
                    //evaluarCasilla
                    break;

                case "acabar":
                    System.out.println(infoTrasTurno(jugadores.get(turno)));
                    acabarTurno();
                    break;

                case "salir carcel":
                    salirCarcel();
                    break;

                case "describir":
                    if(palabrasArray.length == 3) {
                        switch (palabrasArray[1]) {
                            case "jugador":
                                descJugador(palabrasArray[2]);
                                break;
                            case "avatar":
                                descAvatar(palabrasArray[2]);
                                break;
                            default:
                                System.out.println("Comando no válido");
                                break;
                        }
                    }
                    else {
                        descCasilla(palabrasArray[1]);
                    }
                    break;

                case "comprar":
                    if (palabrasArray.length == 2) {
                        comprar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: comprar nombrePropiedad");
                    }
                    break;

                case "ver":
                    if (palabrasArray[1].equals("tablero")) {
                        System.out.println(tablero.toString());
                    } else {
                        System.out.println("Comando no válido");
                    }
                    break;

                case "moveraux":
                    MoverAux(palabrasArray[1]);
                    break;
                default:
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }

    //Método privado para saber si el nombre es repetido o no
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

    private boolean esAvatarDisponible(String tipoAvatar) {
        ArrayList<String> tipoAvatarArray = new ArrayList<>(Arrays.asList("Coche", "Esfinge", "Sombrero", "Pelota"));
        return tipoAvatarArray.contains(tipoAvatar);
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String nombre) {
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

    public void descCasilla(String NombreCasilla) {
        Casilla casillaBuscada = tablero.encontrar_casilla(NombreCasilla);
        if(casillaBuscada == null) {
            System.out.println("La casilla no existe.");
        }
        else {
            System.out.println(casillaBuscada.infoCasilla());
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
        if(!tirado) {
            int valor1 = dado1.hacerTirada();
            int valor2 = dado2.hacerTirada();
            lanzamientos++;
            tirado = true;
            System.out.println("Dado 1: " + valor1);
            System.out.println("Dado 2: " + valor2);
            if(valor1 == valor2) {
                tirado = false;
            }
            Avatar avatar = avatares.get(turno);
            avatar.moverAvatar(tablero.getPosiciones(), (valor1 + valor2));
        }
        else {
            System.out.println("Ya has lanzado el dado en este turno.");
        }
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Jugador comprador = jugadores.get(turno);
        Casilla casillaDeseada = tablero.encontrar_casilla(nombre);
        if(casillaDeseada == null) {
            System.out.println("La casilla deseada no existe.");
            return;
        }
        if(!(casillaDeseada.getTipo().equals("Solar")) && !(casillaDeseada.getTipo().equals("Transporte")) && !(casillaDeseada.getTipo().equals("Servicios"))) {
            System.out.println("Casilla sin opción de compra.");
            return;
        }
        if(comprador.getFortuna() < casillaDeseada.getValor()) {
            System.out.println("El jugador no tiene dinero suficiente para comprar la casilla.");
            return;
        }
        casillaDeseada.comprarCasilla(comprador, banca);
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        Jugador JugActual = jugadores.get(turno);
        boolean TiradasCarcel = JugActual.getTiradasCarcel() < 3;
        float fianza = 0;
        Casilla carcel = tablero.encontrar_casilla("Cárcel");
        fianza = carcel.getImpuesto();
        boolean Presupuesto = JugActual.getFortuna() >= fianza;

        if (JugActual.getAvatar().getLugar() != carcel) {
            System.out.println("El jugador no está en la cárcel. No puede usar el comando.");
            return;
        }

        if (TiradasCarcel && Presupuesto) {
            System.out.println("El jugador puede tirar los dados o pagar la fianza (1/2)");
            int respuesta;
            do {
                try {
                    respuesta = scanner.nextInt();
                    if (respuesta == 1 || respuesta == 2) {
                        break;
                    } else {
                        System.out.println("Valor no válido. Pruebe de nuevo");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor ingrese un número.");
                    scanner.next();
                }
            }while(true);
            if (respuesta == 1) {
                System.out.println("El jugador decide lanzar los dados");
                lanzarDados();
                if (dado1.getValor() == dado2.getValor()) {
                    System.out.println("Dobles! El jugador avanza " + (dado1.getValor() + dado2.getValor()) + " casillas.");
                    JugActual.setEnCarcel(false);
                    JugActual.setTiradasCarcel(0);
                    tirado = false;
                } else {
                    System.out.println("No han salido dobles... El jugador pierde el turno");
                    JugActual.setTiradasCarcel(JugActual.getTiradasCarcel() + 1);
                    acabarTurno();
                }
            } else {
                System.out.println("El jugador paga la fianza. Tiene derecho a usar su turno");
                JugActual.setEnCarcel(false);
                JugActual.setTiradasCarcel(0);
                JugActual.sumarFortuna(-fianza);
                JugActual.sumarGastos(fianza);
                banca.sumarFortuna(fianza);
                tirado = false;
            }
        }
        // Caso 2: Solo puede tirar los dados
        else if (TiradasCarcel) {
            System.out.println("El jugador solo puede tirar los dados.");
            lanzarDados();
            if (dado1.getValor() == dado2.getValor()) {
                System.out.println("Dobles! El jugador avanza " + (dado1.getValor() + dado2.getValor()) + " casillas.");
                JugActual.setEnCarcel(false);
                JugActual.setTiradasCarcel(0);
                tirado = false;
            } else {
                System.out.println("No han salido dobles... El jugador pierde el turno");
                JugActual.setTiradasCarcel(JugActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
        // Caso 3: Solo puede pagar la fianza
        else if (Presupuesto) {
            System.out.println("El jugador solo puede pagar la fianza");
            JugActual.setEnCarcel(false);
            JugActual.setTiradasCarcel(0);
            JugActual.sumarFortuna(-fianza);
            JugActual.sumarGastos(fianza);
            banca.sumarFortuna(fianza);
            tirado = false;
        }
        // Caso 4: No puede tirar los dados ni pagar la fianza (bancarrota)
        else {
            System.out.println("El jugador no puede tirar los dados ni tiene saldo suficiente para pagar la fianza.");
            System.out.println("El jugador se declara en bancarrota");
            solvente = false;
            System.exit(0);
        }
    }


    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for(Casilla c : fila) {
                if((c.getTipo().equals("Solar") || c.getTipo().equals("Transporte") || c.getTipo().equals("Servicios")) && c.getDuenho().equals(banca)) {
                    System.out.println(c.casEnVenta());
                }
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        System.out.println("Jugadores:");
        StringBuilder str = new StringBuilder();
        for (Jugador jugador : jugadores) {
            str.append(jugador.info());
            if (!jugador.equals(jugadores.getLast())) {
                str.append(",\n");
            } else {
                str.append("\n");
            }
        }
        System.out.println(str);
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        System.out.println("Avatares:");
        StringBuilder str = new StringBuilder();
        for (Avatar avatar : avatares) {
            str.append(avatar.infoAvatar());
            if (!avatar.equals(avatares.getLast())) {
                str.append(",\n");
            } else {
                str.append("\n");
            }
        }
        System.out.println(str);
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        //Tiradas carcel xa axustadas na funcion SaliCarcel
        if(!tirado) {
            System.out.println("No puedes acabar turno sin haber lanzado los dados.");
            return;
        }
        Jugador anterior = jugadores.get(turno);
        System.out.println("El jugador " + anterior.getNombre() + ", con avatar " + anterior.getAvatar().getId() + ", acaba su turno.");
        turno = (turno+1)%(jugadores.size());
        lanzamientos = 0;
        tirado = false;
        solvente = true;
        Jugador jugador = jugadores.get(turno);
        System.out.println("El turno le pertenece al jugador " + jugador.getNombre() + ". Con avatar " + jugador.getAvatar().getId() + ".");
    }

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

    private String infoTrasTurno(Jugador jugador) {
        ArrayList<Casilla> props = jugador.getPropiedades();
        System.out.println("El estado financiero de " + jugador.getNombre() + " es:");
        StringBuilder propiedades = new StringBuilder();
        for (Casilla casilla : props) {
            propiedades.append(casilla.getNombre()).append(", "); // Suponiendo que casilla tiene un metodo getNombre()
        }
        // Eliminar la última coma y espacio si hay propiedades
        if (!props.isEmpty()) {
            propiedades.setLength(propiedades.length() - 2);
        } else {
            propiedades.append("Sin propiedades");
        }
        return """
            Fortuna: %.2f
            Gastos: %.2f
            Propiedades: %s
            \n
            """.formatted(jugador.getFortuna(), jugador.getGastos(), propiedades.toString());
    }

    private void MoverAux(String pos) {
        Jugador jugador = jugadores.get(turno);
        Avatar av = jugador.getAvatar();
        int posicion = Integer.parseInt(pos);
        av.moverAvatar(tablero.getPosiciones(), posicion);
    }

}

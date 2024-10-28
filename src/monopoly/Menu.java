package monopoly;

import java.util.*;
import partida.*;
import java.util.Scanner;

/*En esta clase no se necesitan getters, setters ya que solo llama a métodos de otras clases*/
public class Menu {

    /**********Atributos**********/
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
    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado
    int maxJugadores = 0;
    int jugadoresActuales = 0;
    boolean finalizarPartida = false;

    /**********Constructor**********/
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

    /**********Métodos**********/

    /*Método para mostrar la pantalla de inicio y crear los jugadores*/
    private void preIniciarPartida() {
        imprimirLogo();

        /*establece el número de jugadores que van a jugar la partida*/
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

    /*Método en el que se desarrolla la partida hasta que un jugador es no solvente*/
    private void iniciarPartida() {
        while(!finalizarPartida) {
            System.out.print("Introduce el comando: ");
            String comando = scanner.nextLine();
            analizarComando(comando);
        }
        System.out.println(tablero.toString());
        System.out.println("Partida finalizada. El jugador ha caído en una casilla y no es solvente.");
        scanner.close();
        System.exit(0);
    }

    /*Método que crea a todos los jugadores que van a jugar*/
    private void crearJugadores() {
        scanner.nextLine();
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

    /*Método que interpreta el comando introducido y toma la accion correspondiente
    * Parámetro: cadena de caracteres (el comando)
    */
    public void analizarComando(String comando) {
        String[] palabrasArray = comando.split(" ");
        if(palabrasArray.length > 0) {
            switch(palabrasArray[0]) {
                case "crear":
                    System.out.println("Todos los jugadores están registrados");
                    break;

                case "jugador":
                    System.out.println("Tiene el turno: " + (jugadores.get(turno)).getNombre());
                    break;

                case "lanzar":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("dados")) {
                        lanzarDados();
                        System.out.println(tablero.toString());
                        Evaluacion();
                        VueltasTablero();
                    } else {
                        System.out.println("El formato correcto es: lanzar dados");
                    }
                    break;
                case "acabar":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("turno")) {
                        acabarTurno();
                        break;
                    } else {
                        System.out.println("El formato correcto es: acabar turno");
                        break;
                    }

                case "listar":
                    if (palabrasArray.length == 2) {
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
                    } else {
                        System.out.println("El formato correcto es: listar [jugadores, avatares, enventa]");
                        break;
                    }
                    break;
                case "salir":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("carcel")) {
                            salirCarcel();
                        } else {
                            System.out.println("Comando no válido.");
                        }
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
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("tablero")) {
                        System.out.println(tablero.toString());
                    } else {
                        System.out.println("Comando no válido");
                    }
                    break;

                case "moveraux":
                    if (palabrasArray.length == 2) {
                        MoverAux(palabrasArray[1]);
                        Evaluacion();
                        VueltasTablero();
                        break;
                    } else {
                        System.out.println("El formato correcto es: moveraux numPosiciones");
                    }

                case "edificar":
                    if (palabrasArray.length == 2) {
                        edificar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: edificar [casa, hotel, piscina]");
                    }
                    break;

                default:
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }

    private void edificar(String palabra) {
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = jugador.getAvatar().getLugar();
        if (palabra.equals("casa")) {
            casilla.edificarCasa(jugador, jugadores);
        } else if (palabra.equals("hotel")) {
            casilla.edificarHotel(jugador, jugadores);
        } else if (palabra.equals("piscina")) {
            casilla.edificarPiscina(jugador, jugadores);
        }
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

    /**
     * Método que ejecuta las acciones relacionadas con el comando 'lanzar dados'.
     * Realiza la tirada de dados, maneja el movimiento del avatar, y controla las reglas especiales
     * como los dobles y la encarcelación tras tres dobles consecutivos.
     */
    private void lanzarDados() {
        if (!tirado) {
            Jugador jugador = jugadores.get(turno);
            if(jugador.getEnCarcel()) {
                System.out.println("El jugador está en la cárcel, no puede lanzar los dados para moverse.");
                return;
            }
            Avatar avatar = avatares.get(turno);
            int valor1 = dado1.hacerTirada();
            int valor2 = dado2.hacerTirada();
            lanzamientos += 1;
            tirado = true;
            System.out.println("Dado 1: " + valor1);
            System.out.println("Dado 2: " + valor2);
            if (valor1 == valor2) {
                System.out.println("¡Has sacado dobles!");
                if(lanzamientos == 3) {
                    System.out.println("¡Tres dobles consecutivos! El jugador va a la cárcel.");
                    jugador.encarcelar(tablero.getPosiciones());
                    acabarTurno();
                    return;
                } else {
                    System.out.println("Puedes lanzar otra vez.");
                    tirado = false;
                }
            }
            avatar.moverAvatar(tablero.getPosiciones(), (valor1 + valor2));
            if(jugador.getEnCarcel()) {
                acabarTurno();
            }
        }
        else {
            System.out.println("Ya has lanzado el dado en este turno.");
        }
    }

    /**
     * Método que maneja las posibles acciones al lanzar los dados cuando un jugador está encarcelado,
     * mostrando los resultados y tomando decisiones en función de los valores obtenidos.
     *
     * @param jugador El jugador que está encarcelado y realiza la tirada.
     */
    private void lanzarDados(Jugador jugador) {
        int valor1 = dado1.hacerTirada();
        int valor2 = dado2.hacerTirada();
        tirado = true;
        lanzamientos++;
        System.out.println("Dado 1: " + valor1);
        System.out.println("Dado 2: " + valor2);
        if (valor1 == valor2) {
            System.out.println("¡Dobles! El jugador avanza " + (valor1 + valor2) + " casillas y tiene derecho a otro lanzamiento.");
            jugador.getAvatar().moverAvatar(tablero.getPosiciones(), valor1 + valor2); // Mover el avatar
            jugador.setTiradasCarcel(0); // Salir de la cárcel
            jugador.setEnCarcel(false);
            tirado = false; // Permitimos otro lanzamiento
        } else {
            System.out.println("No han salido dobles... El jugador pierde el turno");
            jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
        }
    }

    /**
     * Método que ejecuta las acciones asociadas al comando 'comprar nombre_casilla',
     * permitiendo al jugador adquirir una casilla si cumple con las condiciones.
     *
     * @param nombre El nombre de la casilla que el jugador desea comprar.
     */
    private void comprar(String nombre) {
        if(lanzamientos == 0) {
            System.out.println("No puedes comprar la casilla en la que estabas, debes lanzar.");
            return;
        }
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

    /**
     * Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
     * Permite al jugador intentar salir de la cárcel, ya sea tirando los dados o pagando la fianza,
     * dependiendo de sus opciones y recursos disponibles.
     */
    private void salirCarcel() {
        Jugador JugActual = jugadores.get(turno);
        Casilla carcel = tablero.encontrar_casilla("Cárcel");

        // Verificar si el jugador está en la cárcel
        if (!JugActual.getEnCarcel()) {
            System.out.println("El jugador no está en la cárcel. No puede usar el comando.");
            return;
        }

        boolean TiradasCarcel = JugActual.getTiradasCarcel() < 3;
        float fianza = carcel.getImpuesto();
        boolean Presupuesto = JugActual.getFortuna() >= fianza;

        // Caso 1: Puede tirar los dados o pagar la fianza
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
            } while (true);
            if (respuesta == 1) {
                // Lógica para tirar los dados
                System.out.println("El jugador decide lanzar los dados");
                lanzarDados(JugActual);
            } else {
                // Pagar la fianza
                System.out.println("El jugador paga la fianza. Tiene derecho a usar su turno");
                JugActual.setEnCarcel(false);
                JugActual.setTiradasCarcel(0);
                JugActual.sumarFortuna(-fianza);
                JugActual.sumarGastos(fianza);
                banca.sumarFortuna(fianza);
            }
        }
        // Caso 2: Solo puede tirar los dados
        else if (TiradasCarcel) {
            System.out.println("El jugador solo puede tirar los dados.");
            lanzarDados(JugActual);
            System.out.println(JugActual.getTiradasCarcel());
        }
        // Caso 3: Solo puede pagar la fianza
        else if (Presupuesto) {
            System.out.println("El jugador solo puede pagar la fianza");
            JugActual.setEnCarcel(false);
            JugActual.setTiradasCarcel(0);
            JugActual.sumarFortuna(-fianza);
            JugActual.sumarGastos(fianza);
            banca.sumarFortuna(fianza);
        }
        acabarTurno();
    }

    /*Método que realiza las acciones asociadas al comando 'listar enventa'*/
    private void listarVenta() {
        for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for(Casilla casilla : fila) {
                if((casilla.getTipo().equals("Solar") || casilla.getTipo().equals("Transporte")
                        || casilla.getTipo().equals("Servicios")) && casilla.getDuenho().equals(banca)) {
                    System.out.println(casilla.casEnVenta());
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
        if(!tirado && dado1.getValor() != dado2.getValor()) {
            System.out.println("No puedes acabar turno sin haber lanzado los dados.");
            return;
        }
        System.out.println(infoTrasTurno(jugadores.get(turno)));
        Jugador anterior = jugadores.get(turno);
        System.out.println("El jugador " + anterior.getNombre() + ", con avatar " + anterior.getAvatar().getId() + ", acaba su turno.");
        turno = (turno+1)%(jugadores.size());
        lanzamientos = 0;
        tirado = false;
        solvente = true;
        Jugador jugador = jugadores.get(turno);
        System.out.println("El turno le pertenece al jugador " + jugador.getNombre() + ". Con avatar " + jugador.getAvatar().getId() + ".");
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

    /**
     * Método que imprime por pantalla información sobre el estado financiero de un jugador después de su turno.
     * Muestra la fortuna, los gastos y las propiedades actuales del jugador.
     *
     * @param jugador El jugador del cual se generará la información.
     * @return Una cadena formateada que contiene la fortuna, los gastos y las propiedades del jugador.
     */
    private String infoTrasTurno(Jugador jugador) {
        ArrayList<Casilla> props = jugador.getPropiedades();
        System.out.println("El estado financiero de " + jugador.getNombre() + " es:");
        StringBuilder propiedades = new StringBuilder();
        for (Casilla casilla : props) {
            propiedades.append(casilla.getNombre()).append(", ");
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
            """.formatted(jugador.getFortuna(), jugador.getGastos(), propiedades.toString());
    }

    /**
     * Método para evaluar si el jugador es solvente en la casilla en la que cae.
     *
     * Este método comprueba la solvencia del jugador actual en la casilla correspondiente.
     * Si el jugador no es solvente, la partida se finalizará. Si es solvente y la casilla
     * requiere el pago (Solar, Servicios o Transporte), se calcula el alquiler correspondiente.
     * En el caso de que el jugador caiga en una casilla de Impuestos, se deduce el impuesto
     * de la fortuna del jugador y se suma al bote del Parking.
     */
    private void Evaluacion() {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
        solvente = casillaActual.evaluarCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
        if (!solvente) {
            finalizarPartida = true;
        } else {
            //Comprobar que hay en la casilla en la que se cae hay que pagar
            if (casillaActual.getTipo().equals("Solar") || casillaActual.getTipo().equals("Servicios") || casillaActual.getTipo().equals("Transporte")) {
                casillaActual.pagarAlquiler(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
            }
            else if(casillaActual.getTipo().equals("Impuestos")) {
                jugadorActual.sumarFortuna(-casillaActual.getImpuesto());
                jugadorActual.sumarGastos(casillaActual.getImpuesto());
                Casilla bote = tablero.encontrar_casilla("Parking");
                bote.sumarValor(casillaActual.getImpuesto());
              
                System.out.println("El jugador " + jugadorActual.getNombre() + " ha pagado en impuestos " + casillaActual.getImpuesto());
                banca.sumarFortuna(casillaActual.getImpuesto());
            }
        }
    }

    /**
     * Método que ajusta el valor de las casillas de tipo "Solar" propiedad de la banca
     * si todos los jugadores han completado al menos 4 vueltas al tablero.
     * El valor de estas casillas aumenta un 5% cada vez que un jugador complete un múltiplo de 4 vueltas.
     */
    private void VueltasTablero() {
        boolean sumarSolares = false;
        int menosVueltas = Integer.MAX_VALUE;
        Jugador jugadorMenosVueltas = null;
        // Encontrar el jugador con menos vueltas
        for (Jugador jugadorActual : jugadores) {
            int vueltasActuales = jugadorActual.getVueltas();
            if (vueltasActuales < menosVueltas) {
                menosVueltas = vueltasActuales;
                jugadorMenosVueltas = jugadorActual;
            }
        }
        // Comprobar que el jugador con menos vueltas ha dado más de 0 vueltas y es múltiplo de 4
        if (jugadorMenosVueltas != null && jugadorMenosVueltas.getVueltas() > 0 && jugadorMenosVueltas.getVueltas() % 4 == 0) {
            sumarSolares = true;
        }
        // Incrementar el valor de los solares si se han completado 4 vueltas
        if (sumarSolares) {
            for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
                for (Casilla casilla : fila) {
                    if (casilla.getTipo().equals("Solar") && casilla.getDuenho().equals(banca)) {
                        casilla.sumarValor(casilla.getValor() * 0.05f); // Aumenta el valor de la casilla
                    }
                }
            }
            System.out.println("Todos los jugadores han completado 4 vueltas.\n" +
                    "El precio de los solares sin comprar aumenta un 5%");
        }
    }

    /**
     * Método auxiliar que permite mover el avatar de un jugador a una posición específica en el tablero.
     * Se utiliza para realizar comprobaciones en el juego relacionadas con el movimiento del jugador.
     *
     * @param pos La posición a la que se quiere mover el avatar (en formato de cadena, que será convertida a entero).
     */
    private void MoverAux(String pos) {
        lanzamientos = 1;
        tirado = true;
        Jugador jugador = jugadores.get(turno);
        Avatar av = jugador.getAvatar();
        int posicion = Integer.parseInt(pos);
        av.moverAvatar(tablero.getPosiciones(), posicion);
    }

}

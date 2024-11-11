package monopoly;

import java.lang.reflect.Array;
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
        this.tablero = new Tablero(banca);

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
                    if(tirado){
                        System.out.println("Ya has lanzado los dados en este turno.");
                        break;
                    }
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("dados")) {
                        lanzarDados(0, 0);
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();

                    } else if (palabrasArray.length == 4 && palabrasArray[1].equals("dados")) { //Dados trucados
                        lanzarDados(Integer.parseInt(palabrasArray[2]), Integer.parseInt(palabrasArray[3]));
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();


                    } else {
                        System.out.println("El formato correcto es: lanzar dados o lanzar dados (núm primer dado) (núm segundo dado)");
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
                            case "edificios":
                                listarEdificios();
                                break;
                            default:
                                System.out.println("Comando no válido");
                                break;
                        }
                    } else if(palabrasArray.length == 3) {
                        if(palabrasArray[1].equals("edificios")) {
                            listarEdificiosGrupo(palabrasArray[2]);
                        } else {
                            System.out.println("El formato correcto es: listar edificios [colorGrupo]");
                        }
                    } else {
                        System.out.println("El formato correcto es: listar [jugadores, avatares, enventa, edificios]");
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
                        evaluacion();
                        VueltasTablero();
                    } else {
                        System.out.println("El formato correcto es: moveraux numPosiciones");
                    }
                    break;

                case "edificar":
                    if (palabrasArray.length == 2) {
                        edificar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: edificar [Casa, Hotel, Piscina, PistaDeporte]");
                    }
                    break;

                case "cambiar":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("modo")){
                        Jugador jugador = jugadores.get(turno);
                        Avatar avatar = jugador.getAvatar();
                        avatar.setAvanzado(1);
                        lanzarDados(0, 0);
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();
                    } else {
                        System.out.println("El formato correcto es: cambiar modo");
                    }
                    break;

                case "vender":
                    if(palabrasArray.length == 4) {
                        ventaEdificio(palabrasArray[1], palabrasArray[2], palabrasArray[3]);
                    }
                    break;

                case "estadisticas":
                    if (palabrasArray.length == 1) {
                        estadisticas();
                    } else if (palabrasArray.length == 2) {
                        estadisticasJugador(palabrasArray[1]);
                    }
                    break;
                default:
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }

    private void estadisticasJugador(String jugadorStr) {
        for(Jugador jugador : jugadores) {
            if(jugador.getNombre().equals(jugadorStr)) {
                System.out.println("{");
                System.out.println("\tdineroInvertido: " + jugador.getDineroInvertido());
                System.out.println("\tpagoTasasEImpuestos: " + jugador.getPagoTasasEImpuestos());
                System.out.println("\tpagoDeAlquileres: " + jugador.getPagoDeAlquileres());
                System.out.println("\tcobroDeAlquileres: " + jugador.getCobroDeAlquileres());
                System.out.println("\tpasarPorCasillaDeSalida: " + jugador.getPasarPorCasillaDeSalida());
                System.out.println("\tpremiosInversionesOBote: " + jugador.getPremiosInversionesOBote());
                System.out.println("\tvecesEnLaCarcel: " + jugador.getVecesEnLaCarcel());
                System.out.println("}");
                return ;
            }
        }
        System.out.println("El jugador no existe");
    }


    private void edificar(String palabra) {
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = jugador.getAvatar().getLugar();
        switch (palabra) {
            case "Casa":
                if(casilla.edificarCasa(jugador, contadorCasa)){
                    contadorCasa++;
                }
                break;
            case "Hotel":
                if(casilla.edificarHotel(jugador, contadorHotel)){
                    contadorHotel++;
                }

                break;
            case "Piscina":
                if(casilla.edificarPiscina(jugador, contadorPiscina)){
                    contadorPiscina++;
                }
                break;
            case "PistaDeporte":
                if(casilla.edificarPistaDeporte(jugador, contadorPistaDeporte)){
                    contadorPistaDeporte++;
                }
                break;
            default:
                System.out.println("Edificio no válido.");
                break;
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
    private void lanzarDados(int tirada1, int tirada2) {
        if (!tirado) {
            Jugador jugador = jugadores.get(turno);
            Avatar avatar = avatares.get(turno);

            if(jugador.getEnCarcel()) {
                System.out.println("El jugador está en la cárcel, no puede lanzar los dados para moverse.");
                return;
            }

            int valor1, valor2;
            if (tirada1 == 0 && tirada2 == 0) {
                valor1 = dado1.hacerTirada();
                valor2 = dado2.hacerTirada();
            } else {
                valor1 = tirada1;
                valor2 = tirada2;
            }
            jugador.sumarVecesTiradasDados();

            lanzamientos += 1;
            tirado = true;
            System.out.println("Dado 1: " + valor1);
            System.out.println("Dado 2: " + valor2);

            if(avatar.getAvanzado() == 0){
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
            } else if (avatar.getAvanzado() == 1) {
                if(avatar.getTipo().equalsIgnoreCase("Pelota")){
                    avatar.moverJugadorPelota(tablero.getPosiciones(), valor1, valor2);
                } else if (avatar.getTipo().equalsIgnoreCase("Coche")){
                    avatar.moverJugadorCoche(tablero.getPosiciones(), valor1, valor2, lanzamientos);
                }
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
        jugador.sumarVecesTiradasDados();
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
                JugActual.sumarTasasEImpuestos(fianza);
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
    private void evaluacion() {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
        solvente = casillaActual.evaluarCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
        if (!solvente) {
            finalizarPartida = true;
        } else {
            //Comprobar que hay en la casilla en la que se cae hay que pagar
            switch (casillaActual.getTipo()) {
                case "Solar", "Servicios", "Transporte" ->
                        casillaActual.pagarAlquiler(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
                case "Impuestos" -> {
                    jugadorActual.sumarFortuna(-casillaActual.getImpuesto());
                    jugadorActual.sumarGastos(casillaActual.getImpuesto());
                    jugadorActual.sumarTasasEImpuestos(casillaActual.getImpuesto());
                    Casilla bote = tablero.encontrar_casilla("Parking");
                    bote.sumarValor(casillaActual.getImpuesto());

                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha pagado en impuestos " + casillaActual.getImpuesto());
                    banca.sumarFortuna(casillaActual.getImpuesto());
                }
                case "Suerte" -> {
                    barajas.evaluarSuerte(banca, jugadorActual, tablero);
                }
                case "Comunidad" -> {
                    barajas.evaluarComunidad(banca, jugadorActual, tablero, jugadores);
                }
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
     * Metodo que permite listar los edificios que han sido construídos.
     */
    private void listarEdificios() {
        boolean existenEdificios = false;
        for(Jugador jugador : jugadores) {
            if(!jugador.getEdificios().isEmpty()) {
                existenEdificios = true;
                break;
            }
        }

        if(existenEdificios) {
            System.out.println("Edificios construídos:");
            StringBuilder str = new StringBuilder();
            for (Jugador jugador : jugadores) {
                if(!jugador.getEdificios().isEmpty()) {
                    // Iteramos sobre todos los edificios del jugador
                    for(Edificio edificio : jugador.getEdificios()){
                        str.append(edificio.infoEdificio());
                        str.append("\n");  // Añadimos salto de línea después de cada edificio
                    }
                }
            }
            System.out.println(str);
        }
        else {
            System.out.println("No existen edificios construídos actualmente.");
        }
    }

    /**
     * Metodo que permite listar los edificios construídos en un grupo de solares
     */
    //!!!Comprobar que funciona + añadir qué edificios se poden construír!!!
    private void listarEdificiosGrupo(String color) {
        Casilla casillaGrupo = null;
        boolean existenEdificios = false;

        // Buscar una casilla del grupo primero en edificios
        for(Jugador jugador : jugadores) {
            for(Edificio edificio : jugador.getEdificios()) {
                if (edificio.getCasilla().getGrupo().getNombreGrupo().equals(color)) {
                    existenEdificios = true;
                    casillaGrupo = edificio.getCasilla();
                    break;
                }
            }
            if(casillaGrupo != null) break;  // Si ya encontramos una casilla, salimos del bucle
        }

        // Si no encontramos casilla en edificios, buscamos en el tablero una cualquiera
        if (casillaGrupo == null) {
            for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
                for (Casilla casilla : fila) {
                    if(casilla.getTipo().equals("Solar") && casilla.getGrupo().getNombreGrupo().equals(color)) {
                        casillaGrupo = casilla;
                    }
                }
            }
        }

        int numCasillasGrupo = casillaGrupo.getGrupo().getNumCasillas();
        int numHotelesActuales = casillaGrupo.getGrupo().getNumEdificios(casillaGrupo.getGrupo().getEdificiosGrupo(), "Hotel");
        int numCasasActuales = casillaGrupo.getGrupo().getNumEdificios(casillaGrupo.getGrupo().getEdificiosGrupo(), "Casa");
        int numPiscinasActuales = casillaGrupo.getGrupo().getNumEdificios(casillaGrupo.getGrupo().getEdificiosGrupo(), "Piscina");
        int numPistasActuales = casillaGrupo.getGrupo().getNumEdificios(casillaGrupo.getGrupo().getEdificiosGrupo(), "Pista");

        System.out.println("\n=== Estado actual del grupo " + color + " ===");

        if(existenEdificios) {
            System.out.println("Edificios construidos:");
            StringBuilder str = new StringBuilder();
            for (Jugador jugador : jugadores) {
                for(Casilla casilla : jugador.getPropiedades()) {
                    if(casilla.getGrupo().getNombreGrupo().equals(color) && !casilla.getGrupo().getEdificiosGrupo().isEmpty()) {
                        str.append(casilla.edificiosGrupo());
                    }
                }
            }
            System.out.println(str);
        } else {
            System.out.println("No hay edificios construidos actualmente.");
        }

        System.out.println("\n=== Edificios disponibles para construcción ===");

        // Información sobre hoteles
        int hotelesDisponibles = numCasillasGrupo - numHotelesActuales;
        System.out.println("Hoteles: " + hotelesDisponibles + " disponibles de " + numCasillasGrupo + " máximos");

        // Información sobre casas
        if (numHotelesActuales < numCasillasGrupo) {
            int casasMaximasPorSolar = 4;
            int casasTotalesDisponibles = (numCasillasGrupo * casasMaximasPorSolar) - numCasasActuales;
            System.out.println("Casas: " + casasTotalesDisponibles + " disponibles (máximo 4 por solar)");
        } else {
            System.out.println("Casas: No se pueden construir más (máximo de hoteles alcanzado)");
        }

        // Información sobre piscinas
        int piscinasDisponibles = numCasillasGrupo - numPiscinasActuales;
        System.out.println("Piscinas: " + piscinasDisponibles + " disponibles de " + numCasillasGrupo + " máximas");

        // Información sobre pistas deportivas
        int pistasDisponibles = numCasillasGrupo - numPistasActuales;
        System.out.println("Pistas deportivas: " + pistasDisponibles + " disponibles de " + numCasillasGrupo + " máximas");
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

    private void ventaEdificio(String tipo, String nombreCasilla, String cantidad) {
        int contador = 0;
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla == null) {
            System.out.println("Casilla no encontrada");
            return;
        }
        if(casilla.getDuenho() != jugadores.get(turno)) {
            System.out.println("No eres propietario de la casilla, no puedes vender edificios.");
            return;
        }
        if(casilla.getEdificios().isEmpty()) {
            System.out.println("No hay edificios en la casilla.");
            return;
        }
        for(Edificio edificio : casilla.getEdificios()) {
            if(edificio.getTipo().equals(tipo)) {
                contador++;
                if(contador == Integer.parseInt(cantidad)) {
                    break;
                }
            }
        }
        if(contador != Integer.parseInt(cantidad)) {
            System.out.println("No hay " + Integer.parseInt(cantidad) + " edificios del tipo " + tipo + " en la casilla.");
        }
        casilla.venderEdificios(tipo, contador);
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

    private void estadisticas() {
        System.out.println("{");
        System.out.print("\tcasillaMasRentable: ");
        imprimirNombresCasillas(casillasMasRentables());
        System.out.print("\tgrupoMasRentable: ");
        imprimirNombresGrupos(calcularGruposMasRentables());
        System.out.print("\tcasillaMasFrecuentada: ");
        imprimirNombresCasillas(casillasMasFrecuentadas());
        System.out.print("\tjugadorMasVueltas: ");
        imprimirNombresJugadores(jugadoresConMasVueltas());
        System.out.print("\tjugadorMasVecesDados: ");
        imprimirNombresJugadores(jugadoresConMasTiradasDados());
        System.out.print("\tjugadorEnCabeza: ");
        imprimirNombresJugadores(jugadoresEnCabeza());
        System.out.println("}");
    }

    private void imprimirNombresJugadores(ArrayList<Jugador> jugadores) {
        if (jugadores.isEmpty()) {
            System.out.println("-");
        } else {
            for (Jugador jugador : jugadores) {
                System.out.print(jugador.getNombre());
                if (!jugador.equals(jugadores.getLast())) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private void imprimirNombresCasillas(ArrayList<Casilla> casillas) {
        if (casillas.isEmpty()) {
            System.out.println("-");
        } else {
            for (Casilla casilla : casillas) {
                System.out.print(casilla.getNombre());
                if (!casilla.equals(casillas.getLast())) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private void imprimirNombresGrupos(ArrayList<Grupo> grupos) {
        if (grupos.isEmpty()) {
            System.out.println("-");
        } else {
            for (Grupo grupo : grupos) {
                System.out.print(grupo.getNombreGrupo());
                if (!grupo.equals(grupos.getLast())) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private ArrayList<Jugador> jugadoresConMasVueltas() {
        ArrayList<Jugador> jugadoresMasVueltas = new ArrayList<>();
        int maxVueltas = 0;

        for (Jugador jugador : jugadores) {
            if (jugador.getVueltas() > maxVueltas) {
                maxVueltas = jugador.getVueltas();
                jugadoresMasVueltas.clear();
                jugadoresMasVueltas.add(jugador);
            } else if (jugador.getVueltas() == maxVueltas) {
                jugadoresMasVueltas.add(jugador);
            }
        }

        return jugadoresMasVueltas;
    }

    private ArrayList<Jugador> jugadoresConMasTiradasDados() {
        ArrayList<Jugador> jugadoresMasTiradasDados = new ArrayList<>();
        int maxTiradasDados = 0;

        for (Jugador jugador : jugadores) {
            if (jugador.getVecesTiradasDados() > maxTiradasDados) {
                maxTiradasDados = jugador.getVecesTiradasDados();
                jugadoresMasTiradasDados.clear();
                jugadoresMasTiradasDados.add(jugador);
            } else if (jugador.getVecesTiradasDados() == maxTiradasDados) {
                jugadoresMasTiradasDados.add(jugador);
            }
        }

        return jugadoresMasTiradasDados;
    }

    private ArrayList<Jugador> jugadoresEnCabeza() {
        ArrayList<Jugador> jugadoresEnCabeza = new ArrayList<>();
        float maxFortunaTotal = 0;

        for (Jugador jugador : jugadores) {
            if (jugador.calcularFortunaTotal() > maxFortunaTotal) {
                maxFortunaTotal = jugador.calcularFortunaTotal();
                jugadoresEnCabeza.clear();
                jugadoresEnCabeza.add(jugador);
            } else if (jugador.calcularFortunaTotal() == maxFortunaTotal) {
                jugadoresEnCabeza.add(jugador);
            }
        }

        return jugadoresEnCabeza;
    }

    private ArrayList<Casilla> casillasMasRentables() {
        ArrayList<Casilla> casillasMasRentables = new ArrayList<>();
        float maxAlquileresPagados = 0;
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                if (casilla.getTotalAlquileresPagados() > maxAlquileresPagados) {
                    maxAlquileresPagados = casilla.getTotalAlquileresPagados();
                    casillasMasRentables.clear();
                    casillasMasRentables.add(casilla);
                } else if (casilla.getTotalAlquileresPagados() == maxAlquileresPagados) {
                    casillasMasRentables.add(casilla);
                }
            }
        }

        if (casillasMasRentables.size() == 40) {
            return new ArrayList<>();
        }
        return casillasMasRentables;
    }

    private ArrayList<Grupo> calcularGruposMasRentables() {

        HashMap<Grupo, Float> rentabilidadPorGrupo = new HashMap<>();
        ArrayList<Grupo> gruposMasRentables = new ArrayList<>();
        float maxRentabilidad = 0;

        /*Cálculo de las rentabilidades de cada grupo*/
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                Grupo grupo = casilla.getGrupo();
                if (grupo != null) {
                    rentabilidadPorGrupo.put(grupo, rentabilidadPorGrupo.getOrDefault(grupo, 0f)
                            + casilla.getTotalAlquileresPagados());
                }
            }
        }

        for (Map.Entry<Grupo, Float> entry : rentabilidadPorGrupo.entrySet()) {
            if (entry.getValue() > maxRentabilidad) {
                maxRentabilidad = entry.getValue();
                gruposMasRentables.clear();
                gruposMasRentables.add(entry.getKey());
            } else if (entry.getValue() == maxRentabilidad) {
                gruposMasRentables.add(entry.getKey());
            }
        }
        if (gruposMasRentables.size() == 8) {
            return new ArrayList<>();
        }
        return gruposMasRentables;
    }

    private ArrayList<Casilla> casillasMasFrecuentadas() {
        ArrayList<Casilla> casillasMasFrecuentadas = new ArrayList<>();
        float maxTotalVecesFrecuentada = 0;
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                if (casilla.getTotalVecesFrecuentada() > maxTotalVecesFrecuentada) {
                    maxTotalVecesFrecuentada = casilla.getTotalVecesFrecuentada();
                    casillasMasFrecuentadas.clear();
                    casillasMasFrecuentadas.add(casilla);
                } else if (casilla.getTotalVecesFrecuentada() == maxTotalVecesFrecuentada) {
                    casillasMasFrecuentadas.add(casilla);
                }
            }
        }
        if (casillasMasFrecuentadas.size() == 40) {
            return new ArrayList<>();
        }
        return casillasMasFrecuentadas;
    }

}
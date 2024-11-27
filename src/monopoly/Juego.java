package monopoly;

import monopoly.casilla.Casilla;
import partida.Avatar;
import partida.Dado;
import partida.Jugador;

import java.util.*;

public class Juego implements Comando{

    /**********Atributos**********/
    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado
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

    //Instancias la consola en esta clase

    /**********Constructor**********/
    public Juego() {
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

        preIniciarPartida();

    }

    /*Getters*/
    public boolean isFinalizarPartida() {
        return finalizarPartida;
    }

    public boolean isSeHaMovido() {
        return seHaMovido;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public int getTurno() {
        return turno;
    }

    public boolean isTirado() {
        return tirado;
    }

    /*Setters*/

    public void setSeHaMovido(boolean seHaMovido) {
        this.seHaMovido = seHaMovido;
    }

    public void setTirado(boolean tirado) {
        this.tirado = tirado;
    }

    public void setSaltoMovimiento(int saltoMovimiento) {
        this.saltoMovimiento = saltoMovimiento;
    }

    public void setDadosDobles(boolean dadosDobles) {
        this.dadosDobles = dadosDobles;
    }

    /**********Métodos**********/

    /******************************/
    /*SECCIÓN DE INICIO DE PARTIDA*/
    /******************************/

    /*Método para mostrar la pantalla de inicio y crear los jugadores*/
    public void preIniciarPartida() {
        imprimirLogo();

        /*establece el número de jugadores que van a jugar la partida*/
        System.out.println("-----Número de jugadores-----");

        maxJugadores = introducirNum(2, 6);

        crearJugadores();
    }
  

    /*Método que imprime el logo mediante un for con un efecto visual*/
    public void imprimirLogo() {
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

    /**********************************/
    /*SECCIÓN DE CREACIÓN DE JUGADORES*/
    /**********************************/

    /*Método que crea a todos los jugadores que van a jugar*/
    public void crearJugadores() {
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
    public boolean esJugadorRepetido(String nombre) {
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
    public boolean esAvatarCorrecto(String tipoAvatar) {
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
    public void darAltaJugador(String nombre, String tipoAvatar){
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

    /*******************************/
    /*SECCIÓN MOVIMIENTOS AVANZADOS*/
    /*******************************/

    /*Método para activar el modo avanzado del jugador*/
    public void modoAvanzado() {
        Avatar avatarActual = jugadores.get(turno).getAvatar();
        avatarActual.setAvanzado(true);
        System.out.println("A partir de ahora el avatar " + avatarActual.getId() + ", de tipo " + avatarActual.getTipo() + ", se moverá en modo avanzado.");
    }

    /*Método con la lógica del movimiento avanzado Pelota*/
    public void moverJugadorPelota(int valorTirada) {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            System.out.println("El jugador está en la cárcel, no puede avanzar.");
            return;
        }

        if (valorTirada > 4) {
            avatarActual.moverAvatar(tablero.getPosiciones(), 5);
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }

            saltoMovimiento = valorTirada - 5;

        } else {
            avatarActual.moverAvatar(tablero.getPosiciones(), -1);
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }

            saltoMovimiento = -valorTirada + 1;
        }
    }

    /*Método con la lógica del movimiento avanzado Coche*/
    public void moverJugadorCoche(int valorTirada){
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            System.out.println("El jugador está en la cárcel, no puede avanzar.");
            return;
        }

        if(valorTirada > 4 && saltoMovimiento > 0) {
            tirado = false;
            avatarActual.moverAvatar(tablero.getPosiciones(), valorTirada);
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }
            saltoMovimiento--;
            if(saltoMovimiento == 0) {
                tirado = !dadosDobles;
            }
        }

        if (valorTirada <= 4) {
            avatarActual.moverAvatar(tablero.getPosiciones(), -valorTirada);
            jugadorActual.setNoPuedeTirarDados(2);
            saltoMovimiento = 0;
            tirado = true;
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }
        }
    }

    /*Método para avanzar con el modo avanzado Pelota*/
    public void avanzar() {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        if(saltoMovimiento == 0) {
            System.out.println("No hay ningún movimiento pendiente.");
            return;
        }
        seHaMovido = true;
        if(saltoMovimiento > 0) {
            if(saltoMovimiento == 1) {
                avatarActual.moverAvatar(tablero.getPosiciones(), 1);
                saltoMovimiento = 0;
            } else {
                avatarActual.moverAvatar(tablero.getPosiciones(), 2);
                if(jugadorActual.getEnCarcel()) {
                    saltoMovimiento = 0;
                } else {
                    saltoMovimiento -= 2;
                }
            }
        } else {
            if(saltoMovimiento == -1) {
                avatarActual.moverAvatar(tablero.getPosiciones(), -1);
                saltoMovimiento = 0;
            }
            else {
                avatarActual.moverAvatar(tablero.getPosiciones(), -2);
                saltoMovimiento += 2;
            }
        }
    }

    /**********************************/
    /*SECCIÓN DE DESCRIPCIÓN DE CLASES*/
    /**********************************/

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    public void descJugador(String nombre) {
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
    public void descAvatar(String ID) {
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

    /*******************/
    /*SECCIÓN DE LISTAR*/
    /*******************/

    /*Método que realiza las acciones asociadas al comando 'listar enventa'*/
    public void listarVenta() {
        for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for(Casilla casilla : fila) {
                if((casilla.getTipo().equals("Solar") || casilla.getTipo().equals("Transporte")
                        || casilla.getTipo().equals("Servicios")) && casilla.getDuenho().equals(banca)) {
                    System.out.println(casilla.casillaEnVenta());
                }
            }
        }
    }

    /* Método que realiza las acciones asociadas al comando 'listar jugadores'*/
    public void listarJugadores() {
        System.out.println("Jugadores:");
        listaArray(jugadores);
        /*
        StringBuilder str = new StringBuilder();
        for (Jugador jugador : jugadores) {
            str.append(jugador.info());
            if (!jugador.equals(jugadores.getLast())) {
                str.append(",\n");
            } else {
                str.append("\n");
            }
        }
        System.out.println(str); */
    }

    /* Método que realiza las acciones asociadas al comando 'listar avatares' */
    public void listarAvatares() {
        System.out.println("Avatares:");
        listaArray(avatares);
        /*
        StringBuilder str = new StringBuilder();
        for (Avatar avatar : avatares) {
            str.append(avatar.infoAvatar());
            if (!avatar.equals(avatares.getLast())) {
                str.append(",\n");
            } else {
                str.append("\n");
            }
        }
        System.out.println(str); */
    }

    /*************************/
    /*SECCIÓN DE ESTADÍSTICAS*/
    /*************************/

    /* Método que realiza las acciones asociadas al comando 'estadisticas jugador'.
     * Parámetro: nombre del jugador a describir.
     */
    public void estadisticasJugador(String jugadorStr) {
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

    /**
     * Método que imprime las estadísticas del juego.
     */
    public void estadisticas() {
        ArrayList<Jugador> jugadoresMasVueltas = new ArrayList<>();
        ArrayList<Jugador> jugadoresMasTiradasDados = new ArrayList<>();
        ArrayList<Jugador> jugadoresEnCabeza = new ArrayList<>();
        jugadoresEstadisticasGenerales(jugadoresMasVueltas, jugadoresMasTiradasDados, jugadoresEnCabeza);

        ArrayList<Casilla> casillasMasRentables = new ArrayList<>();
        ArrayList<Casilla> casillasMasFrecuentadas = new ArrayList<>();
        casillasEstadisticasGenerales(casillasMasRentables, casillasMasFrecuentadas);

        System.out.println("{");
        System.out.print("\tcasillaMasRentable: ");
        imprimirNombres(casillasMasRentables);
        System.out.print("\tgrupoMasRentable: ");
        imprimirNombres(calcularGruposMasRentables());
        System.out.print("\tcasillaMasFrecuentada: ");
        imprimirNombres(casillasMasFrecuentadas);
        System.out.print("\tjugadorMasVueltas: ");
        imprimirNombres(jugadoresMasVueltas);
        System.out.print("\tjugadorMasVecesDados: ");
        imprimirNombres(jugadoresMasTiradasDados);
        System.out.print("\tjugadorEnCabeza: ");
        imprimirNombres(jugadoresEnCabeza);
        System.out.println("}");
    }

    /**
     * Método genérico que imprime los nombres de una lista de objetos.
     * Si la lista está vacía, imprime un guion ("-").
     *
     * @param lista La lista de objetos cuyos nombres se van a imprimir.
     */
    public void imprimirNombres(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("-");
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Object elemento = lista.get(i);

                if (elemento instanceof Jugador) {
                    System.out.print(((Jugador) elemento).getNombre());
                } else if (elemento instanceof Casilla) {
                    System.out.print(((Casilla) elemento).getNombre());
                } else if (elemento instanceof Grupo) {
                    System.out.print(((Grupo) elemento).getNombreGrupo());
                }

                if (i < lista.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Método que calcula las estadísticas generales de los jugadores.
     */
    public void jugadoresEstadisticasGenerales(ArrayList<Jugador> jugadoresMasVueltas,
                                               ArrayList<Jugador> jugadoresMasTiradasDados,
                                               ArrayList<Jugador> jugadoresEnCabeza) {
        int maxVueltas = 0;
        int maxTiradasDados = 0;
        float maxFortunaTotal = 0;

        for(Jugador jugador : jugadores) {
            //Verificar las vueltas
            if(jugador.getVueltas() > maxVueltas) {
                maxVueltas = jugador.getVueltas();
                jugadoresMasVueltas.clear();
                jugadoresMasVueltas.add(jugador);
            } else if(jugador.getVueltas() == maxVueltas) {
                jugadoresMasVueltas.add(jugador);
            }

            //Verificar las tiradas de dados
            if(jugador.getVecesTiradasDados() > maxTiradasDados) {
                maxTiradasDados = jugador.getVecesTiradasDados();
                jugadoresMasTiradasDados.clear();
                jugadoresMasTiradasDados.add(jugador);
            } else if(jugador.getVecesTiradasDados() == maxTiradasDados) {
                jugadoresMasTiradasDados.add(jugador);
            }

            //Verificar la fortuna total
            if(jugador.calcularFortunaTotal() > maxFortunaTotal) {
                maxFortunaTotal = jugador.calcularFortunaTotal();
                jugadoresEnCabeza.clear();
                jugadoresEnCabeza.add(jugador);
            } else if(jugador.calcularFortunaTotal() == maxFortunaTotal) {
                jugadoresEnCabeza.add(jugador);
            }
        }
    }

    /**
     * Método que calcula las estadísticas generales de las casillas.
     */
    public void casillasEstadisticasGenerales(ArrayList<Casilla> casillasMasRentables,
                                              ArrayList<Casilla> casillasMasFrecuentadas) {
        float maxAlquileresPagados = 0;
        int maxVecesVisitada = 0;

        for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for(Casilla casilla : fila) {
                //Verificar las casillas más rentables
                if(casilla.getTotalAlquileresPagados() > maxAlquileresPagados) {
                    maxAlquileresPagados = casilla.getTotalAlquileresPagados();
                    casillasMasRentables.clear();
                    casillasMasRentables.add(casilla);
                } else if(casilla.getTotalAlquileresPagados() == maxAlquileresPagados) {
                    casillasMasRentables.add(casilla);
                }

                //Verificar las casillas más frecuentadas
                if(casilla.getTotalVecesFrecuentada() > maxVecesVisitada) {
                    maxVecesVisitada = casilla.getTotalVecesFrecuentada();
                    casillasMasFrecuentadas.clear();
                    casillasMasFrecuentadas.add(casilla);
                } else if(casilla.getTotalVecesFrecuentada() == maxVecesVisitada) {
                    casillasMasFrecuentadas.add(casilla);
                }
            }
        }

        // Si todas las casillas tienen el mismo total de alquileres pagados, devuelve una lista vacía
        if (casillasMasRentables.size() == 40) {
            casillasMasRentables.clear();
        }

        // Si todas las casillas han sido frecuentadas el mismo número de veces, devuelve una lista vacía
        if (casillasMasFrecuentadas.size() == 40) {
            casillasMasFrecuentadas.clear();
        }

    }

    /**
     * Método que calcula los grupos más rentables.
     *
     * @return Una lista de los grupos más rentables.
     */
    public ArrayList<Grupo> calcularGruposMasRentables() {
        HashMap<Grupo, Float> rentabilidadPorGrupo = new HashMap<>();
        ArrayList<Grupo> gruposMasRentables = new ArrayList<>();
        float maxRentabilidad = 0;

        // Recorre el tablero para calcular la rentabilidad de cada grupo
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                Grupo grupo = casilla.getGrupo();
                if (grupo != null) {
                    rentabilidadPorGrupo.put(grupo, rentabilidadPorGrupo.getOrDefault(grupo, 0f)
                            + casilla.getTotalAlquileresPagados());
                }
            }
        }

        // Encuentra los grupos con la mayor rentabilidad
        for (Map.Entry<Grupo, Float> entry : rentabilidadPorGrupo.entrySet()) {
            if (entry.getValue() > maxRentabilidad) {
                // Si el grupo actual tiene más rentabilidad que el máximo actual, actualiza el máximo y reinicia la lista
                maxRentabilidad = entry.getValue();
                gruposMasRentables.clear();
                gruposMasRentables.add(entry.getKey());
            } else if (entry.getValue() == maxRentabilidad) {
                // Si el grupo actual tiene la misma rentabilidad que el máximo, añádelo a la lista
                gruposMasRentables.add(entry.getKey());
            }
        }

        // Si todos los grupos tienen la misma rentabilidad, devuelve una lista vacía
        if (gruposMasRentables.size() == 8) {
            return new ArrayList<>();
        }
        return gruposMasRentables;
    }

    /*************************/
    /*SECCIÓN DE LANZAR DADOS*/
    /*************************/

    /**
     * Método que ejecuta las acciones relacionadas con el comando 'lanzar dados'.
     * Realiza la tirada de dados, maneja el movimiento del avatar, y controla las reglas especiales
     * como los dobles y la encarcelación tras tres dobles consecutivos.
     */
    public void lanzarDados(int tirada1, int tirada2) {
        if(!solvente) {
            System.out.println("El jugador no es solvente, no puede lanzar los dados.");
            return;
        }
        if (!tirado || dadosDobles) {
            if (saltoMovimiento != 0 && jugadores.get(turno).getAvatar().getTipo().equals("Pelota")) {
                System.out.println("El jugador está en modo avanzado, no puede lanzar los dados.");
                return;
            }

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

            //Sin modo avanzado o con pelota
            if(!avatar.isAvanzado() || avatar.getTipo().equals("Pelota")) {
                if (valor1 == valor2) {
                    System.out.println("¡Has sacado dobles!");
                    dadosDobles = true;
                    if (lanzamientos == 3) {
                        System.out.println("¡Tres dobles consecutivos! El jugador va a la cárcel.");
                        jugador.encarcelar(tablero.getPosiciones());
                        dadosDobles = false;
                        acabarTurno();
                        return;
                    } else {
                        System.out.println("Puedes lanzar otra vez.");
                        tirado = false;
                    }
                } else {
                    dadosDobles = false;
                }
            } else if (avatar.getTipo().equals("Coche")){ //Modo avanzado con coche
                if(lanzamientos >= 4 && (valor1 == valor2)) {
                    System.out.println("Puede lanzar otra vez.");
                    dadosDobles = true;
                    tirado = false;
                } else if(lanzamientos < 4 && (valor1 + valor2) < 4) {
                    System.out.println("Has sacado un valor menor que 4, no puedes lanzar otra vez");
                    dadosDobles = false;
                }
                if(lanzamientos >= 5) {
                    System.out.println("El jugador va a la cárcel");
                    jugador.encarcelar(tablero.getPosiciones());
                    dadosDobles = false;
                    acabarTurno();
                    return;
                }
            }

            seHaMovido = true;
            if (avatar.isAvanzado()) {
                if(avatar.getTipo().equals("Pelota")){
                    moverJugadorPelota(valor1 + valor2);
                } else if (avatar.getTipo().equals("Coche")){
                    moverJugadorCoche(valor1 + valor2);
                }
            } else avatar.moverAvatar(tablero.getPosiciones(), (valor1 + valor2));

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
    public void lanzarDados(Jugador jugador) {
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
            dadosDobles = true;
        } else {
            System.out.println("No han salido dobles... El jugador pierde el turno");
            dadosDobles = false;
            jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
            acabarTurno();
        }
    }

    /***************************/
    /*SECCIÓN POST LANZAR DADOS*/
    /***************************/

    /**
     * Método para evaluar si el jugador es solvente en la casilla en la que cae.
     *
     * Este método comprueba la solvencia del jugador actual en la casilla correspondiente.
     * Si el jugador no es solvente, la partida se finalizará. Si es solvente y la casilla
     * requiere el pago (Solar, Servicios o Transporte), se calcula el alquiler correspondiente.
     * En el caso de que el jugador caiga en una casilla de Impuestos, se deduce el impuesto
     * de la fortuna del jugador y se suma al bote del Parking.
     */
    public void evaluacion() {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
        solvente = casillaActual.evaluarCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
        if (!solvente) {
            System.out.println("El jugador " + jugadorActual.getNombre() + " no es solvente. Necesita conseguir dinero");
            float dineroNecesario = casillaActual.calcularDineroNecesarioCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
            //Manejmaos o caso de que se teña que declarar en bancarrota dentro de conseguir dinero
            conseguirDinero(dineroNecesario);
            if(jugadorActual.getFortuna() == 0) {
                return;
            }
        }
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
                if(seHaMovido || !jugadorActual.getAvatar().isAvanzado()) {
                    barajas.evaluarSuerte(banca, jugadorActual, tablero);
                }
                if(jugadorActual.getEnCarcel()) {
                    dadosDobles = false;
                    acabarTurno();
                }
            }
            case "Comunidad" -> {
                if(seHaMovido || !jugadorActual.getAvatar().isAvanzado()) {
                    //CAMBIAR
                    //barajas.evaluarComunidad(banca, jugadorActual, tablero, jugadores, this);
                }
                if(jugadorActual.getEnCarcel()) {
                    dadosDobles = false;
                    acabarTurno();
                }
            }
        }
    }

    /**
     * Método que ajusta el valor de las casillas de tipo "Solar" propiedad de la banca
     * si todos los jugadores han completado al menos 4 vueltas al tablero.
     * El valor de estas casillas aumenta un 5% cada vez que un jugador complete un múltiplo de 4 vueltas.
     */
    public void VueltasTablero() {
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

    /**********************/
    /*SECCIÓN DE EDIFICIOS*/
    /**********************/

    public void edificar(String palabra) {
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = jugador.getAvatar().getLugar();
        switch (palabra) {
            case "Casa":
                if(casilla.edificarCasa(jugador, contadorCasa)){
                    contadorCasa++;
                }
                casilla.modificarAlquiler();
                break;
            case "Hotel":
                if(casilla.edificarHotel(jugador, contadorHotel)){
                    contadorHotel++;
                }
                casilla.modificarAlquiler();
                break;
            case "Piscina":
                if(casilla.edificarPiscina(jugador, contadorPiscina)){
                    contadorPiscina++;
                }
                casilla.modificarAlquiler();
                break;
            case "PistaDeporte":
                if(casilla.edificarPistaDeporte(jugador, contadorPistaDeporte)){
                    contadorPistaDeporte++;
                }
                casilla.modificarAlquiler();
                break;
            default:
                System.out.println("Edificio no válido.");
                break;
        }
    }

    /**
     * Metodo que permite listar los edificios que han sido construídos.
     */
    public void listarEdificios() {
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
    public void listarEdificiosGrupo(String color) {
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

    public void ventaEdificio(String tipo, String nombreCasilla, String cantidad) {
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
            return;
        }
        casilla.venderEdificios(tipo, contador);
        casilla.modificarAlquiler();
    }

    /**********************************/
    /*SECCIÓN ACABAR TURNO Y DERIVADOS*/
    /**********************************/

    /* Método que realiza las acciones asociadas al comando 'acabar turno' */
    public void acabarTurno() {
        if(!solvente) {
            System.out.println("El jugador no es solvente, no puede acabar turno.");
            return;
        }
        //Tiradas carcel xa axustadas na funcion SaliCarcel
        //if(!tirado || dado1.getValor() == dado2.getValor()) {
        if(jugadores.get(turno).getEnCarcel()) {
            dadosDobles = false;
            tirado = true;
        }
        if(!tirado || dadosDobles) {
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
        dadosDobles = false;

        if (jugador.getAvatar().getTipo().equals("Coche")) {
            saltoMovimiento = 3;
        } else if (jugador.getAvatar().getTipo().equals("Pelota")) {
            saltoMovimiento = 0;
        }
    }

    /**
     * Método que imprime por pantalla información sobre el estado financiero de un jugador después de su turno.
     * Muestra la fortuna, los gastos y las propiedades actuales del jugador.
     *
     * @param jugador El jugador del cual se generará la información.
     * @return Una cadena formateada que contiene la fortuna, los gastos y las propiedades del jugador.
     */
    public String infoTrasTurno(Jugador jugador) {
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

    /*****************************************************/
    /*SECCIÓN DE HIPOTECAR, CONSEGUIR DINERO Y BANCARROTA*/
    /*****************************************************/

    /**
     * Método que permite hipotecar una casilla.
     * Verifica que la casilla exista, que el jugador sea el propietario, que no tenga edificios y que no esté ya hipotecada.
     * Si todas las condiciones se cumplen, hipoteca la casilla y actualiza la fortuna del jugador y de la banca.
     *
     * @param nombreCasilla El nombre de la casilla a hipotecar.
     */
    public void hipotecar(String nombreCasilla) {
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        Jugador jugadorActual = jugadores.get(turno);

        if (casilla == null) {
            System.out.println("Casilla no encontrada");
            return;
        }

        if (!casilla.getDuenho().equals(jugadorActual)) {
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
            return;
        }
        if (!casilla.getEdificios().isEmpty()) {
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Tiene edificios construidos");
            return;
        }
        if (casilla.isHipotecado()) {
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada");
            return;
        }

        System.out.println("El jugador " + jugadorActual.getNombre() + " hipoteca " + nombreCasilla + " por " + casilla.getHipoteca());
        casilla.setHipotecado(true);
        jugadorActual.sumarFortuna(casilla.getHipoteca());
        banca.sumarFortuna(-casilla.getHipoteca());
    }

    public void deshipotecar(String nombreCasilla) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);

        if (casilla == null) {
            System.out.println("Casilla no encontrada");
            return;
        }

        if (!casilla.getDuenho().equals(jugadorActual)) {
            System.out.println(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
            return;
        }
        if (!casilla.isHipotecado()) {
            System.out.println(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No está hipotecada");
            return;
        }

        float precioDeshipotecar = casilla.getHipoteca() * 1.1f;
        if(precioDeshipotecar > jugadorActual.getFortuna()) {
            System.out.println(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No tiene suficiente dinero");
            return;
        }

        System.out.println("El jugador " + jugadorActual.getNombre() + " paga " + precioDeshipotecar + " por deshipotecar "
                + nombreCasilla + ". Ahora puede recibir alquileres.");



        jugadorActual.sumarFortuna(-precioDeshipotecar);
        jugadorActual.sumarGastos(precioDeshipotecar);
        banca.sumarFortuna(precioDeshipotecar);
        casilla.setHipotecado(false);
    }

    /*
    /*Método que se llama cuando el jugador tiene que conseguir dinero vendiendo edificios, hipotecando propiedades y sino
     *debe declararse en bancarrota*/
    public void conseguirDinero(float dineroAConseguir) {
        float dineroConseguido = 0;
        int contadorPropiedades = 0;
        ArrayList<Casilla> solares = new ArrayList<>();
        int contadorEdificios = 0;
        ArrayList<Edificio> edificiosLista = new ArrayList<>();
        Jugador jugadorActual = jugadores.get(turno);
        for (Casilla propiedad : jugadorActual.getPropiedades()) {
            if(propiedad.getEdificios().isEmpty() && !propiedad.isHipotecado()) {
                contadorPropiedades++;
                solares.add(propiedad);
            }
        }
        for (Edificio edificio : jugadorActual.getEdificios()) {
            contadorEdificios++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("El jugador no tiene suficiente dinero para pagar. Debe vender edificios y/o hipotecar propiedades.");
        if (jugadorActual.getEdificios().isEmpty() && contadorPropiedades == 0) {
            System.out.println("El jugador no tiene propiedades ni edificios para vender. Se declara en bancarrota.");
            bancarrota(false);
        } else {
            System.out.println("El jugador tiene propiedades y/o edificios. ¿Qué desea hipotecar/vender? (propiedades[1]/edificios[2]) ");
            int opcion;
            do {
                opcion = scanner.nextInt();
                if (opcion != 1 && opcion != 2) {
                    System.out.println("Opción no válida. Introduzca 1 para propiedades o 2 para edificios.");
                }
            } while (opcion != 1 && opcion != 2);
            if (opcion == 1) { //Hipotecar propiedades
                System.out.println("Propiedades hipotecables:");
                for(Casilla casilla : solares) {
                    System.out.println(casilla.getNombre());
                }
                if(contadorPropiedades != 0) {
                    dineroConseguido = 0;
                    while((dineroConseguido < dineroAConseguir)) {
                        if(contadorPropiedades == 0) {
                            break;
                        }
                        System.out.println("Introduce el nombre de la propiedad que quieres hipotecar:");
                        String nombrePropiedad = scanner.next();
                        Casilla propiedadHipotecar = null;
                        for (Casilla propiedad : jugadorActual.getPropiedades()) {
                            if (propiedad.getNombre().equals(nombrePropiedad) && !propiedad.isHipotecado()) {
                                propiedadHipotecar = propiedad;
                                break;
                            }
                        }
                        while (propiedadHipotecar == null) {
                            System.out.println("Propiedad no válida. Introduce un nombre válido:");
                            nombrePropiedad = scanner.next();
                            for (Casilla propiedad : jugadorActual.getPropiedades()) {
                                if (propiedad.getNombre().equals(nombrePropiedad) && !propiedad.isHipotecado()) {
                                    propiedadHipotecar = propiedad;
                                    break;
                                }
                            }
                        }
                        contadorPropiedades--;
                        dineroConseguido += propiedadHipotecar.getHipoteca();
                        hipotecar(propiedadHipotecar.getNombre());
                    }
                } else {
                    System.out.println("El jugador no tiene propiedades sin edificar. Debe vender los edificios antes de hipotecar una propiedad.");
                }
            } else {
                dineroConseguido = 0;
                System.out.println("Edificios:");
                for(Edificio edificio : jugadorActual.getEdificios()) {
                    System.out.println(edificio.getIdEdificio());
                }
                if(contadorEdificios != 0) {
                    while((dineroConseguido < dineroAConseguir)) {
                        if(contadorEdificios == 0) {
                            break;
                        }
                        System.out.println("Introduce el ID del edificio que quieres vender:");
                        String idEdificio = scanner.next();
                        Edificio edificioVender = null;
                        for (Edificio edificio : jugadorActual.getEdificios()) {
                            if (edificio.getIdEdificio().equals(idEdificio)) {
                                edificioVender = edificio;
                                break;
                            }
                        }
                        while (edificioVender == null) {
                            System.out.println("Edificio no válido. Introduce un ID válido:");
                            idEdificio = scanner.next();
                            for (Edificio edificio : jugadorActual.getEdificios()) {
                                if (edificio.getIdEdificio().equals(idEdificio)) {
                                    edificioVender = edificio;
                                    break;
                                }
                            }
                        }
                        Casilla casilla = edificioVender.getCasilla();
                        casilla.venderEdificios(edificioVender.getTipo(), 1);
                        dineroConseguido += edificioVender.getValor() / 2;
                        contadorEdificios--;
                        if (casilla.getEdificios().isEmpty() && !casilla.isHipotecado()) {
                            solares.add(casilla);
                            contadorPropiedades++;
                        }
                    }
                } else {
                    System.out.println("El jugador no tiene edificios para vender.");
                }
            }
            if(dineroConseguido > dineroAConseguir) {
                System.out.println("El jugador ha conseguido suficiente dinero para pagar. Se ha vuelto solvente.");
                if(jugadorActual.getAvatar().getLugar().getNombre().equals("Cárcel")) {
                    System.out.println("Solicite de nuevo salir de la cárcel.");
                }
                solvente = true;
                jugadorActual.getAvatar().setConseguirDinero(false);
            }
            else {
                if(contadorEdificios == 0 && contadorPropiedades == 0) {
                    System.out.println("El jugador no ha conseguido dinero suficiente y no le quedan propiedades ni edificios para vender. Se declara en bancarrota.");
                    bancarrota(false);
                }
                else {
                    System.out.println("El jugador no ha conseguido suficiente dinero para pagar. Debe seguir vendiendo propiedades.");
                    conseguirDinero(dineroAConseguir - dineroConseguido);
                }
            }

        }
    }

    public void bancarrota(boolean voluntario) {
        Jugador jugActual = jugadores.get(turno);
        Avatar avActual = jugActual.getAvatar();
        String motivo;
        if (voluntario) {
            motivo = "Voluntario";
        }
        else if (avActual.getLugar().getTipo().equals("Solar") ||
                avActual.getLugar().getTipo().equals("Servicios") ||
                avActual.getLugar().getTipo().equals("Transporte")) {
            if (avActual.getLugar().getDuenho().equals(banca) ||
                    avActual.getLugar().getDuenho().equals(jugActual)) {
                return;
            }
            motivo = "Alquiler";
        } else {
            motivo = "Banca";
        }
        jugActual.bancarrota(motivo, banca);
        jugadores.remove(jugActual);
        avActual.getLugar().eliminarAvatar(avActual);
        avatares.remove(avActual);
        turno = turno % jugadores.size();
        lanzamientos  = 0;
        tirado = false;
        solvente = true;
        jugActual = null;
        avActual = null;
        //Aqueí falta eliminar o avatar do tablero
    }

    /**************************************************/
    /*SECCIÓN DE SALIR CÁRCEL, COMPRAR Y INTRODUCIRNUM*/
    /**************************************************/

    /**
     * Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
     * Permite al jugador intentar salir de la cárcel, ya sea tirando los dados o pagando la fianza,
     * dependiendo de sus opciones y recursos disponibles.
     */
    public void salirCarcel() {
        Jugador jugActual = jugadores.get(turno);
        Casilla carcel = tablero.encontrar_casilla("Cárcel");

        // Verificar si el jugador está en la cárcel
        if (!jugActual.getEnCarcel()) {
            System.out.println("El jugador no está en la cárcel. No puede usar el comando.");
            return;
        }

        float fianza = carcel.getImpuesto();
        boolean puedePagarFianza = jugActual.getFortuna() >= fianza;

        // Siempre ofrecer la opción de pagar la fianza si puede tirar dados
        if (jugActual.getTiradasCarcel() < 3) {
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
                lanzarDados(jugActual);
            } else if (puedePagarFianza) {
                jugActual.sumarFortuna(-fianza);
                banca.sumarFortuna(fianza);
                jugActual.setEnCarcel(false);
                jugActual.setTiradasCarcel(0);
                System.out.println("El jugador ha pagado la fianza y ha salido de la cárcel. Puede tirar los dados.");
                tirado = false;
            } else {
                System.out.println("El jugador no tiene suficiente dinero para pagar la fianza.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                conseguirDinero(fianza - jugActual.getFortuna());
                if(jugActual.getTiradasCarcel() < 3) {
                    solvente = true;
                }
            }
        } else if (puedePagarFianza) {
            System.out.println("El jugador puede pagar la fianza para salir de la cárcel.");
            jugActual.sumarFortuna(-fianza);
            banca.sumarFortuna(fianza);
            jugActual.setEnCarcel(false);
            jugActual.setTiradasCarcel(0);
            System.out.println("El jugador ha pagado la fianza y ha salido de la cárcel. Puede tirar los dados.");
            tirado = false;
        } else {
            System.out.println("El jugador no tiene suficiente dinero para pagar la fianza ni puede tirar los dados.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            conseguirDinero(fianza - jugActual.getFortuna());
            if(jugActual.getTiradasCarcel() < 3) {
                solvente = true;
            }
        }
    }

    /**
     * Método que ejecuta las acciones asociadas al comando 'comprar nombre_casilla',
     * permitiendo al jugador adquirir una casilla si cumple con las condiciones.
     *
     * @param nombre El nombre de la casilla que el jugador desea comprar.
     */
    public void comprar(String nombre) {
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
        if(comprador.getAvatar().isAvanzado()) {
            if (compraMovimientoCoche) {
                System.out.println("El jugador ya ha comprado una propiedad en este turno.");
                return ;
            } else {
                compraMovimientoCoche = true;
                casillaDeseada.comprarCasilla(comprador, banca);
                return ;
            }
        }

        casillaDeseada.comprarCasilla(comprador, banca);
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
    public static int introducirNum(int min, int max){
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

    /**
     * Metodo que convierte una lista de elementos en una representación de cadena.
     * Si la lista contiene cadenas, se devuelven como una lista de cadenas.
     * Si la lista contiene objetos de tipo Casilla, se devuelven sus nombres.
     * Si la lista contiene objetos de tipo Edificios, se devuelven sus id's.
     *
     * @param array La lista de elementos a convertir en cadena. Puede contener
     *              objetos de tipo String o Casilla.
     * @return Una representación de cadena de la lista, con los elementos
     *         separados por comas y encerrados entre corchetes. Si la lista está
     *         vacía, se devuelve un guion ("-").
     */
    public static String listaArray(ArrayList<?> array) {
        StringBuilder listaArray = new StringBuilder();

        if (!array.isEmpty()) {
            Object firstElement = array.getFirst();

            if (firstElement instanceof String) {
                listaArray.append("[").append((String) firstElement);
            } else if (firstElement instanceof Casilla) {
                listaArray.append("[").append(((Casilla) firstElement).getNombre());
            } else if (firstElement instanceof Edificio) {
                listaArray.append("[").append(((Edificio) firstElement).getIdEdificio());
            }

            for (int i = 1; i < array.size(); ++i) {
                Object element = array.get(i);
                if (element instanceof String) {
                    listaArray.append(", ").append((String) element);
                } else if (element instanceof Casilla) {
                    listaArray.append(", ").append(((Casilla) element).getNombre());
                } else if (element instanceof Edificio) {
                    listaArray.append(", ").append(((Edificio) element).getIdEdificio());
                }
            }

            listaArray.append("]");
        } else {
            listaArray = new StringBuilder("-");
        }
        return listaArray.toString();
    }




}
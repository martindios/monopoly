package monopoly;

import monopoly.edificio.*;
import monopoly.casilla.*;
import monopoly.casilla.accion.*;
import monopoly.casilla.propiedad.*;
import monopoly.excepcion.*;
import monopoly.excepcion.excepcionCarcel.*;
import monopoly.excepcion.excepcionDados.*;
import monopoly.excepcion.excepcionEdificar.*;
import monopoly.excepcion.excepcionEntradaUsuario.*;
import partida.avatar.Avatar;
import partida.Dado;
import partida.Jugador;
import partida.avatar.*;

import java.util.*;

public class Juego implements Comando{

    /**********Atributos**********/
    private final ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private final ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private final Tablero tablero; //Tablero en el que se juega.
    private final Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private final Dado dado2;
    private final Jugador banca; //El jugador banca.
    private final Baraja barajas;
    private static boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private int maxJugadores = 0;
    private int jugadoresActuales = 0;
    private boolean finalizarPartida = false;
    private int contadorCasa;
    private int contadorHotel;
    private int contadorPiscina;
    private int contadorPistaDeporte;
    private int contadorTratos;

    private static int saltoMovimiento; //Variable para controlar el movimiento del avatar en modo avanzado
    private static boolean seHaMovido; //Booleano para comprobar si el jugador se ha movido en su turno
    private boolean compraMovimientoCoche;
    private static boolean dadosDobles;
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();

    //Instancias la consola en esta clase

    /**********Constructor**********/
    public Juego() throws Exception {
        this.jugadores = new ArrayList<>();
        this.avatares = new ArrayList<>();
        this.barajas = new Baraja();
        this.turno = 0;
        this.lanzamientos = 0;
        tirado = false;
        this.solvente = true;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.banca = new Jugador();
        saltoMovimiento = 0;
        seHaMovido = false;
        this.compraMovimientoCoche = false;
        this.tablero = new Tablero(banca);


        preIniciarPartida();

    }

    /*Getters*/

    public static boolean isDadosDobles() {
        return dadosDobles;
    }

    public boolean isFinalizarPartida() {
        return finalizarPartida;
    }

    public static boolean isSeHaMovido() {
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

    public static boolean isTirado() {
        return tirado;
    }

    public static int getSaltoMovimiento() {
        return saltoMovimiento;
    }

    /*Setters*/


    public static void setSeHaMovido(boolean seHaMovido) {
        Juego.seHaMovido = seHaMovido;
    }

    public static void setTirado(boolean tirado) {
        Juego.tirado = tirado;
    }

    public static void setDadosDobles(boolean dadosDobles) {
        Juego.dadosDobles = dadosDobles;
    }

    public static void setSaltoMovimiento(int saltoMovimiento) {
        Juego.saltoMovimiento = saltoMovimiento;
    }



    /**********Métodos**********/

    /******************************/
    /*SECCIÓN DE INICIO DE PARTIDA*/
    /******************************/

    /*Método para mostrar la pantalla de inicio y crear los jugadores*/
    public void preIniciarPartida() throws Exception {
        imprimirLogo();

        /*establece el número de jugadores que van a jugar la partida*/
        consolaNormal.imprimir("-----Número de jugadores-----");

        maxJugadores = introducirNum(2, 6);

        crearJugadores();


    }
  

    /*Método que imprime el logo mediante un for con un efecto visual*/
    public void imprimirLogo() throws Exception {
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
                consolaNormal.imprimirSinSalto(s.charAt(i));
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new Exception("Error en la impresión del logo");
                }
            }
        }
    }

    /**********************************/
    /*SECCIÓN DE CREACIÓN DE JUGADORES*/
    /**********************************/

    /*Método que crea a todos los jugadores que van a jugar*/
    public void crearJugadores() throws ExcepcionEntradaUsuario {
        consolaNormal.leer();//Limpiar buffer
        /*Comprobación para que no se exceda el número de jugadores establecido*/
        while (jugadoresActuales < maxJugadores) {
            consolaNormal.imprimirSinSalto("Introduce el comando: ");
            String comando = consolaNormal.leer();
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
                                throw new ExcepcionEntradaUsuario("El avatar introducido no está disponible [Coche, Esfinge, Sombrero, Pelota]");
                            }
                        } else {
                            throw new ExcepcionEntradaUsuario("Jugador ya existente");
                        }
                    } else {
                        throw new ExcepcionFormatoIncorrecto("crear jugador nombre tipoAvatar");
                    }
                } else {
                    throw new ExcepcionEntradaUsuario("Debe primero crear los jugadores para poder jugar");
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
    public void darAltaJugador(String nombre, String tipoAvatar){
        Casilla casillaInicio = tablero.encontrar_casilla("Salida"); //Se busca la casilla correspondiente a la salida
        //Se crea el jugador y se añade al array que contiene a todos los participantes de la partida
        Jugador jugadorCreado = new Jugador(nombre, tipoAvatar, casillaInicio, avatares);
        jugadores.add(jugadorCreado);
        //Se muestra por pantalla la información del jugador creado
        consolaNormal.imprimir("{");
        consolaNormal.imprimir("    nombre: " + jugadorCreado.getNombre() + ",");
        consolaNormal.imprimir("    avatar: " + jugadorCreado.getAvatar().getId());
        consolaNormal.imprimir("}");
    }

    /*******************************/
    /*SECCIÓN MOVIMIENTOS AVANZADOS*/
    /*******************************/

    /*Método para activar el modo avanzado del jugador*/
    public void modoAvanzado() throws ExcepcionMovimientosAvanzados {
        Avatar avatarActual = jugadores.get(turno).getAvatar();
        if(!tirado) {
            if(avatarActual.isAvanzado()) {
                avatarActual.setAvanzado(false);
                consolaNormal.imprimir("A partir de ahora el avatar " + avatarActual.getId() + ", de tipo " + avatarActual.getClass().getSimpleName() + ", se moverá en modo normal.");
            }
            else {
                if (jugadores.get(turno).getAvatar() instanceof Coche) {
                    Juego.setSaltoMovimiento(4);
                }
                avatarActual.setAvanzado(true);
                consolaNormal.imprimir("A partir de ahora el avatar " + avatarActual.getId() + ", de tipo " + avatarActual.getClass().getSimpleName() + ", se moverá en modo avanzado.");
            }
        }
        else {
            throw new ExcepcionMovimientosAvanzados("El jugador debe esperar al inicio del siguiente turno para cambiar el modo de movimiento. En este turno ya ha tirado.");
        }
    }

    /**********************************/
    /*SECCIÓN DE DESCRIPCIÓN DE CLASES*/
    /**********************************/

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    public void descJugador(String nombre) throws ExcepcionEntidadNoExistente {
        //Comprobar jugador
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) { //getter de getNombre de jugador
                consolaNormal.imprimir(jugador.info());
                return;
            }
        }
        throw new ExcepcionEntidadNoExistente("jugador");
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    public void descAvatar(String ID) throws ExcepcionEntidadNoExistente {
        //Comprueba que el ID que se pide describir es uno existente
        for (Avatar avatar : avatares) {
            if (avatar.getId().equalsIgnoreCase(ID)) {
                consolaNormal.imprimir(avatar.infoAvatar());
                return;
            }
        }
        throw new ExcepcionEntidadNoExistente("avatar");
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    public void descCasilla(String NombreCasilla) throws Exception {
        Casilla casillaBuscada = tablero.encontrar_casilla(NombreCasilla);
        if(casillaBuscada == null) {
            throw new ExcepcionEntidadNoExistente("casilla");
        }
        else {
            consolaNormal.imprimir(casillaBuscada.infoCasilla());
        }
    }

    /*******************/
    /*SECCIÓN DE LISTAR*/
    /*******************/

    /*Método que realiza las acciones asociadas al comando 'listar enventa'*/
    public void listarVenta() throws ExcepcionNoHayPropiedadesVenta {
        boolean hayPropiedadesEnVenta = false;

        for(ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for(Casilla casilla : fila) {
                if(casilla instanceof Solar || casilla instanceof Transporte || casilla instanceof Servicio
                        && casilla.getDuenho().equals(banca)) {
                    hayPropiedadesEnVenta = true;
                    Propiedad propiedad = (Propiedad) casilla;
                    consolaNormal.imprimir(propiedad.casillaEnVenta());
                }
            }
        }

        if(!hayPropiedadesEnVenta) {
            throw new ExcepcionNoHayPropiedadesVenta("No hay propiedades en venta");
        }

    }

    /* Método que realiza las acciones asociadas al comando 'listar jugadores'*/
    public void listarJugadores() {
        consolaNormal.imprimir("Jugadores:");
        for(Jugador jugador : jugadores) {
            consolaNormal.imprimir(jugador.info());
        }
    }

    /* Método que realiza las acciones asociadas al comando 'listar avatares' */
    public void listarAvatares() {
        consolaNormal.imprimir("Avatares:");
        for(Avatar avatar : avatares) {
            consolaNormal.imprimir(avatar.infoAvatar());
        }
    }

    /*************************/
    /*SECCIÓN DE ESTADÍSTICAS*/
    /*************************/

    /* Método que realiza las acciones asociadas al comando 'estadisticas jugador'.
     * Parámetro: nombre del jugador a describir.
     */
    public void estadisticasJugador(String jugadorStr) throws ExcepcionEntidadNoExistente {
        for(Jugador jugador : jugadores) {
            if(jugador.getNombre().equals(jugadorStr)) {
                consolaNormal.imprimir("{");
                consolaNormal.imprimir("\tdineroInvertido: " + jugador.getDineroInvertido());
                consolaNormal.imprimir("\tpagoTasasEImpuestos: " + jugador.getPagoTasasEImpuestos());
                consolaNormal.imprimir("\tpagoDeAlquileres: " + jugador.getPagoDeAlquileres());
                consolaNormal.imprimir("\tcobroDeAlquileres: " + jugador.getCobroDeAlquileres());
                consolaNormal.imprimir("\tpasarPorCasillaDeSalida: " + jugador.getPasarPorCasillaDeSalida());
                consolaNormal.imprimir("\tpremiosInversionesOBote: " + jugador.getPremiosInversionesOBote());
                consolaNormal.imprimir("\tvecesEnLaCarcel: " + jugador.getVecesEnLaCarcel());
                consolaNormal.imprimir("}");
                return ;
            }
        }
        throw new ExcepcionEntidadNoExistente("jugador");
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

        consolaNormal.imprimir("{");
        consolaNormal.imprimirSinSalto("\tcasillaMasRentable: ");
        imprimirNombres(casillasMasRentables);
        consolaNormal.imprimirSinSalto("\tgrupoMasRentable: ");
        imprimirNombres(calcularGruposMasRentables());
        consolaNormal.imprimirSinSalto("\tcasillaMasFrecuentada: ");
        imprimirNombres(casillasMasFrecuentadas);
        consolaNormal.imprimirSinSalto("\tjugadorMasVueltas: ");
        imprimirNombres(jugadoresMasVueltas);
        consolaNormal.imprimirSinSalto("\tjugadorMasVecesDados: ");
        imprimirNombres(jugadoresMasTiradasDados);
        consolaNormal.imprimirSinSalto("\tjugadorEnCabeza: ");
        imprimirNombres(jugadoresEnCabeza);
        consolaNormal.imprimir("}");
    }

    /**
     * Método genérico que imprime los nombres de una lista de objetos.
     * Si la lista está vacía, imprime un guion ("-").
     *
     * @param lista La lista de objetos cuyos nombres se van a imprimir.
     */
    public void imprimirNombres(List<?> lista) {
        if (lista.isEmpty()) {
            consolaNormal.imprimir("-");
        } else {
            for (int i = 0; i < lista.size(); i++) {
                Object elemento = lista.get(i);

                if (elemento instanceof Jugador) {
                    consolaNormal.imprimirSinSalto(((Jugador) elemento).getNombre());
                } else if (elemento instanceof Casilla) {
                    consolaNormal.imprimirSinSalto(((Casilla) elemento).getNombre());
                } else if (elemento instanceof Grupo) {
                    consolaNormal.imprimirSinSalto(((Grupo) elemento).getNombreGrupo());
                }

                if (i < lista.size() - 1) {
                    consolaNormal.imprimirSinSalto(", ");
                }
            }
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
                if(casilla instanceof Solar solar) {
                    if(solar.getTotalAlquileresPagados() > maxAlquileresPagados) {
                        maxAlquileresPagados = solar.getTotalAlquileresPagados();
                        casillasMasRentables.clear();
                        casillasMasRentables.add(solar);
                    } else if(solar.getTotalAlquileresPagados() == maxAlquileresPagados) {
                        casillasMasRentables.add(solar);
                    }
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
                if(casilla instanceof Solar solar) {
                    Grupo grupo = solar.getGrupo();
                    rentabilidadPorGrupo.put(grupo, rentabilidadPorGrupo.getOrDefault(grupo, 0f)
                            + solar.getTotalAlquileresPagados());
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
    public void lanzarDados(int tirada1, int tirada2) throws Exception {
        if(!solvente) {
            throw new Exception("El jugador no es solvente, no puede lanzar los dados.");
        }
        if (!tirado || dadosDobles) {
            if (saltoMovimiento != 0 && jugadores.get(turno).getAvatar().getClass().getSimpleName().equals("Pelota")) {
                throw new Exception("El jugador está en modo avanzado, no puede lanzar los dados.");
            }

            Jugador jugador = jugadores.get(turno);
            Avatar avatar = avatares.get(turno);

            if(jugador.getEnCarcel()) {
                throw new ExcepcionJugadorEnCarcel(", no puede lanzar los dados para moverse.");
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
            consolaNormal.imprimir("Dado 1: " + valor1);
            consolaNormal.imprimir("Dado 2: " + valor2);

            //Sin modo avanzado o con pelota
            if(!avatar.isAvanzado() || avatar instanceof Pelota) {
                if (valor1 == valor2) {
                    consolaNormal.imprimir("¡Has sacado dobles!");
                    dadosDobles = true;
                    if (lanzamientos == 3) {
                        throw new ExcepcionIrACarcel("¡Tres dobles consecutivos! El jugador va a la cárcel.");
                    } else {
                        consolaNormal.imprimir("Puedes lanzar otra vez.");
                        tirado = false;
                    }
                } else {
                    dadosDobles = false;
                }
            } else if (avatar instanceof Coche){ //Modo avanzado con coche
                if(lanzamientos >= 4 && (valor1 == valor2)) {
                    consolaNormal.imprimir("Puede lanzar otra vez.");
                    dadosDobles = true;
                    tirado = false;
                } else if(lanzamientos < 4 && (valor1 + valor2) < 4) {
                    throw new ExcepcionDadosCoche("Has sacado un valor menor que 4, no puedes lanzar otra vez");
                }
                if(lanzamientos >= 5) {
                    throw new ExcepcionIrACarcel("El jugador va a la cárcel");
                }
            }

            seHaMovido = true;
            if (avatar.isAvanzado()) {
                if(avatar instanceof Pelota pelota){
                    pelota.moverJugador(jugador, valor1 + valor2, tablero);
                } else if (avatar instanceof Coche coche){
                    coche.moverJugador(jugador, valor1 + valor2, tablero);
                }
            } else avatar.moverBasico(tablero.getPosiciones(), (valor1 + valor2));

            if(jugador.getEnCarcel()) {
                acabarTurno();
            }

        }
        else {
            throw new ExcepcionDados("Ya has lanzado el dado en este turno.");
        }
    }

    /**
     * Método que maneja las posibles acciones al lanzar los dados cuando un jugador está encarcelado,
     * mostrando los resultados y tomando decisiones en función de los valores obtenidos.
     *
     * @param jugador El jugador que está encarcelado y realiza la tirada.
     */
    public void lanzarDados(Jugador jugador) throws Exception {
        int valor1 = dado1.hacerTirada();
        int valor2 = dado2.hacerTirada();
        tirado = true;
        lanzamientos++;
        jugador.sumarVecesTiradasDados();
        consolaNormal.imprimir("Dado 1: " + valor1);
        consolaNormal.imprimir("Dado 2: " + valor2);
        if (valor1 == valor2) {
            consolaNormal.imprimir("¡Dobles! El jugador avanza " + (valor1 + valor2) + " casillas y tiene derecho a otro lanzamiento.");
            jugador.getAvatar().moverBasico(tablero.getPosiciones(), valor1 + valor2); // Mover el avatar
            jugador.setTiradasCarcel(0); // Salir de la cárcel
            jugador.setEnCarcel(false);
            tirado = false; // Permitimos otro lanzamiento
            dadosDobles = true;
        } else {
            throw new ExcepcionCarcelTirarDados();
        }
    }

    /***************************/
    /*SECCIÓN POST LANZAR DADOS*/
    /***************************/

    /**
     * Método para evaluar si el jugador es solvente en la casilla en la que cae.
     * Este método comprueba la solvencia del jugador actual en la casilla correspondiente.
     * Si el jugador no es solvente, la partida se finalizará. Si es solvente y la casilla
     * requiere el pago (Solar, Servicios o Transporte), se calcula el alquiler correspondiente.
     * En el caso de que el jugador caiga en una casilla de Impuestos, se deduce el impuesto
     * de la fortuna del jugador y se suma al bote del Parking.
     */
    public void evaluacion() throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();
        solvente = casillaActual.evaluarCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
        if (!solvente) {
            consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " no es solvente. Necesita conseguir dinero");
            float dineroNecesario = casillaActual.calcularDineroNecesarioCasilla(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
            //Manejmaos o caso de que se teña que declarar en bancarrota dentro de conseguir dinero
            conseguirDinero(dineroNecesario);
            if(jugadorActual.getFortuna() == 0) {
                return;
            }
        }

        //Comprobar que hay en la casilla en la que se cae hay que pagar
        switch (casillaActual) {
            case Propiedad propiedad ->
                    propiedad.pagarAlquiler(jugadores.get(turno), banca, dado1.getValor() + dado2.getValor());
            case Impuesto _ -> {
                jugadorActual.sumarFortuna(-casillaActual.getImpuesto());
                jugadorActual.sumarGastos(casillaActual.getImpuesto());
                jugadorActual.sumarTasasEImpuestos(casillaActual.getImpuesto());
                Casilla bote = tablero.encontrar_casilla("Parking");
                bote.sumarValor(casillaActual.getImpuesto());

                consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " ha pagado en impuestos " + casillaActual.getImpuesto());
                banca.sumarFortuna(casillaActual.getImpuesto());
            }
            case AccionSuerte _ -> {
                if(seHaMovido || !jugadorActual.getAvatar().isAvanzado()) {
                    barajas.evaluarSuerte(banca, jugadorActual, tablero, jugadores);
                }
                if(jugadorActual.getEnCarcel()) {
                    dadosDobles = false;
                    acabarTurno();
                }
            }
            case AccionCajaComunidad _ -> {
                if(seHaMovido || !jugadorActual.getAvatar().isAvanzado()) {
                    barajas.evaluarCajaComunidad(banca, jugadorActual, tablero, jugadores);
                }
                if(jugadorActual.getEnCarcel()) {
                    dadosDobles = false;
                    acabarTurno();
                }
            }
            default -> throw new Exception("Error en la evaluación de casilla");
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
                    if (casilla instanceof Solar && casilla.getDuenho().equals(banca)) {
                        casilla.sumarValor(casilla.getValor() * 0.05f); // Aumenta el valor de la casilla
                    }
                }
            }
            consolaNormal.imprimir("Todos los jugadores han completado 4 vueltas.\n" +
                    "El precio de los solares sin comprar aumenta un 5%");
        }
    }

    /**********************/
    /*SECCIÓN DE EDIFICIOS*/
    /**********************/

    public void edificar(String palabra) throws Exception {
        float factor;
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = jugador.getAvatar().getLugar();

        if(!(casilla instanceof Solar solar)) {
            throw new ExcepcionEdificarNoEdificable();
        }

        if(!casilla.getDuenho().equals(jugador)) {
            throw new ExcepcionEdificarNoPropietario();
        }

        switch (palabra) {
            case "Casa":
                factor = 0.6f;
                if (jugador.getFortuna() < casilla.getValor() * factor) {
                    throw new ExcepcionEdificarNoDinero(palabra);
                }
                if(solar.edificarCasa(jugador, contadorCasa)){
                    contadorCasa++;
                }
                solar.modificarAlquiler();
                break;
            case "Hotel":
                factor = 0.6f;
                if (jugador.getFortuna() < casilla.getValor() * factor) {
                    throw new ExcepcionEdificarNoDinero(palabra);
                }
                if(solar.edificarHotel(jugador, contadorHotel)){
                    contadorHotel++;
                }
                solar.modificarAlquiler();
                break;
            case "Piscina":
                factor = 0.4f;
                if (jugador.getFortuna() < casilla.getValor() * factor) {
                    throw new ExcepcionEdificarNoDinero(palabra);
                }
                if(solar.edificarPiscina(jugador, contadorPiscina)){
                    contadorPiscina++;
                }
                solar.modificarAlquiler();
                break;
            case "PistaDeporte":
                factor = 1.25f;
                if (jugador.getFortuna() < casilla.getValor() * factor) {
                    throw new ExcepcionEdificarNoDinero(palabra);
                }
                if(solar.edificarPistaDeporte(jugador, contadorPistaDeporte)){
                    contadorPistaDeporte++;
                }
                solar.modificarAlquiler();
                break;
            default:
                throw new ExcepcionEdificar("Edificio no válido.");
        }
    }

    /**
     * Metodo que permite listar los edificios que han sido construídos.
     */
    public void listarEdificios() throws Exception {
        boolean existenEdificios = false;
        for(Jugador jugador : jugadores) {
            if(!jugador.getEdificios().isEmpty()) {
                existenEdificios = true;
                break;
            }
        }

        if(existenEdificios) {
            consolaNormal.imprimir("Edificios construídos:");
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
            consolaNormal.imprimirStrBuilder(str);
        }
        else {
            throw new Exception("No existen edificios construídos actualmente.");
        }
    }

    /**
     * Metodo que permite listar los edificios construídos en un grupo de solares
     */
    public void listarEdificiosGrupo(String nombreGrupo) {
            ArrayList<Solar> solaresGrupo = new ArrayList<>();

            // Guardar todos los solares en un grupo específico
            for (Jugador jugador : jugadores) {
                for (Propiedad propiedad : jugador.getPropiedades()) {
                    if (propiedad instanceof Solar solar && solar.getGrupo().getNombreGrupo().equals(nombreGrupo)) {
                        solaresGrupo.add(solar);
                    }
                }
            }

            // Imprimir los edificios
            for (Solar solar : solaresGrupo) {
                ArrayList<String> hoteles = new ArrayList<>();
                ArrayList<String> casas = new ArrayList<>();
                ArrayList<String> piscinas = new ArrayList<>();
                ArrayList<String> pistasDeDeporte = new ArrayList<>();

                for (Edificio edificio : solar.getEdificios()) {
                    if (edificio instanceof Hotel) {
                        hoteles.add(edificio.getIdEdificio());
                    } else if (edificio instanceof Casa) {
                        casas.add(edificio.getIdEdificio());
                    } else if (edificio instanceof Piscina) {
                        piscinas.add(edificio.getIdEdificio());
                    } else if (edificio instanceof PistaDeporte) {
                        pistasDeDeporte.add(edificio.getIdEdificio());
                    }
                }

                consolaNormal.imprimir("{");
                consolaNormal.imprimir("\tpropiedad: " + solar.getNombre() + ",");
                consolaNormal.imprimir("\thoteles: " + (hoteles.isEmpty() ? "-" : hoteles) + ",");
                consolaNormal.imprimir("\tcasas: " + (casas.isEmpty() ? "-" : casas) + ",");
                consolaNormal.imprimir("\tpiscinas: " + (piscinas.isEmpty() ? "-" : piscinas) + ",");
                consolaNormal.imprimir("\tpistasDeDeporte: " + (pistasDeDeporte.isEmpty() ? "-" : pistasDeDeporte) + ",");
                consolaNormal.imprimir("\talquiler: " + solar.getImpuesto());
                consolaNormal.imprimir(" ");
                consolaNormal.imprimir("\tEdificios disponibles para edificar:");

                /*Comprobar qué se puede construir*/
                Grupo grupo = solar.getGrupo();
                int maxEdificios = grupo.getNumCasillas();

                if(hoteles.size() < maxEdificios) { //Si la cantidad de hoteles es menor a la permitida
                    if(casas.size() < 4) {
                        consolaNormal.imprimir("\t\tCasa: puede construir " + (4 - casas.size()) + " casas");
                    } else if (casas.size() == 4) {
                        consolaNormal.imprimir("\t\tHotel: Puede construir un hotel (reemplazando 4 casas)");
                    }
                }

                if(!hoteles.isEmpty() && casas.size() >= 2 && piscinas.size() < maxEdificios) {
                    consolaNormal.imprimir("\t\tPiscina: Puede construir una piscina");
                }

                if(hoteles.size() >= 2 && pistasDeDeporte.size() < maxEdificios) {
                    consolaNormal.imprimir("\t\tPista de Deporte: Puede construir una pista de deporte");
                }

                if(casas.size() >= maxEdificios && hoteles.size() >= maxEdificios &&
                        piscinas.size() >= maxEdificios && pistasDeDeporte.size() >= maxEdificios) {
                    consolaNormal.imprimir("\t\tNo se puede edificar más en la casilla");
                }

                if(solar.equals(solaresGrupo.getLast())) consolaNormal.imprimir("}");
                else consolaNormal.imprimir("},");

            }
    }

    /**
     * Método que permite vender edificios de un tipo específico en una casilla determinada.
     *
     * @param tipo El tipo de edificio a vender.
     * @param nombreCasilla El nombre de la casilla donde se encuentran los edificios.
     * @param cantidad La cantidad de edificios a vender.
     */
    public void ventaEdificio(String tipo, String nombreCasilla, String cantidad) throws ExcepcionEntidadNoExistente, ExcepcionEdificar {
        int contador = 0;
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla == null) {
            throw new ExcepcionEntidadNoExistente("casilla");
        }
        if(!(casilla instanceof Solar solar)) {
            throw new ExcepcionEdificar("La casilla no es un solar, no se pueden vender edificios.");
        }
        if(solar.getDuenho() != jugadores.get(turno)) {
            throw new ExcepcionEdificar("No eres propietario de la casilla, no puedes vender edificios.");
        }
        if(solar.getEdificios().isEmpty()) {
            throw new ExcepcionEdificar("No hay edificios en la casilla.");
        }


        //Declaramos una clase que no sabemos cuál es, solo sabemos que es derivada de Edificio
        Class<? extends Edificio> tipoDeseado = null;

        tipoDeseado = convertirStrClase(tipo, tipoDeseado);
        while(tipoDeseado == null) {
            consolaNormal.imprimirSinSalto("Tipo de edificio no válido. Introduzca un tipo válido: ");
            tipo = consolaNormal.leer();
            tipoDeseado = convertirStrClase(tipo, tipoDeseado);
        }

        for(Edificio edificio : solar.getEdificios()) {
            if(tipoDeseado.isInstance(edificio)) {
                contador++;
                if(contador == Integer.parseInt(cantidad)) {
                    break;
                }
            }
        }
        if(contador != Integer.parseInt(cantidad)) {
            throw new ExcepcionEdificar("No hay " + Integer.parseInt(cantidad) + " edificios del tipo " + tipo + " en la casilla.");
        }
        solar.venderEdificios(tipoDeseado, contador);
        solar.modificarAlquiler();
    }

    private Class<? extends Edificio> convertirStrClase(String str, Class<? extends Edificio> tipoDeseado) {
        try {
            //En tipo almacenamos el paquete donde está la clase (edificio) y la clase que queremos
            str = "monopoly.edificio." + str;
            //Hacemos que la clase tome el nombre de la clase que queremos
            Class<?> claseGeneral = Class.forName(str);
            //Si la clase que queremos no es derivada de Edificio
            if (!Edificio.class.isAssignableFrom(claseGeneral)) {
                consolaNormal.imprimir("El tipo de edificio " + str + " no es válido.");
                return null;
            }
            tipoDeseado = (Class<? extends Edificio>) claseGeneral;
            return tipoDeseado;
        } catch (ClassNotFoundException e) {
            consolaNormal.imprimir("Clase no encontrada para el tipo: " + str);
            return null;
        }
    }

    /**********************************/
    /*SECCIÓN ACABAR TURNO Y DERIVADOS*/
    /**********************************/

    /* Método que realiza las acciones asociadas al comando 'acabar turno' */
    public void acabarTurno() throws Exception {
        if(!solvente) {
            throw new Exception("El jugador no es solvente, no puede acabar turno.");
        }
        //Tiradas carcel xa axustadas na funcion SaliCarcel
        //if(!tirado || dado1.getValor() == dado2.getValor()) {
        if(jugadores.get(turno).getEnCarcel()) {
            dadosDobles = false;
            tirado = true;
        }
        if(!tirado || dadosDobles) {
            throw new Exception("No puedes acabar turno sin haber lanzado los dados.");
        }
        consolaNormal.imprimir(infoTrasTurno(jugadores.get(turno)));
        Jugador anterior = jugadores.get(turno);
        consolaNormal.imprimir("El jugador " + anterior.getNombre() + ", con avatar " + anterior.getAvatar().getId() + ", acaba su turno.");
        turno = (turno+1)%(jugadores.size());
        lanzamientos = 0;
        tirado = false;
        solvente = true;
        Jugador jugador = jugadores.get(turno);
        consolaNormal.imprimir("El turno le pertenece al jugador " + jugador.getNombre() + ". Con avatar " + jugador.getAvatar().getId() + ".");
        dadosDobles = false;

        if (jugador.getAvatar() instanceof Coche) {
            saltoMovimiento = 3;
        } else if (jugador.getAvatar() instanceof Pelota) {
            saltoMovimiento = 0;
        }
        //Una vez actualizados los atributos para comenzar el turno de otro jugador, se imprimen los tratos del jugador que recibe el turno
        listarTratos();
    }

    /**
     * Método que imprime por pantalla información sobre el estado financiero de un jugador después de su turno.
     * Muestra la fortuna, los gastos y las propiedades actuales del jugador.
     *
     * @param jugador El jugador del cual se generará la información.
     * @return Una cadena formateada que contiene la fortuna, los gastos y las propiedades del jugador.
     */
    public String infoTrasTurno(Jugador jugador) {
        ArrayList<Propiedad> props = jugador.getPropiedades();
        consolaNormal.imprimir("El estado financiero de " + jugador.getNombre() + " es:");
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

    /****************************************************************/
    /*SECCIÓN DE HIPOTECAR, CONSEGUIR DINERO, BANCARROTA Y DERIVADOS*/
    /****************************************************************/

    /**
     * Método que permite hipotecar una casilla.
     * Verifica que la casilla exista, que el jugador sea el propietario, que no tenga edificios y que no esté ya hipotecada.
     * Si todas las condiciones se cumplen, hipoteca la casilla y actualiza la fortuna del jugador y de la banca.
     *
     * @param nombreCasilla El nombre de la casilla a hipotecar.
     */
    public boolean hipotecar(String nombreCasilla) throws Exception {
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        Jugador jugadorActual = jugadores.get(turno);

        if (casilla == null) {
            throw new Exception("Casilla no encontrada");
        }

        if(!(casilla instanceof Propiedad propiedad)) {
            throw new Exception("La casilla no es una propiedad");
        }

        if (!propiedad.getDuenho().equals(jugadorActual)) {
            throw new Exception(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
        }

        if(propiedad instanceof Solar solar && !(solar.getEdificios().isEmpty())) {
            throw new Exception(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Tiene edificios construidos");
        }

        if (propiedad.isHipotecado()) {
            throw new Exception(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada");
        }

        consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " hipoteca " + nombreCasilla + " por " + propiedad.getHipoteca());
        propiedad.setHipotecado(true);
        jugadorActual.sumarFortuna(propiedad.getHipoteca());
        banca.sumarFortuna(-propiedad.getHipoteca());
        jugadorActual.getHipotecas().add(propiedad.getNombre());
        return true;
    }

    /**
     * Método para deshipotecar una casilla.
     * Verifica que la casilla exista, que el jugador sea el propietario, que esté hipotecada y que el jugador tenga suficiente dinero.
     * Si todas las condiciones se cumplen, deshipoteca la casilla y actualiza la fortuna del jugador y de la banca.
     *
     * @param nombreCasilla El nombre de la casilla a deshipotecar.
     */
    public void deshipotecar(String nombreCasilla) throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);

        if (casilla == null) {
            throw new Exception("Casilla no encontrada");
        }

        if(!(casilla instanceof Propiedad propiedad)) {
            throw new Exception("La casilla no es una propiedad");
        }

        if (!propiedad.getDuenho().equals(jugadorActual)) {
            throw new Exception(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
        }
        if (!propiedad.isHipotecado()) {
            throw new Exception(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No está hipotecada");
        }

        float precioDeshipotecar = propiedad.getHipoteca() * 1.1f;
        if(precioDeshipotecar > jugadorActual.getFortuna()) {
            throw new Exception(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No tiene suficiente dinero");
        }

        consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " paga " + precioDeshipotecar + " por deshipotecar "
                + nombreCasilla + ". Ahora puede recibir alquileres.");

        jugadorActual.sumarFortuna(-precioDeshipotecar);
        jugadorActual.sumarGastos(precioDeshipotecar);
        banca.sumarFortuna(precioDeshipotecar);
        propiedad.setHipotecado(false);
        jugadorActual.getHipotecas().remove(propiedad.getNombre());
    }

    private void propiedadesHipotecables(Jugador jugadorActual, ArrayList<Propiedad> propiedades) throws Exception {
        for(Propiedad propiedad : jugadorActual.getPropiedades()) {
            if(!propiedad.isHipotecado()) {
                if(propiedad instanceof Solar solar && solar.getEdificios().isEmpty()) {
                    propiedades.add(solar);
                } else {
                    propiedades.add(propiedad);
                }
            }
        }
        if(propiedades.isEmpty()) {
            throw new Exception("El jugador " + jugadorActual.getNombre() + " no tiene propiedades hipotecables");
        }
    }

    private void evaluarRecoleccionDinero(Jugador jugadorActual, int contadorPropiedades, int contadorEdificios, float dineroAConseguir, float dineroConseguido) throws Exception {
        if(dineroConseguido > dineroAConseguir) {
            consolaNormal.imprimir("El jugador ha conseguido suficiente dinero para pagar. Se ha vuelto solvente.");
            if(jugadorActual.getAvatar().getLugar().getNombre().equals("Cárcel")) {
                consolaNormal.imprimir("Solicite de nuevo salir de la cárcel.");
            }
            solvente = true;
            jugadorActual.getAvatar().setConseguirDinero(false);
        }
        else {
            if(contadorEdificios == 0 && contadorPropiedades == 0) {
                throw new ExcepcionBancarrota("El jugador no ha conseguido dinero suficiente y no le quedan propiedades ni edificios para vender. Se declara en bancarrota.");
            }
            else {
                consolaNormal.imprimir("El jugador no ha conseguido suficiente dinero para pagar. Debe seguir vendiendo propiedades.");
                conseguirDinero(dineroAConseguir - dineroConseguido);
            }
        }
    }

    /*
    /*Método que se llama cuando el jugador tiene que conseguir dinero vendiendo edificios, hipotecando propiedades y sino
     *debe declararse en bancarrota*/
    public void conseguirDinero(float dineroAConseguir) throws Exception {
        //Declaramos las variables necesarias
        float dineroConseguido;
        Jugador jugadorActual = jugadores.get(turno);
        ArrayList<Propiedad> propiedadesHipotecables = new ArrayList<>();
        ArrayList<Edificio> edificiosLista = new ArrayList<>(jugadorActual.getEdificios());

        int contadorEdificios = edificiosLista.size();

        //Comprobamos si el jugador tiene propiedades hipotecables
        propiedadesHipotecables(jugadorActual, propiedadesHipotecables);
        int contadorPropiedadesHipotecables = propiedadesHipotecables.size();

        consolaNormal.imprimir("El jugador no tiene suficiente dinero para pagar. Debe vender edificios y/o hipotecar propiedades.");
        consolaNormal.imprimir("El jugador tiene " + propiedadesHipotecables.size() +
                " propiedades hipotecables y " + edificiosLista.size() + "edificios en total.");

        if (propiedadesHipotecables.isEmpty()) { //Se comprueba únicamente propiedades porque si no tiene propiedades no tiene edificios
            throw new ExcepcionBancarrota("El jugador no tiene propiedades ni edificios para vender. Se declara en bancarrota.");
        } else {
            consolaNormal.imprimir("El jugador tiene propiedades y/o edificios. ¿Qué desea hipotecar/vender? (propiedades[1]/edificios[2]) ");
            int opcion;
            do {
                opcion = consolaNormal.leerInt();
                if (opcion != 1 && opcion != 2) {
                    consolaNormal.imprimir("Opción no válida. Introduzca 1 para propiedades o 2 para edificios.");
                }
            } while (opcion != 1 && opcion != 2);

            if (opcion == 1) { //Hipotecar propiedades
                consolaNormal.imprimir("Propiedades hipotecables:");
                for(Propiedad propiedad : propiedadesHipotecables) {
                    consolaNormal.imprimir(propiedad.getNombre());
                }
                dineroConseguido = 0;
                while((dineroConseguido < dineroAConseguir)) {
                    if (contadorPropiedadesHipotecables == 0) {
                        break;
                    }

                    Propiedad propiedadHipotecar = null;

                    while (propiedadHipotecar == null) {
                        consolaNormal.imprimir("Introduce el nombre de la propiedad que quieres hipotecar:");
                        String nombrePropiedad = consolaNormal.leerPalabra();
                        propiedadHipotecar = (Propiedad) tablero.encontrar_casilla(nombrePropiedad);
                    }

                    if(hipotecar(propiedadHipotecar.getNombre())){
                        contadorPropiedadesHipotecables--;
                        dineroConseguido += propiedadHipotecar.getHipoteca();
                    }
                }

            } else {
                dineroConseguido = 0;
                consolaNormal.imprimir("Edificios construidos:");
                for (Propiedad propiedad : jugadorActual.getPropiedades()) {
                    if (propiedad instanceof Solar solar && !solar.getEdificios().isEmpty()) {
                        consolaNormal.imprimirSinSalto(propiedad.getNombre() + ": ");
                        for (Edificio edificio : solar.getEdificios()) {
                            consolaNormal.imprimirSinSalto(edificio.getIdEdificio() + " ");
                        }
                        consolaNormal.imprimir("");
                    }
                }
                if(contadorEdificios != 0) {
                    while((dineroConseguido < dineroAConseguir)) {
                        if(contadorEdificios == 0) {
                            break;
                        }

                        consolaNormal.imprimir("Introduce la casilla de la que quieres vender un edificio: ");
                        String nombreCasilla = consolaNormal.leerPalabra();
                        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
                        Solar solar = null;
                        int tamanho = 0;
                        //Con la segunda condición nos aseguramos de que; si introduce un Solar que no es del jugador, a pesar de encontrarlo y ser instancia de Solar,
                        //el dueño no coincidirá, condición que tiene que ser verdadera para que pueda vender.
                        if (casilla instanceof Solar && casilla.getDuenho().equals(jugadorActual)) {
                            solar = (Solar) casilla;
                            tamanho = solar.getEdificios().size();
                        }

                        consolaNormal.imprimir("Introduce el tipo de edificio que quieres vender: ");
                        String tipo = consolaNormal.leerPalabra();
                        float valor;
                        switch (tipo) {
                            case "Casa", "Hotel" -> valor = casilla.getValor() * 0.6f;
                            case "Piscina" -> valor = casilla.getValor() * 0.4f;
                            case "PistaDeporte" -> valor = casilla.getValor() * 1.25f;
                            default -> valor = 0;
                        }

                        consolaNormal.imprimir("Introduce la cantidad de edificios que quieres vender: ");
                        String cantidadStr = consolaNormal.leerPalabra();

                        ventaEdificio(tipo, nombreCasilla, cantidadStr);

                        if(solar != null) {
                            //Si vendió los edificios, el tamaño anterior menos la cantidad de edificios que vendió es igual al tamaño actual. Entonces entra.
                            //Si no los vendió, el tamaño no será igual, no entra. Da igual que el nombre la casilla esté bien o no.
                            if(tamanho - Integer.parseInt(cantidadStr) == solar.getEdificios().size()) {
                                dineroConseguido += valor / 2;
                                contadorEdificios -= Integer.parseInt(cantidadStr);
                            }

                            if (solar.getEdificios().isEmpty() && !solar.isHipotecado() && solar.getNombre().equals(nombreCasilla)) {
                                propiedadesHipotecables.add(solar);
                                contadorPropiedadesHipotecables++;
                            }
                        }
                    }
                } else {
                    throw new ExcepcionEdificar("El jugador no tiene edificios para vender.");
                }
            }
            evaluarRecoleccionDinero(jugadorActual, contadorPropiedadesHipotecables, contadorEdificios, dineroAConseguir, dineroConseguido);
        }
    }

    public void bancarrota(boolean voluntario) {
        Jugador jugActual = jugadores.get(turno);
        Avatar avActual = jugActual.getAvatar();
        String motivo;
        if (voluntario) {
            motivo = "Voluntario";
        }
        else if (avActual.getLugar() instanceof Solar ||
                avActual.getLugar() instanceof Servicio ||
                avActual.getLugar() instanceof Transporte) {
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
    }

    /**************************************************/
    /*SECCIÓN DE SALIR CÁRCEL, COMPRAR E INTRODUCIRNUM*/
    /**************************************************/

    /**
     * Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
     * Permite al jugador intentar salir de la cárcel, ya sea tirando los dados o pagando la fianza,
     * dependiendo de sus opciones y recursos disponibles.
     */
    public void salirCarcel() throws Exception {
        Jugador jugActual = jugadores.get(turno);
        Casilla carcel = tablero.encontrar_casilla("Cárcel");

        // Verificar si el jugador está en la cárcel
        if (!jugActual.getEnCarcel()) {
            throw new ExcepcionCarcel("El jugador no está en la cárcel. No puede usar el comando.");
        }

        float fianza = carcel.getImpuesto();
        boolean puedePagarFianza = jugActual.getFortuna() >= fianza;

        // Siempre ofrecer la opción de pagar la fianza si puede tirar dados
        if (jugActual.getTiradasCarcel() < 3) {
            consolaNormal.imprimir("El jugador puede tirar los dados o pagar la fianza (1/2)");
            int respuesta;
            do {
                try {
                    respuesta = consolaNormal.leerInt();
                    if (respuesta == 1 || respuesta == 2) {
                        break;
                    } else {
                        consolaNormal.imprimir("Valor no válido. Pruebe de nuevo");
                    }
                } catch (InputMismatchException e) {
                    consolaNormal.imprimir("Entrada no válida. Por favor ingrese un número.");
                    consolaNormal.leerPalabra();
                }
            } while (true);

            if (respuesta == 1) {
                lanzarDados(jugActual);
            } else if (puedePagarFianza) {
                jugActual.sumarFortuna(-fianza);
                banca.sumarFortuna(fianza);
                jugActual.setEnCarcel(false);
                jugActual.setTiradasCarcel(0);
                consolaNormal.imprimir("El jugador ha pagado la fianza y ha salido de la cárcel. Puede tirar los dados.");
                tirado = false;
            } else {
                consolaNormal.imprimir("El jugador no tiene suficiente dinero para pagar la fianza.");
                conseguirDinero(fianza - jugActual.getFortuna());
                if(jugActual.getTiradasCarcel() < 3) {
                    solvente = true;
                }
            }
        } else if (puedePagarFianza) {
            consolaNormal.imprimir("El jugador puede pagar la fianza para salir de la cárcel.");
            jugActual.sumarFortuna(-fianza);
            banca.sumarFortuna(fianza);
            jugActual.setEnCarcel(false);
            jugActual.setTiradasCarcel(0);
            consolaNormal.imprimir("El jugador ha pagado la fianza y ha salido de la cárcel. Puede tirar los dados.");
            tirado = false;
        } else {
            consolaNormal.imprimir("El jugador no tiene suficiente dinero para pagar la fianza ni puede tirar los dados.");
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
    public void comprar(String nombre) throws Exception {
        if(lanzamientos == 0) {
            throw new Exception("No puedes comprar la casilla en la que estabas, debes lanzar.");
        }
        Jugador comprador = jugadores.get(turno);
        Casilla casillaDeseada = tablero.encontrar_casilla(nombre);
        if(casillaDeseada == null) {
            throw new ExcepcionEntidadNoExistente("casilla");
        }
        if(!(casillaDeseada instanceof Propiedad propiedad)) {
            throw new Exception("Casilla sin opción de compra, no es una propiedad.");
        }

        if(comprador.getFortuna() < propiedad.getValor()) {
            throw new Exception("El jugador no tiene dinero suficiente para comprar la casilla.");
        }
        if(comprador.getAvatar().isAvanzado()) {
            if (compraMovimientoCoche) {
                throw new Exception("El jugador ya ha comprado una propiedad en este turno.");
            } else {
                compraMovimientoCoche = true;
                propiedad.comprarCasilla(comprador, banca);
            }
            return ;
        }

        propiedad.comprarCasilla(comprador, banca);
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
            consolaNormal.imprimirSinSalto("Introduce un número del " + min + " al " + max + ": ");
            try {
                num = consolaNormal.leerInt();
                if (num < min || num > max) {
                    consolaNormal.imprimir("Introduzca un número dentro del rango");
                } else {
                    return num;
                }
            } catch (InputMismatchException e) {
                consolaNormal.imprimir("Entrada inválida, introduzca un número");
                consolaNormal.leerPalabra();
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

    /*******************/
    /*SECCIÓN DE TRATOS*/
    /*******************/


    /**
     * Clasifica el trato entre dos jugadores basado en los objetos ofrecidos.
     *
     * @param jugadorOfertado El nombre del jugador al que se le ofrece el trato.
     * @param objeto1 El primer objeto del trato (puede ser una propiedad o una cantidad de dinero).
     * @param objeto2 El segundo objeto del trato (puede ser una propiedad o una cantidad de dinero).
     * @param objeto3 El tercer objeto del trato (opcional, puede ser una cantidad de dinero).
     * @throws Exception Si el jugador ofertado no existe, si el jugador que ofrece es el mismo que el jugador ofertado,
     *                   o si los objetos del trato no son válidos.
     */
    public void clasificarTrato(String jugadorOfertado, String objeto1, String objeto2, String objeto3) throws Exception {
        Jugador jugadorOfrece = jugadores.get(turno);
        Jugador jugadorRecibe = null;
        for(Jugador jugador : jugadores) {
            if(jugador.getNombre().equals(jugadorOfertado)) {
                jugadorRecibe = jugador;
                break;
            }
        }
        if(jugadorRecibe == null) {
            throw new ExcepcionEntidadNoExistente("jugador");
        }
        if(jugadorOfrece.equals(jugadorRecibe)) {
            throw new Exception("No puedes hacer tratos contigo mismo.");
        }

        if(objeto3 == null) {
            //Orden inverso para que se detecten los números (el dinero) antes de que se detecten como String
            //La expresión regular \\d+\\.\\d+|\\d+ detecta números decimales y enteros y \\S+ detecta cualquier cadena de caracteres
            if (objeto1.matches("\\d+\\.\\d+|\\d+") && objeto2.matches("\\S+")) {
                // Caso 3: cantidad de dinero por propiedad
                creacionTrato(jugadorOfrece, jugadorRecibe, objeto1, objeto2, null, 3);
            } else if (objeto1.matches("\\S+") && objeto2.matches("\\d+\\.\\d+|\\d+")) {
                // Caso 2: propiedad por cantidad de dinero
                creacionTrato(jugadorOfrece, jugadorRecibe, objeto1, objeto2, null, 2);
            } else if (objeto1.matches("\\S+") && objeto2.matches("\\S+")) {
                // Caso 1: propiedad por propiedad
                creacionTrato(jugadorOfrece, jugadorRecibe, objeto1, objeto2, null, 1);
            }
        }
        else {
            if(objeto1.matches("\\S+") && objeto2.matches("\\S+") && objeto3.matches("(\\d+\\.\\d+|\\d+)")) {
                creacionTrato(jugadorOfrece, jugadorRecibe, objeto1, objeto2, objeto3, 4);
            }
            else if(objeto1.matches("\\S+") && objeto2.matches("(\\d+\\.\\d+|\\d+)") && objeto3.matches("\\S+")) {
                creacionTrato(jugadorOfrece, jugadorRecibe, objeto1, objeto2, objeto3, 5);
            }
        }
    }

    /**
     * Crea un trato entre dos jugadores basado en los objetos ofrecidos.
     *
     * @param jugadorOfrece El jugador que ofrece el trato.
     * @param jugadorRecibe El jugador que recibe el trato.
     * @param objeto1 El primer objeto del trato (puede ser una propiedad o una cantidad de dinero).
     * @param objeto2 El segundo objeto del trato (puede ser una propiedad o una cantidad de dinero).
     * @param objeto3 El tercer objeto del trato (opcional, puede ser una cantidad de dinero).
     * @param trato El tipo de trato a crear (1: propiedad por propiedad, 2: propiedad por dinero, 3: dinero por propiedad, 4: propiedad por propiedad y dinero, 5: propiedad y dinero por propiedad).
     * @throws Exception Si ocurre un error durante la creación del trato.
     */
    private void creacionTrato(Jugador jugadorOfrece, Jugador jugadorRecibe, String objeto1, String objeto2, String objeto3, int trato) throws Exception {
        //Hacemos un switch para los diferentes tipos de tratos
        switch (trato) {
            case 1:
                //Obtenemos las propiedades a intercambiar
                Propiedad propiedad1 = obtenerPropiedad(jugadorOfrece, objeto1);
                Propiedad propiedad2 = obtenerPropiedad(jugadorRecibe, objeto2);
                if(propiedad1 == null || propiedad2 == null) {
                    throw new Exception("Una de las propiedades no pertenece a un jugador. No se formaliza el trato.");
                }

                //Creamos el trato y lo añadimos a la lista de tratos del jugador que lo recibe
                Trato tratoCreado = new Trato(jugadorOfrece, jugadorRecibe, propiedad1, propiedad2, contadorTratos);
                jugadorRecibe.addTrato(tratoCreado);
                consolaNormal.imprimir("Trato creado con éxito.");
                //Incrementamos el contador de tratos para formar el ID
                contadorTratos++;
                break;

            case 2:
                Propiedad propiedad3 = obtenerPropiedad(jugadorOfrece, objeto1);
                if(propiedad3 == null) {
                    throw new ExcepcionTratoNoPropiedad("inicia");
                }

                //No comprobamos el dinero ya que en algún momento se puede conseguir
                Trato tratoCreado2 = new Trato(jugadorOfrece, jugadorRecibe, propiedad3, Float.parseFloat(objeto2), contadorTratos);
                jugadorRecibe.addTrato(tratoCreado2);
                consolaNormal.imprimir("Trato creado con éxito.");
                contadorTratos++;
                break;

            case 3:
                //Ídem 2, pero con el dinero en el primer objeto
                Propiedad propiedad4 = obtenerPropiedad(jugadorRecibe, objeto2);
                if(propiedad4 == null) {
                    throw new ExcepcionTratoNoPropiedad("recibe");
                }

                Trato tratoCreado3 = new Trato(jugadorOfrece, jugadorRecibe, Float.parseFloat(objeto1), propiedad4, contadorTratos);
                jugadorRecibe.addTrato(tratoCreado3);
                consolaNormal.imprimir("Trato creado con éxito.");
                contadorTratos++;
                break;

            case 4:
                //Obtenemos las propiedades y comprobamos que se consiguieron correctamente
                Propiedad propiedad5 = obtenerPropiedad(jugadorOfrece, objeto1);
                if(propiedad5 == null) {
                    throw new ExcepcionTratoNoPropiedad("inicia");
                }

                Propiedad propiedad6 = obtenerPropiedad(jugadorRecibe, objeto2);
                if(propiedad6 == null) {
                    throw new ExcepcionTratoNoPropiedad("recibe");
                }

                //Creamos el trato sin comprobar el dinero porque podemos llegar a conseguirlo
                Trato tratoCreado4 = new Trato(jugadorOfrece, jugadorRecibe, propiedad5, propiedad6, Float.parseFloat(objeto3), contadorTratos);
                jugadorRecibe.addTrato(tratoCreado4);
                consolaNormal.imprimir("Trato creado con éxito.");
                contadorTratos++;
                break;

            case 5:
                //Ídem 4 pero cambiando el dinero de persona
                Propiedad propiedad7 = obtenerPropiedad(jugadorOfrece, objeto1);
                if(propiedad7 == null) {
                    throw new ExcepcionTratoNoPropiedad("inicia");
                }

                Propiedad propiedad8 = obtenerPropiedad(jugadorRecibe, objeto3);
                if(propiedad8 == null) {
                    throw new ExcepcionTratoNoPropiedad("recibe");
                }

                Trato tratoCreado5 = new Trato(jugadorOfrece, jugadorRecibe, propiedad7, Float.parseFloat(objeto2), propiedad8, contadorTratos);
                jugadorRecibe.addTrato(tratoCreado5);
                consolaNormal.imprimir("Trato creado con éxito.");
                contadorTratos++;
                break;
        }
    }

    /**
     * Obtiene una propiedad específica de un jugador.
     *
     * @param jugador El jugador que posee la propiedad.
     * @param propiedad El nombre de la propiedad a obtener.
     * @return La propiedad si se encuentra, de lo contrario null.
     */
    private Propiedad obtenerPropiedad(Jugador jugador, String propiedad) {
        for(Propiedad prop : jugador.getPropiedades()) {
            if(prop.getNombre().equals(propiedad)) {
                return prop;
            }
        }
        return null;
    }

    /**
     * Transfiere la propiedad especificada al jugador beneficiado.
     *
     * @param propiedad La propiedad que se va a transferir.
     * @param jugadorBeneficiado El jugador que recibirá la propiedad.
     */
    private void traspasoPropiedad(Propiedad propiedad, Jugador jugadorBeneficiado) {
        Jugador jugadorAnterior = propiedad.getDuenho();
        //Cambiamos el dueño de la propiedad
        propiedad.setDuenho(jugadorBeneficiado);

        //Actualizamos los arrays de propiedades
        jugadorAnterior.getPropiedades().remove(propiedad);
        jugadorBeneficiado.getPropiedades().add(propiedad);

        //Actualizamos las hipotecas
        if(propiedad.isHipotecado()) {
            jugadorAnterior.getHipotecas().remove(propiedad.getNombre());
            jugadorBeneficiado.getHipotecas().add(propiedad.getNombre());
        }

        //Si es un solar, cambiamos el dueño de los edificios
        if(propiedad instanceof Solar solar) {
            for(Edificio edificio : solar.getEdificios()) {
                jugadorAnterior.getEdificios().remove(edificio);
                jugadorBeneficiado.getEdificios().add(edificio);
                edificio.setPropietario(jugadorBeneficiado);
            }
        }
    }

    /**
     * Método que se llama cuando un jugador quiere aceptar un trato.
     *
     * @param trato El trato que se va a aceptar.
     * @throws Exception Si ocurre un error durante la aceptación del trato.
     */
    private void valorarAceptarTrato(Trato trato) throws Exception {
        Jugador jugadorOfrece = trato.getJugadorPropone();
        Jugador jugadorRecibe = trato.getJugadorRecibe();
        Propiedad propiedad1 = trato.getPropiedad1();
        Propiedad propiedad2 = trato.getPropiedad2();
        float dinero = trato.getDinero();

        //Clasificamos según el trato que sea
        switch (trato.getNumTrato()) {
            case 1:
                //Preguntamos, en caso de que estea hipotecada, si quiere aceptarla
                if (aceptarPropiedadHipotecada(propiedad1)) {
                    return;
                }

                //Si consigue la propiedad, se la traspasamos
                traspasoPropiedad(propiedad1, jugadorRecibe);
                traspasoPropiedad(propiedad2, jugadorOfrece);
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido realizado entre " + jugadorOfrece.getNombre() + " y " + jugadorRecibe.getNombre() +
                        ". Se intercambiaron " + propiedad1.getNombre() + " por " + propiedad2.getNombre() + ".");
                //Una vez aceptado, se elimina el trato de la lista de tratos del jugador que lo propuso
                jugadorRecibe.getTratos().remove(trato);
                comprobarTratosRestantes(jugadorRecibe, propiedad2);
                comprobarTratosRestantes(jugadorOfrece, propiedad1);
                break;

            case 2:
                //Comprobamos que en el momento de querer aceptar el trato, tenga dinero suficiente
                if (jugadorRecibe.getFortuna() < dinero) {
                    throw new Exception("No tienes suficiente dinero para aceptar el trato.");
                }

                //Preguntamos, en caso de que estea hipotecada, si quiere aceptarla
                if (aceptarPropiedadHipotecada(propiedad1)) {
                    return;
                }

                //La traspasamos y actualiazamos las fortunas y gastos
                traspasoPropiedad(propiedad1, jugadorRecibe);
                jugadorRecibe.sumarFortuna(-dinero);
                jugadorOfrece.sumarFortuna(dinero);
                jugadorRecibe.sumarGastos(dinero);
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido realizado entre " + jugadorOfrece.getNombre() + " y " + jugadorRecibe.getNombre() +
                        ". Se intercambiaron " + propiedad1.getNombre() + " por " + dinero + "€.");
                jugadorRecibe.getTratos().remove(trato);
                comprobarTratosRestantes(jugadorRecibe, propiedad2);
                comprobarTratosRestantes(jugadorOfrece, propiedad1);
                break;

            case 3:
                //Comprobamos que en el momento de querer aceptar el trato, tenga dinero suficiente
                if (jugadorOfrece.getFortuna() < dinero) {
                    throw new Exception("El jugador que ofreció el trato no tiene el dinero suficiente para formalizarlo actualmente.");
                }

                //Traspasamos la propiedad y actualizamos las fortunas y gastos
                traspasoPropiedad(propiedad1, jugadorOfrece);
                jugadorRecibe.sumarFortuna(dinero);
                jugadorOfrece.sumarFortuna(-dinero);
                jugadorOfrece.sumarGastos(dinero);
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido realizado entre " + jugadorOfrece.getNombre() + " y " + jugadorRecibe.getNombre() +
                        ". Se intercambiaron " + dinero + "€ por " + propiedad1.getNombre() + ".");
                jugadorRecibe.getTratos().remove(trato);
                comprobarTratosRestantes(jugadorRecibe, propiedad2);
                comprobarTratosRestantes(jugadorOfrece, propiedad1);
                break;

            case 4:
                //Preguntamos, en caso de que estea hipotecada, si quiere aceptarla
                if (aceptarPropiedadHipotecada(propiedad1)) {
                    return;
                }
                //Comprobamos que en el momento de querer aceptar el trato, tenga dinero suficiente
                if (jugadorRecibe.getFortuna() < dinero) {
                    throw new Exception("No tienes suficiente dinero para aceptar el trato.");
                }

                //Traspasamos las propiedades y actualizamos las fortunas y gastos
                traspasoPropiedad(propiedad1, jugadorRecibe);
                traspasoPropiedad(propiedad2, jugadorOfrece);
                jugadorOfrece.sumarFortuna(dinero);
                jugadorRecibe.sumarFortuna(-dinero);
                jugadorRecibe.sumarGastos(dinero);
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido realizado entre " + jugadorOfrece.getNombre() + " y " + jugadorRecibe.getNombre() +
                        ". Se intercambiaron " + propiedad1.getNombre() + " por " + propiedad2.getNombre() + " y " + dinero + "€.");
                jugadorRecibe.getTratos().remove(trato);
                comprobarTratosRestantes(jugadorRecibe, propiedad2);
                comprobarTratosRestantes(jugadorOfrece, propiedad1);
                break;

            case 5:
                //Preguntamos, en caso de que estea hipotecada, si quiere aceptarla y luego comprobamos el dinero
                if (aceptarPropiedadHipotecada(propiedad1)) {
                    return;
                }
                if (jugadorOfrece.getFortuna() < dinero) {
                    consolaNormal.imprimir("El jugador que ofreció el trato no tiene el dinero suficiente para formalizarlo actualmente.");
                    return;
                }

                //Traspasamos las propiedades y actualizamos las fortunas y gastos
                traspasoPropiedad(propiedad1, jugadorRecibe);
                traspasoPropiedad(propiedad2, jugadorOfrece);
                jugadorOfrece.sumarFortuna(-dinero);
                jugadorRecibe.sumarFortuna(dinero);
                jugadorOfrece.sumarGastos(dinero);
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido realizado entre " + jugadorOfrece.getNombre() + " y " + jugadorRecibe.getNombre() +
                        ". Se intercambiaron " + propiedad1.getNombre() + " y " + dinero + "€ por " + propiedad2.getNombre() + ".");
                jugadorRecibe.getTratos().remove(trato);
                comprobarTratosRestantes(jugadorRecibe, propiedad2);
                comprobarTratosRestantes(jugadorOfrece, propiedad1);
                break;
        }
    }

    /**
     * Verifica si el jugador desea aceptar una propiedad hipotecada.
     *
     * @param propiedad La propiedad que se va a verificar.
     * @return true si el jugador acepta la propiedad hipotecada, false en caso contrario.
     */
    private boolean aceptarPropiedadHipotecada(Propiedad propiedad) {
        //Si la propiedad está hipotecada, preguntamos si quiere aceptarla
        if (propiedad.isHipotecado()) {
            consolaNormal.imprimir("La propiedad " + propiedad.getNombre() + " está hipotecada, ¿Quieres aceptar el trato igualmente? (s/n)");
            String respuesta = consolaNormal.leerPalabra();
            //Si la respuesta es n, no se acepta el trato, ya que devuelve un True, que entra en un "break"
            return respuesta.equals("n");
        }
        return false;
    }

    /**
     * Método que permite a un jugador aceptar un trato.
     *
     * @param idTrato El identificador del trato que se va a aceptar.
     * @throws Exception Si ocurre un error durante la aceptación del trato.
     */
    public void aceptarTrato(String idTrato) throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Trato tratoDeseado = null;
        //Buscamos el trato que quiere aceptar en la lista de tratos recibidos
        for(Trato trato : jugadorActual.getTratos()) {
            //Si existe, lo igualamos al objeto tratoDeseado y salimos del bucle
            if(idTrato.equals(trato.getIdTrato())) {
                tratoDeseado = trato;
                break;
            }
        }
        if(tratoDeseado == null) {
            throw new ExcepcionEntidadNoExistente("trato");
        }

        valorarAceptarTrato(tratoDeseado);
    }

    /**
     * Método que lista todos los tratos pendientes de los jugadores.
     * Imprime los detalles de cada trato en la consola.
     */
    public void listarTratos() {
        Jugador jugadorActual = jugadores.get(turno);
        StringBuilder str = new StringBuilder();
        str.append("[\n");
        if (jugadorActual.getTratos().isEmpty()) {
            str.append("No hay tratos disponibles\n");
        } else {
            //Para cada trato, se imprime su ID, el jugador que lo propone y los detalles del trato
            for (Trato trato : jugadorActual.getTratos()) {
                str.append("{\n");
                str.append("\tid: ").append(trato.getIdTrato()).append(",\n");
                str.append("\tjugadorPropone: ").append(trato.getJugadorPropone().getNombre()).append(",\n");
                str.append("\ttrato: cambiar (");
                //Los detalles dependen del tipo de trato que sea
                switch (trato.getNumTrato()) {
                    case 1:
                        str.append(trato.getPropiedad1().getNombre()).append(", ").append(trato.getPropiedad2().getNombre());
                        break;
                    case 2:
                        str.append(trato.getPropiedad1().getNombre()).append(", ").append(trato.getDinero()).append("€");
                        break;
                    case 3:
                        str.append(trato.getDinero()).append("€, ").append(trato.getPropiedad1().getNombre());
                        break;
                    case 4:
                        str.append(trato.getPropiedad1().getNombre()).append(" por ").append(trato.getPropiedad2().getNombre()).append(", ").append(trato.getDinero()).append("€");
                        break;
                    case 5:
                        str.append(trato.getPropiedad1().getNombre()).append(", ").append(trato.getDinero()).append("€ por ").append(trato.getPropiedad2().getNombre());
                        break;
                }
                str.append(")\n");
                str.append("},\n");
            }
            if (str.length() > 2) {
                //Eliminar la última coma
                str.setLength(str.length() - 2);
            }
        }
        str.append("\n]");
        consolaNormal.imprimirStrBuilder(str);
    }

    /**
     * Método que permite eliminar un trato específico.
     *
     * @param idTrato El identificador del trato que se va a eliminar.
     * @throws ExcepcionEntidadNoExistente Si el trato no existe.
     */
    public void eliminarTrato(String idTrato) throws ExcepcionEntidadNoExistente {
        Jugador jugadorActual = jugadores.get(turno);
        Trato tratoAEliminar = null;

        //Buscamos el trato a eliminar en la lista de tratos del jugadorS
        for(Trato trato : jugadorActual.getTratos()) {
            if(idTrato.equals(trato.getIdTrato())) {
                tratoAEliminar = trato;
                break;
            }
        }
        if(tratoAEliminar == null) {
            throw new ExcepcionEntidadNoExistente("Trato no encontrado.");
        }

        jugadorActual.getTratos().remove(tratoAEliminar);
        consolaNormal.imprimir("Trato eliminado con éxito.");
    }

    /**
     * Método que comprueba en todos los jugadores si tienen algún trato con la propiedad que ya fue intercambiada.
     *
     * @param jugador El jugador que intercambió la propiedad.
     * @param propiedadIntercambiada La propiedad que fue intercambiada.
     */
    public void comprobarTratosRestantes(Jugador jugador, Propiedad propiedadIntercambiada) {
        for (Jugador j : jugadores) {

            //Creamos una lista de todos los tratos que se van a eliminar
            List<Trato> tratosAEliminar = new ArrayList<>();

            //Recorremos el arraylist de tratos del jugador correspondiente
            for (Trato trato : j.getTratos()) {

                /* Si la propiedad está presente en algúntrato*/
                if ((trato.getPropiedad1().equals(propiedadIntercambiada) && trato.getJugadorPropone().equals(jugador)) ||
                        (trato.getPropiedad2().equals(propiedadIntercambiada) && trato.getJugadorRecibe().equals(jugador))) {
                    tratosAEliminar.add(trato);
                }
            }

            j.getTratos().removeAll(tratosAEliminar);

            for (Trato trato : tratosAEliminar) {
                consolaNormal.imprimir("El trato " + trato.getIdTrato() + " ha sido eliminado ya que " + propiedadIntercambiada.getNombre() + " ya fue intercambiado/a.");
            }
        }
    }

}
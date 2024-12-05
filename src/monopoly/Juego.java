package monopoly;

import monopoly.carta.CartaCajaComunidad;
import monopoly.carta.CartaSuerte;
import monopoly.edificio.*;
import monopoly.casilla.Casilla;
import monopoly.casilla.Impuesto;
import monopoly.casilla.accion.AccionCajaComunidad;
import monopoly.casilla.accion.AccionSuerte;
import monopoly.casilla.propiedad.Propiedad;
import monopoly.casilla.propiedad.Servicio;
import monopoly.casilla.propiedad.Solar;
import monopoly.casilla.propiedad.Transporte;
import monopoly.excepcion.ExcepcionBancarrota;
import monopoly.excepcion.ExcepcionEntidadNoExistente;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import monopoly.excepcion.excepcionCarcel.ExcepcionIrACarcel;
import monopoly.excepcion.excepcionCarcel.ExcepcionJugadorEnCarcel;
import monopoly.excepcion.ExcepcionMovimientosAvanzados;
import monopoly.excepcion.ExcepcionNoHayPropiedadesVenta;
import monopoly.excepcion.excepcionDados.ExcepcionDados;
import monopoly.excepcion.excepcionDados.ExcepcionDadosCoche;
import monopoly.excepcion.excepcionEdificar.ExcepcionEdificar;
import monopoly.excepcion.excepcionEdificar.ExcepcionEdificarNoDinero;
import monopoly.excepcion.excepcionEdificar.ExcepcionEdificarNoEdificable;
import monopoly.excepcion.excepcionEdificar.ExcepcionEdificarNoPropietario;
import monopoly.excepcion.excepcionEntradaUsuario.ExcepcionEntradaUsuario;
import monopoly.excepcion.excepcionEntradaUsuario.ExcepcionFormatoIncorrecto;
import partida.avatar.Avatar;
import partida.Dado;
import partida.Jugador;

import java.util.*;

public class Juego implements Comando{

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
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();

    //Instancias la consola en esta clase

    /**********Constructor**********/
    public Juego() throws ExcepcionEntradaUsuario {
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

    public boolean isDadosDobles() {
        return dadosDobles;
    }

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
    public void preIniciarPartida() throws ExcepcionEntradaUsuario {
        imprimirLogo();

        /*establece el número de jugadores que van a jugar la partida*/
        consolaNormal.imprimir("-----Número de jugadores-----");

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
                consolaNormal.imprimirSinSalto(s.charAt(i));
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    consolaNormal.imprimir("Error en la impresión del logo");
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
                consolaNormal.imprimir("A partir de ahora el avatar " + avatarActual.getId() + ", de tipo " + avatarActual.getTipo() + ", se moverá en modo normal.");
            }
            else {
                avatarActual.setAvanzado(true);
                consolaNormal.imprimir("A partir de ahora el avatar " + avatarActual.getId() + ", de tipo " + avatarActual.getTipo() + ", se moverá en modo avanzado.");
            }
        }
        else {
            throw new ExcepcionMovimientosAvanzados("El jugador debe esperar al inicio del siguiente turno para volver a cambiar el modo de movimiento.");
        }
    }

    /*Método con la lógica del movimiento avanzado Pelota*/
    public void moverJugadorPelota(int valorTirada) throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            throw new ExcepcionJugadorEnCarcel(", no puede avanzar.");
        }

        if (valorTirada > 4) {
            avatarActual.moverAvanzado(tablero.getPosiciones(), 5);
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }

            saltoMovimiento = valorTirada - 5;

        } else {
            avatarActual.moverAvanzado(tablero.getPosiciones(), -1);
            if (jugadorActual.getEnCarcel()) {
                saltoMovimiento = 0;
                return;
            }

            saltoMovimiento = -valorTirada + 1;
        }
    }

    /*Método con la lógica del movimiento avanzado Coche*/
    public void moverJugadorCoche(int valorTirada) throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();

        if (jugadorActual.getEnCarcel()) {
            throw new ExcepcionJugadorEnCarcel(", no puede avanzar.");
        }

        if(valorTirada > 4 && saltoMovimiento > 0) {
            tirado = false;
            avatarActual.moverAvanzado(tablero.getPosiciones(), valorTirada);
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
            avatarActual.moverAvanzado(tablero.getPosiciones(), -valorTirada);
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
    public void avanzar() throws Exception {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        if(saltoMovimiento == 0) {
            throw new ExcepcionMovimientosAvanzados("No hay ningún movimiento pendiente.");
        }
        seHaMovido = true;
        if(saltoMovimiento > 0) {
            if(saltoMovimiento == 1) {
                avatarActual.moverAvanzado(tablero.getPosiciones(), 1);
                saltoMovimiento = 0;
            } else {
                avatarActual.moverAvanzado(tablero.getPosiciones(), 2);
                if(jugadorActual.getEnCarcel()) {
                    saltoMovimiento = 0;
                } else {
                    saltoMovimiento -= 2;
                }
            }
        } else {
            if(saltoMovimiento == -1) {
                avatarActual.moverAvanzado(tablero.getPosiciones(), -1);
                saltoMovimiento = 0;
            }
            else {
                avatarActual.moverAvanzado(tablero.getPosiciones(), -2);
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
                if(casilla instanceof Solar solar || casilla instanceof Transporte || casilla instanceof Servicio
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
            if (saltoMovimiento != 0 && jugadores.get(turno).getAvatar().getTipo().equals("Pelota")) {
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
            if(!avatar.isAvanzado() || avatar.getTipo().equals("Pelota")) {
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
            } else if (avatar.getTipo().equals("Coche")){ //Modo avanzado con coche
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
                if(avatar.getTipo().equals("Pelota")){
                    moverJugadorPelota(valor1 + valor2);
                } else if (avatar.getTipo().equals("Coche")){
                    moverJugadorCoche(valor1 + valor2);
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
            consolaNormal.imprimir("No han salido dobles... El jugador pierde el turno");
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
    public void evaluacion() throws ExcepcionEntidadNoExistente, ExcepcionBancarrota, ExcepcionEdificar {
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
                    //CAMBIAR
                    //barajas.evaluarComunidad(banca, jugadorActual, tablero, jugadores, this);
                    barajas.evaluarCajaComunidad(banca, jugadorActual, tablero, jugadores);
                }
                if(jugadorActual.getEnCarcel()) {
                    dadosDobles = false;
                    acabarTurno();
                }
            }
            default -> consolaNormal.imprimir("Error en la evaluación de casilla");
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
        float factor = 1;
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
    public void ventaEdificio(String tipo, String nombreCasilla, String cantidad) throws ExcepcionEntidadNoExistente {
        int contador = 0;
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if(casilla == null) {
            throw new ExcepcionEntidadNoExistente("casilla");
        }
        if(!(casilla instanceof Solar solar)) {
            consolaNormal.imprimir("La casilla no es un solar, no se pueden vender edificios.");
            return;
        }
        if(solar.getDuenho() != jugadores.get(turno)) {
            consolaNormal.imprimir("No eres propietario de la casilla, no puedes vender edificios.");
            return;
        }
        if(solar.getEdificios().isEmpty()) {
            consolaNormal.imprimir("No hay edificios en la casilla.");
            return;
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
            consolaNormal.imprimir("No hay " + Integer.parseInt(cantidad) + " edificios del tipo " + tipo + " en la casilla.");
            return;
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
    public void acabarTurno() {
        if(!solvente) {
            consolaNormal.imprimir("El jugador no es solvente, no puede acabar turno.");
            return;
        }
        //Tiradas carcel xa axustadas na funcion SaliCarcel
        //if(!tirado || dado1.getValor() == dado2.getValor()) {
        if(jugadores.get(turno).getEnCarcel()) {
            dadosDobles = false;
            tirado = true;
        }
        if(!tirado || dadosDobles) {
            consolaNormal.imprimir("No puedes acabar turno sin haber lanzado los dados.");
            return;
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
    public void hipotecar(String nombreCasilla) {
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        Jugador jugadorActual = jugadores.get(turno);

        if (casilla == null) {
            consolaNormal.imprimir("Casilla no encontrada");
            return;
        }

        if(!(casilla instanceof Propiedad propiedad)) {
            consolaNormal.imprimir("La casilla no es una propiedad");
            return;
        }

        if (!propiedad.getDuenho().equals(jugadorActual)) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
            return;
        }

        if(propiedad instanceof Solar solar && !(solar.getEdificios().isEmpty())) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Tiene edificios construidos");
            return;
        }

        if (propiedad.isHipotecado()) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada");
            return;
        }

        consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " hipoteca " + nombreCasilla + " por " + propiedad.getHipoteca());
        propiedad.setHipotecado(true);
        jugadorActual.sumarFortuna(propiedad.getHipoteca());
        banca.sumarFortuna(-propiedad.getHipoteca());
    }

    /**
     * Método para deshipotecar una casilla.
     * Verifica que la casilla exista, que el jugador sea el propietario, que esté hipotecada y que el jugador tenga suficiente dinero.
     * Si todas las condiciones se cumplen, deshipoteca la casilla y actualiza la fortuna del jugador y de la banca.
     *
     * @param nombreCasilla El nombre de la casilla a deshipotecar.
     */
    public void deshipotecar(String nombreCasilla) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);

        if (casilla == null) {
            consolaNormal.imprimir("Casilla no encontrada");
            return;
        }

        if(!(casilla instanceof Propiedad propiedad)) {
            consolaNormal.imprimir("La casilla no es una propiedad");
            return;
        }

        if (!propiedad.getDuenho().equals(jugadorActual)) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No es una propiedad que le pertenece");
            return;
        }
        if (!propiedad.isHipotecado()) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No está hipotecada");
            return;
        }

        float precioDeshipotecar = propiedad.getHipoteca() * 1.1f;
        if(precioDeshipotecar > jugadorActual.getFortuna()) {
            consolaNormal.imprimir(jugadorActual.getNombre() + " no puede deshipotecar " + nombreCasilla + ". No tiene suficiente dinero");
            return;
        }

        consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " paga " + precioDeshipotecar + " por deshipotecar "
                + nombreCasilla + ". Ahora puede recibir alquileres.");

        jugadorActual.sumarFortuna(-precioDeshipotecar);
        jugadorActual.sumarGastos(precioDeshipotecar);
        banca.sumarFortuna(precioDeshipotecar);
        propiedad.setHipotecado(false);
    }

    private void propiedadesHipotecables(Jugador jugadorActual, ArrayList<Propiedad> propiedades) {
        for(Propiedad propiedad : jugadorActual.getPropiedades()) {
            if(!propiedad.isHipotecado()) {
                switch (propiedad) {
                    case Solar solar when solar.getEdificios().isEmpty() -> propiedades.add(solar);
                    case Servicio servicio -> propiedades.add(servicio);
                    case Transporte transporte -> propiedades.add(transporte);
                    default -> {}
                }
            }
        }
        if(propiedades.isEmpty()) {
            consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " no tiene propiedades hipotecables");
        }
    }

    private void edificiosVender(Jugador jugadorActual, ArrayList<Edificio> edificiosLista) {
        edificiosLista.addAll(jugadorActual.getEdificios());
        if(edificiosLista.isEmpty()) {
            consolaNormal.imprimir("El jugador " + jugadorActual.getNombre() + " no tiene edificios");
        }
    }

    private Propiedad buscarHipotecable(Jugador JugadorActual, String nombrePropiedad) {
        for(Propiedad propiedad : JugadorActual.getPropiedades()) {
            if(propiedad.getNombre().equals(nombrePropiedad) && !propiedad.isHipotecado()) {
                return propiedad;
            }
        }
        consolaNormal.imprimir("Propiedad no válida.");
        return null;
    }

    private Edificio buscarEdificio(Jugador jugadorActual, String idEdificio) {
        for (Edificio edificio : jugadorActual.getEdificios()) {
            if (edificio.getIdEdificio().equals(idEdificio)) {
                return edificio;
            }
        }
        consolaNormal.imprimir("Edificio no válido.");
        return null;
    }

    private void evaluarRecoleccionDinero(Jugador jugadorActual, int contadorPropiedades, int contadorEdificios, float dineroAConseguir, float dineroConseguido) throws ExcepcionEntidadNoExistente, ExcepcionBancarrota, ExcepcionEdificar {
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
                consolaNormal.imprimir("El jugador no ha conseguido dinero suficiente y no le quedan propiedades ni edificios para vender. Se declara en bancarrota.");
                bancarrota(false);
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
    public void conseguirDinero(float dineroAConseguir) throws ExcepcionEntidadNoExistente, ExcepcionBancarrota, ExcepcionEdificar {
        //Declaramos las variables necesarias
        float dineroConseguido = 0;
        int contadorPropiedades = 0;
        int contadorEdificios = 0;
        ArrayList<Propiedad> propiedades = new ArrayList<>();
        ArrayList<Edificio> edificiosLista = new ArrayList<>();
        Jugador jugadorActual = jugadores.get(turno);

        //Comprobamos si el jugador tiene propiedades hipotecables
        propiedadesHipotecables(jugadorActual, propiedades);
        contadorPropiedades = propiedades.size();

        //Informamos de los edificios que tiene el jugador
        edificiosVender(jugadorActual, edificiosLista);
        contadorEdificios = edificiosLista.size();

        consolaNormal.imprimir("El jugador no tiene suficiente dinero para pagar. Debe vender edificios y/o hipotecar propiedades.");

        if (jugadorActual.getEdificios().isEmpty() && contadorPropiedades == 0) {
            throw new ExcepcionBancarrota("El jugador no tiene propiedades ni edificios para vender. Se declara en bancarrota.");
        } else {
            consolaNormal.imprimir("El jugador tiene propiedades y/o edificios. ¿Qué desea hipotecar/vender? (propiedades[1]/edificios[2]) ");
            int opcion;
            do {
                opcion = consolaNormal.leerInt();
                if (opcion != 1 && opcion != 2) {
                    throw new ExcepcionEdificar("Opción no válida. Introduzca 1 para propiedades o 2 para edificios.");
                }
            } while (opcion != 1 && opcion != 2);
            if (opcion == 1) { //Hipotecar propiedades
                consolaNormal.imprimir("Propiedades hipotecables:");
                for(Propiedad propiedad : propiedades) {
                    consolaNormal.imprimir(propiedad.getNombre());
                }
                if(contadorPropiedades != 0) {
                    dineroConseguido = 0;
                    while((dineroConseguido < dineroAConseguir)) {
                        if(contadorPropiedades == 0) {
                            break;
                        }

                        Propiedad propiedadHipotecar = null;

                        while(propiedadHipotecar == null) {
                            consolaNormal.imprimir("Introduce el nombre de la propiedad que quieres hipotecar:");
                            String nombrePropiedad = consolaNormal.leerPalabra();
                            //Pasamos el jugAct para el array de props y el nombre de la propiedad.
                            propiedadHipotecar = buscarHipotecable(jugadorActual, nombrePropiedad);
                        }

                        contadorPropiedades--;
                        dineroConseguido += propiedadHipotecar.getHipoteca();
                        hipotecar(propiedadHipotecar.getNombre());
                    }
                } else {
                    throw new ExcepcionEdificar("El jugador no tiene propiedades sin edificar. Debe vender los edificios antes de hipotecar una propiedad.");
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
                        float valor = 0;
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
                                propiedades.add((Propiedad) solar);
                                contadorPropiedades++;
                            }
                        }
                    }
                } else {
                    throw new ExcepcionEdificar("El jugador no tiene edificios para vender.");
                }
            }
            evaluarRecoleccionDinero(jugadorActual, contadorPropiedades, contadorEdificios, dineroAConseguir, dineroConseguido);
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
        jugActual = null;
        avActual = null;
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
            consolaNormal.imprimir("El jugador no está en la cárcel. No puede usar el comando.");
            return;
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
            consolaNormal.imprimir("El jugador puede pagar la fianza para salir de la cárcel.");
            jugActual.sumarFortuna(-fianza);
            banca.sumarFortuna(fianza);
            jugActual.setEnCarcel(false);
            jugActual.setTiradasCarcel(0);
            consolaNormal.imprimir("El jugador ha pagado la fianza y ha salido de la cárcel. Puede tirar los dados.");
            tirado = false;
        } else {
            consolaNormal.imprimir("El jugador no tiene suficiente dinero para pagar la fianza ni puede tirar los dados.");
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




}
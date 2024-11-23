package monopoly;

import partida.Avatar;
import partida.Dado;
import partida.Jugador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static monopoly.Valor.FORTUNA_INICIAL;

public class Juego {

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

        //preIniciarPartida();
    }

    /**********Métodos**********/

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
                    if(jugadores.get(turno).getNoPuedeTirarDados() > 0) {
                        System.out.println("No puedes lanzar los dados en este turno.");
                        jugadores.get(turno).setNoPuedeTirarDados(jugadores.get(turno).getNoPuedeTirarDados() - 1);
                        tirado = true;
                        acabarTurno();
                        break;
                    }
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("dados")) {
                        lanzarDados(0, 0);
                        if(jugadores.get(turno).getAvatar().getConseguirDinero()) {
                            conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                        }
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();

                    } else if (palabrasArray.length == 4 && palabrasArray[1].equals("dados")) { //Dados trucados
                        lanzarDados(Integer.parseInt(palabrasArray[2]), Integer.parseInt(palabrasArray[3]));
                        if(jugadores.get(turno).getAvatar().getConseguirDinero()) {
                            conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                        }
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();


                    } else {
                        System.out.println("El formato correcto es: lanzar dados o lanzar dados (núm primer dado) (núm segundo dado)");
                    }
                    if(jugadores.get(turno).getEnCarcel()) {
                        dadosDobles = false;
                    }
                    break;

                case "avanzar":
                    avanzar();
                    System.out.println(tablero.toString());
                    evaluacion();
                    VueltasTablero();
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
                        evaluacion();
                        VueltasTablero();
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

                case "hipotecar":
                    if (palabrasArray.length == 2) {
                        hipotecar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: hipotecar nombrePropiedad");
                    }
                    break;

                case "deshipotecar":
                    if (palabrasArray.length == 2) {
                        deshipotecar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: deshipotecar nombrePropiedad");
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
                    if (palabrasArray.length == 1) {
                        MoverAux();
                        System.out.println(tablero.toString());
                        evaluacion();
                        VueltasTablero();
                    } else {
                        System.out.println("El formato correcto es: moveraux");
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
                        modoAvanzado();
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

                case "bancarrota":
                    bancarrota(true);
                    break;
                default:
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }

    /*******************************/
    /*SECCIÓN MOVIMIENTOS AVANZADOS*/
    /*******************************/

    /*Método para activar el modo avanzado del jugador*/
    private void modoAvanzado() {
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
    private void avanzar() {
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

    /*******************/
    /*SECCIÓN DE LISTAR*/
    /*******************/

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

    /* Método que realiza las acciones asociadas al comando 'listar jugadores'*/
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

    /* Método que realiza las acciones asociadas al comando 'listar avatares' */
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

    /*************************/
    /*SECCIÓN DE ESTADÍSTICAS*/
    /*************************/

    /**
     * Método que imprime las estadísticas del juego.
     */
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

    /**
     * Método que imprime los nombres de los jugadores.
     * Si la lista de jugadores está vacía, imprime un guion ("-").
     *
     * @param jugadores La lista de jugadores cuyos nombres se van a imprimir.
     */
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

    /**
     * Método que imprime los nombres de las casillas.
     * Si la lista de casillas está vacía, imprime un guion ("-").
     *
     * @param casillas La lista de casillas cuyos nombres se van a imprimir.
     */
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

    /**
     * Método que imprime los nombres de los grupos.
     * Si la lista de grupos está vacía, imprime un guion ("-").
     *
     * @param grupos La lista de grupos cuyos nombres se van a imprimir.
     */
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

        // Recorre la lista de jugadores para encontrar el número máximo de vueltas
        for (Jugador jugador : jugadores) {
            if (jugador.getVueltas() > maxVueltas) {
                // Si el jugador actual tiene más vueltas que el máximo actual, actualiza el máximo y reinicia la lista
                maxVueltas = jugador.getVueltas();
                jugadoresMasVueltas.clear();
                jugadoresMasVueltas.add(jugador);
            } else if (jugador.getVueltas() == maxVueltas) {
                // Si el jugador actual tiene el mismo número de vueltas que el máximo, añádelo a la lista
                jugadoresMasVueltas.add(jugador);
            }
        }

        return jugadoresMasVueltas;
    }

    /**
     * Método que devuelve una lista de los jugadores con más tiradas de dados.
     *
     * @return Una lista de jugadores con el mayor número de tiradas de dados.
     */
    private ArrayList<Jugador> jugadoresConMasTiradasDados() {
        ArrayList<Jugador> jugadoresMasTiradasDados = new ArrayList<>();
        int maxTiradasDados = 0;

        // Recorre la lista de jugadores para encontrar el número máximo de tiradas de dados
        for (Jugador jugador : jugadores) {
            if (jugador.getVecesTiradasDados() > maxTiradasDados) {
                // Si el jugador actual tiene más tiradas que el máximo actual, actualiza el máximo y reinicia la lista
                maxTiradasDados = jugador.getVecesTiradasDados();
                jugadoresMasTiradasDados.clear();
                jugadoresMasTiradasDados.add(jugador);
            } else if (jugador.getVecesTiradasDados() == maxTiradasDados) {
                // Si el jugador actual tiene el mismo número de tiradas que el máximo, añádelo a la lista
                jugadoresMasTiradasDados.add(jugador);
            }
        }

        return jugadoresMasTiradasDados;
    }

    /**
     * Método que devuelve una lista de los jugadores en cabeza.
     *
     * @return Una lista de jugadores con la mayor fortuna total.
     */
    private ArrayList<Jugador> jugadoresEnCabeza() {
        ArrayList<Jugador> jugadoresEnCabeza = new ArrayList<>();
        float maxFortunaTotal = 0;

        // Recorre la lista de jugadores para encontrar la mayor fortuna total
        for (Jugador jugador : jugadores) {
            if (jugador.calcularFortunaTotal() > maxFortunaTotal) {
                // Si el jugador actual tiene más fortuna que el máximo actual, actualiza el máximo y reinicia la lista
                maxFortunaTotal = jugador.calcularFortunaTotal();
                jugadoresEnCabeza.clear();
                jugadoresEnCabeza.add(jugador);
            } else if (jugador.calcularFortunaTotal() == maxFortunaTotal) {
                // Si el jugador actual tiene la misma fortuna que el máximo, añádelo a la lista
                jugadoresEnCabeza.add(jugador);
            }
        }

        return jugadoresEnCabeza;
    }

    /**
     * Método que devuelve una lista de las casillas más rentables.
     *
     * @return Una lista de casillas con el mayor total de alquileres pagados.
     */
    private ArrayList<Casilla> casillasMasRentables() {
        ArrayList<Casilla> casillasMasRentables = new ArrayList<>();
        float maxAlquileresPagados = 0;

        // Recorre el tablero para encontrar la casilla con el mayor total de alquileres pagados
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                if (casilla.getTotalAlquileresPagados() > maxAlquileresPagados) {
                    // Si la casilla actual tiene más alquileres pagados que el máximo actual, actualiza el máximo y reinicia la lista
                    maxAlquileresPagados = casilla.getTotalAlquileresPagados();
                    casillasMasRentables.clear();
                    casillasMasRentables.add(casilla);
                } else if (casilla.getTotalAlquileresPagados() == maxAlquileresPagados) {
                    // Si la casilla actual tiene el mismo total de alquileres pagados que el máximo, añádela a la lista
                    casillasMasRentables.add(casilla);
                }
            }
        }

        // Si todas las casillas tienen el mismo total de alquileres pagados, devuelve una lista vacía
        if (casillasMasRentables.size() == 40) {
            return new ArrayList<>();
        }
        return casillasMasRentables;
    }

    /**
     * Método que calcula los grupos más rentables.
     *
     * @return Una lista de los grupos más rentables.
     */
    private ArrayList<Grupo> calcularGruposMasRentables() {
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

    /**
     * Método que devuelve una lista de las casillas más frecuentadas.
     *
     * @return Una lista de casillas con el mayor número de veces frecuentada.
     */
    private ArrayList<Casilla> casillasMasFrecuentadas() {
        ArrayList<Casilla> casillasMasFrecuentadas = new ArrayList<>();
        float maxTotalVecesFrecuentada = 0;

        // Recorre el tablero para encontrar la casilla con el mayor número de veces frecuentada
        for (ArrayList<Casilla> fila : tablero.getPosiciones()) {
            for (Casilla casilla : fila) {
                if (casilla.getTotalVecesFrecuentada() > maxTotalVecesFrecuentada) {
                    // Si la casilla actual ha sido frecuentada más veces que el máximo actual, actualiza el máximo y reinicia la lista
                    maxTotalVecesFrecuentada = casilla.getTotalVecesFrecuentada();
                    casillasMasFrecuentadas.clear();
                    casillasMasFrecuentadas.add(casilla);
                } else if (casilla.getTotalVecesFrecuentada() == maxTotalVecesFrecuentada) {
                    // Si la casilla actual ha sido frecuentada el mismo número de veces que el máximo, añádela a la lista
                    casillasMasFrecuentadas.add(casilla);
                }
            }
        }

        // Si todas las casillas han sido frecuentadas el mismo número de veces, devuelve una lista vacía
        if (casillasMasFrecuentadas.size() == 40) {
            return new ArrayList<>();
        }
        return casillasMasFrecuentadas;
    }

}

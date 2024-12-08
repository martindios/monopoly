package monopoly;

import monopoly.excepcion.*;
import monopoly.excepcion.excepcionCarcel.*;
import monopoly.excepcion.excepcionConseguirDinero.*;
import monopoly.excepcion.excepcionDados.*;
import monopoly.excepcion.excepcionEntradaUsuario.*;
import partida.Jugador;
import partida.avatar.Coche;
import partida.avatar.Pelota;

import java.util.ArrayList;

import static monopoly.Valor.FORTUNA_INICIAL;

// Analizar comando con el while, los comandos se implementan en el juego, también se manejan las excepciones
//El bucle lo tiene el menu

public class Menu {

    /**********Atributos**********/
    Juego juego;
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();

    /**********Constructor**********/
    public Menu() throws Exception {
        try{
            this.juego = new Juego();
        } catch (Exception e) {
            if(e instanceof ExcepcionEntradaUsuario) {
                consolaNormal.imprimir(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        iniciarPartida();
    }

    /**********Métodos**********/
    /*Método en el que se desarrolla la partida hasta que un jugador es no solvente*/
    public void iniciarPartida() throws Exception {
        while(!juego.isFinalizarPartida()) {
            Juego.setSeHaMovido(false);
            consolaNormal.imprimirSinSalto("Introduce el comando: ");
            String comando = consolaNormal.leer();
            analizarComando(comando);
        }
        consolaNormal.imprimir(juego.getTablero().toString());
        consolaNormal.imprimir("Partida finalizada. El jugador ha caído en una casilla y no es solvente.");
        System.exit(0);
    }

    /*Método que interpreta el comando introducido y toma la accion correspondiente
     * Parámetro: cadena de caracteres (el comando)
     */
    public void analizarComando(String comando) throws Exception {
        ArrayList<Jugador> jugadores = juego.getJugadores();
        int turno = juego.getTurno();
        Tablero tablero = juego.getTablero();

        String[] palabrasArray = comando.split(" ");
        try {
            if (palabrasArray.length > 0) {
                switch (palabrasArray[0]) {
                    case "crear":
                        throw new ExcepcionJugadoresYaRegistrados("Todos los jugadores están registrados");

                    case "jugador":
                        consolaNormal.imprimir("Tiene el turno: " + (jugadores.get(turno)).getNombre());
                        break;

                    case "lanzar":
                        if(Juego.isTirado()) {
                            throw new ExcepcionEntradaUsuario("Ya has lanzado los dados este turno.");
                        }

                        if (jugadores.get(turno).getNoPuedeTirarDados() > 0) {
                            throw new ExcepcionNoPuedeTirarDados("No puedes lanzar los dados en este turno.");
                        }
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("dados")) {
                            juego.lanzarDados(0, 0);
                            if (jugadores.get(turno).getAvatar().getConseguirDinero()) {
                                juego.conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                            }
                            consolaNormal.imprimir(tablero.toString());
                            juego.evaluacion();
                            juego.VueltasTablero();

                        } else if (palabrasArray.length == 4 && palabrasArray[1].equals("dados")) { //Dados trucados
                            juego.lanzarDados(Integer.parseInt(palabrasArray[2]), Integer.parseInt(palabrasArray[3]));
                            if (jugadores.get(turno).getAvatar().getConseguirDinero()) {
                                juego.conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                            }
                            consolaNormal.imprimir(tablero.toString());
                            juego.evaluacion();
                            juego.VueltasTablero();

                        } else {
                            throw new ExcepcionFormatoIncorrecto("lanzar dados o lanzar dados (núm primer dado) (núm segundo dado)");
                        }
                        if (jugadores.get(turno).getEnCarcel()) {
                            Juego.setDadosDobles(false);
                        }
                        break;

                    case "avanzar":
                        Jugador jugadorActual = jugadores.get(turno);
                        if(jugadorActual.getAvatar() instanceof Pelota pelota) {
                            pelota.avanzar(jugadorActual, tablero);
                        }
                        consolaNormal.imprimir(tablero.toString());
                        juego.evaluacion();
                        juego.VueltasTablero();
                        break;

                    case "acabar":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("turno")) {
                            juego.acabarTurno();
                            break;
                        } else {
                            throw new ExcepcionFormatoIncorrecto("acabar turno");
                        }

                    case "listar":
                        if (palabrasArray.length == 2) {
                            switch (palabrasArray[1]) {
                                case "jugadores":
                                    juego.listarJugadores();
                                    break;
                                case "avatares":
                                    juego.listarAvatares();
                                    break;
                                case "enventa":
                                    juego.listarVenta();
                                    break;
                                case "edificios":
                                    juego.listarEdificios();
                                    break;
                                case "tratos":
                                    juego.listarTratos();
                                    break;
                                default:
                                    throw new ExcepcionFormatoIncorrecto("listar [jugadores, avatares, enventa, edificios]");
                            }
                        } else if (palabrasArray.length == 3) {
                            if (palabrasArray[1].equals("edificios")) {
                                juego.listarEdificiosGrupo(palabrasArray[2]);
                            } else {
                                throw new ExcepcionFormatoIncorrecto("listar edificios [colorGrupo]");
                            }
                        } else {
                            throw new ExcepcionFormatoIncorrecto("listar [jugadores, avatares, enventa, edificios]");
                        }
                        break;
                    case "salir":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("carcel")) {
                            juego.salirCarcel();
                            juego.evaluacion();
                            juego.VueltasTablero();
                        } else {
                            throw new ExcepcionFormatoIncorrecto("salir carcel");
                        }
                        break;

                    case "describir":
                        if (palabrasArray.length == 3) {
                            switch (palabrasArray[1]) {
                                case "jugador":
                                    juego.descJugador(palabrasArray[2]);
                                    break;
                                case "avatar":
                                    juego.descAvatar(palabrasArray[2]);
                                    break;
                                default:
                                    throw new ExcepcionFormatoIncorrecto("describir [jugador, avatar] nombre/ID");
                            }
                        } else {
                            juego.descCasilla(palabrasArray[1]);
                        }
                        break;

                    case "comprar":
                        if (palabrasArray.length == 2) {
                            juego.comprar(palabrasArray[1]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("comprar nombrePropiedad");
                        }
                        break;

                    case "hipotecar":
                        if (palabrasArray.length == 2) {
                            juego.hipotecar(palabrasArray[1]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("hipotecar nombrePropiedad");
                        }
                        break;

                    case "deshipotecar":
                        if (palabrasArray.length == 2) {
                            juego.deshipotecar(palabrasArray[1]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("deshipotecar nombrePropiedad");
                        }
                        break;

                    case "ver":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("tablero")) {
                            consolaNormal.imprimir(tablero.toString());
                        } else {
                            throw new ExcepcionFormatoIncorrecto("ver tablero");
                        }
                        break;

                    case "edificar":
                        if (palabrasArray.length == 2) {
                            juego.edificar(palabrasArray[1]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("edificar [Casa, Hotel, Piscina, PistaDeporte]");
                        }
                        break;

                    case "cambiar":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("modo")) {
                            juego.modoAvanzado();
                        } else {
                            throw new ExcepcionFormatoIncorrecto("cambiar modo");
                        }
                        break;

                    case "vender":
                        if (palabrasArray.length == 4) {
                            juego.ventaEdificio(palabrasArray[1], palabrasArray[2], palabrasArray[3]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("vender tipoEdificio solar cantidad");
                        }
                        break;

                    case "estadisticas":
                        if (palabrasArray.length == 1) {
                            juego.estadisticas();
                        } else if (palabrasArray.length == 2) {
                            juego.estadisticasJugador(palabrasArray[1]);
                        } else {
                            throw new ExcepcionFormatoIncorrecto("estadisticas. O también estadisticas nombreJugador");
                        }
                        break;

                    case "bancarrota":
                        juego.bancarrota(true);
                        break;

                    case "trato":
                        if(palabrasArray.length == 5 && palabrasArray[3].equals("por")) {
                            juego.clasificarTrato(palabrasArray[1], palabrasArray[2], palabrasArray[4], null);
                        }
                        else if(palabrasArray.length == 6) {
                            if(palabrasArray[3].equals("por")) {
                                juego.clasificarTrato(palabrasArray[1], palabrasArray[2], palabrasArray[4], palabrasArray[5]);
                            }
                            else if(palabrasArray[4].equals("por")) {
                                juego.clasificarTrato(palabrasArray[1], palabrasArray[2], palabrasArray[3], palabrasArray[5]);
                            }
                            else {
                                throw new ExcepcionFormatoIncorrecto("trato jugadorOfertado X por Y // trato jugadorOfertado X Y por Z // trato jugadorOfertado X por Y Z");
                            }
                        }
                        else {
                            throw new ExcepcionFormatoIncorrecto("trato jugadorOfertado X por Y // trato jugadorOfertado X Y por Z // trato jugadorOfertado X por Y Z");
                        }
                        break;

                    case "aceptar":
                        if(palabrasArray.length == 2) {
                            juego.aceptarTrato(palabrasArray[1]);
                        }
                        else {
                            throw new ExcepcionFormatoIncorrecto("aceptar idTrato");
                        }
                        break;

                    case "eliminar":
                        if(palabrasArray.length == 2) {
                            juego.eliminarTrato(palabrasArray[1]);
                        }
                        else {
                            throw new ExcepcionFormatoIncorrecto("eliminar idTrato");
                        }
                        break;

                    default:
                        throw new ExcepcionEntradaUsuario("Comando no válido");
                }
            }
        } catch (Exception e) {
            consolaNormal.imprimir(e.getMessage());
            switch (e) {
                case ExcepcionNoPuedeTirarDados _ -> {
                    jugadores.get(turno).setNoPuedeTirarDados(jugadores.get(turno).getNoPuedeTirarDados() - 1);
                    Juego.setTirado(true);
                    juego.acabarTurno();
                }
                case ExcepcionDadosCoche _ -> Juego.setDadosDobles(false);
                case ExcepcionIrACarcel _ -> {
                    juego.getJugadores().get(turno).encarcelar(tablero.getPosiciones());
                    Juego.setDadosDobles(false);
                    juego.acabarTurno();
                }
                case ExcepcionBancarrota _ -> juego.bancarrota(false);
                case ExcepcionCarcelTirarDados _ -> {
                    Juego.setDadosDobles(false);
                    jugadores.get(turno).setTiradasCarcel(jugadores.get(turno).getTiradasCarcel() + 1);
                    juego.acabarTurno();
                }
                case ExcepcionConseguirDinero _ ->
                        jugadores.get(turno).getAvatar().setConseguirDinero(true);
                case ExcepcionConseguirDineroCaja excepcionConseguirDineroCaja ->
                        juego.conseguirDinero(excepcionConseguirDineroCaja.getRestante());
                default -> {
                }
            }
        }
    }
}

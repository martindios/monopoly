package monopoly;

import monopoly.excepcion.DadosYaLanzadosExcepcion;
import monopoly.excepcion.ExcepcionEntradaUsuario;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Scanner;

import static monopoly.Valor.FORTUNA_INICIAL;

// Analizar comando con el while, los comandos se implementan en el juego, también se manejan las excepciones
//El bucle lo tiene el menu

public class Menu {
    Juego juego;
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();

    public Menu() {
        this.juego = new Juego();
        iniciarPartida();
    }

    /*Método en el que se desarrolla la partida hasta que un jugador es no solvente*/
    public void iniciarPartida() {
        //SE DEBERÍA DE QUITAR ESTE IF
        if (juego.getJugadores().get(juego.getTurno()).getAvatar().getTipo().equals("Coche")) {
            juego.setSaltoMovimiento(4);
        }
        while(!juego.isFinalizarPartida()) {
            juego.setSeHaMovido(false);
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
    public void analizarComando(String comando) {
        ArrayList<Jugador> jugadores = juego.getJugadores();
        int turno = juego.getTurno();
        Tablero tablero = juego.getTablero();

        String[] palabrasArray = comando.split(" ");
        try {
            if (palabrasArray.length > 0) {
                switch (palabrasArray[0]) {
                    case "crear":
                        consolaNormal.imprimir("Todos los jugadores están registrados");
                        break;

                    case "jugador":
                        consolaNormal.imprimir("Tiene el turno: " + (jugadores.get(turno)).getNombre());
                        break;

                    case "lanzar":
                        if(juego.isTirado()) {
                            throw new ExcepcionEntradaUsuario("Ya has lanzado los dados este turno.");
                        }
                        /*
                        try {
                            if (juego.isTirado()) {
                                throw new DadosYaLanzadosExcepcion("Ya has lanzado los dados este turno.");
                            }
                        } catch (DadosYaLanzadosExcepcion e) {
                            consolaNormal.imprimir(e.getMessage());
                            break;
                        } */


                        if (jugadores.get(turno).getNoPuedeTirarDados() > 0) {
                            consolaNormal.imprimir("No puedes lanzar los dados en este turno.");
                            jugadores.get(turno).setNoPuedeTirarDados(jugadores.get(turno).getNoPuedeTirarDados() - 1);
                            juego.setTirado(true);
                            juego.acabarTurno();
                            break;
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
                            consolaNormal.imprimir("El formato correcto es: lanzar dados o lanzar dados (núm primer dado) (núm segundo dado)");
                        }
                        if (jugadores.get(turno).getEnCarcel()) {
                            juego.setDadosDobles(false);
                        }
                        break;

                    case "avanzar":
                        juego.avanzar();
                        consolaNormal.imprimir(tablero.toString());
                        juego.evaluacion();
                        juego.VueltasTablero();
                        break;

                    case "acabar":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("turno")) {
                            juego.acabarTurno();
                            break;
                        } else {
                            consolaNormal.imprimir("El formato correcto es: acabar turno");
                            break;
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
                                default:
                                    consolaNormal.imprimir("Comando no válido");
                                    break;
                            }
                        } else if (palabrasArray.length == 3) {
                            if (palabrasArray[1].equals("edificios")) {
                                juego.listarEdificiosGrupo(palabrasArray[2]);
                            } else {
                                consolaNormal.imprimir("El formato correcto es: listar edificios [colorGrupo]");
                            }
                        } else {
                            consolaNormal.imprimir("El formato correcto es: listar [jugadores, avatares, enventa, edificios]");
                            break;
                        }
                        break;
                    case "salir":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("carcel")) {
                            juego.salirCarcel();
                            juego.evaluacion();
                            juego.VueltasTablero();
                        } else {
                            consolaNormal.imprimir("Comando no válido.");
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
                                    consolaNormal.imprimir("Comando no válido");
                                    break;
                            }
                        } else {
                            juego.descCasilla(palabrasArray[1]);
                        }
                        break;

                    case "comprar":
                        if (palabrasArray.length == 2) {
                            juego.comprar(palabrasArray[1]);
                        } else {
                            consolaNormal.imprimir("El formato correcto es: comprar nombrePropiedad");
                        }
                        break;

                    case "hipotecar":
                        if (palabrasArray.length == 2) {
                            juego.hipotecar(palabrasArray[1]);
                        } else {
                            consolaNormal.imprimir("El formato correcto es: hipotecar nombrePropiedad");
                        }
                        break;

                    case "deshipotecar":
                        if (palabrasArray.length == 2) {
                            juego.deshipotecar(palabrasArray[1]);
                        } else {
                            consolaNormal.imprimir("El formato correcto es: deshipotecar nombrePropiedad");
                        }
                        break;

                    case "ver":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("tablero")) {
                            consolaNormal.imprimir(tablero.toString());
                        } else {
                            consolaNormal.imprimir("Comando no válido");
                        }
                        break;

                    case "edificar":
                        if (palabrasArray.length == 2) {
                            juego.edificar(palabrasArray[1]);
                        } else {
                            consolaNormal.imprimir("El formato correcto es: edificar [Casa, Hotel, Piscina, PistaDeporte]");
                        }
                        break;

                    case "cambiar":
                        if (palabrasArray.length == 2 && palabrasArray[1].equals("modo")) {
                            juego.modoAvanzado();
                        } else {
                            consolaNormal.imprimir("El formato correcto es: cambiar modo");
                        }
                        break;

                    case "vender":
                        if (palabrasArray.length == 4) {
                            juego.ventaEdificio(palabrasArray[1], palabrasArray[2], palabrasArray[3]);
                        }
                        break;

                    case "estadisticas":
                        if (palabrasArray.length == 1) {
                            juego.estadisticas();
                        } else if (palabrasArray.length == 2) {
                            juego.estadisticasJugador(palabrasArray[1]);
                        }
                        break;

                    case "bancarrota":
                        juego.bancarrota(true);
                        break;
                    default:
                        consolaNormal.imprimir("Comando no válido");
                        break;
                }
            }
        } catch (ExcepcionEntradaUsuario e) {
            consolaNormal.imprimir(e.getMessage());
        }
    }




}

package monopoly;

import partida.Jugador;

import java.util.ArrayList;
import java.util.Scanner;

import static monopoly.Valor.FORTUNA_INICIAL;

// Analizar comando con el while, los comandos se implementan en el juego, también se manejan las excepciones
//El bucle lo tiene el menu

public class Menu {
    Juego juego;
    static Scanner scanner = new Scanner(System.in); //scanner para leer lo que se pone por teclado

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
            System.out.print("Introduce el comando: ");
            String comando = scanner.nextLine();
            analizarComando(comando);
        }
        System.out.println(juego.getTablero().toString());
        System.out.println("Partida finalizada. El jugador ha caído en una casilla y no es solvente.");
        scanner.close();
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
        if(palabrasArray.length > 0) {
            switch(palabrasArray[0]) {
                case "crear":
                    System.out.println("Todos los jugadores están registrados");
                    break;

                case "jugador":
                    System.out.println("Tiene el turno: " + (jugadores.get(turno)).getNombre());
                    break;

                case "lanzar":
                    if(juego.isTirado()){
                        System.out.println("Ya has lanzado los dados en este turno.");
                        break;
                    }
                    if(jugadores.get(turno).getNoPuedeTirarDados() > 0) {
                        System.out.println("No puedes lanzar los dados en este turno.");
                        jugadores.get(turno).setNoPuedeTirarDados(jugadores.get(turno).getNoPuedeTirarDados() - 1);
                        juego.setTirado(true);
                        juego.acabarTurno();
                        break;
                    }
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("dados")) {
                        juego.lanzarDados(0, 0);
                        if(jugadores.get(turno).getAvatar().getConseguirDinero()) {
                            juego.conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                        }
                        System.out.println(tablero.toString());
                        juego.evaluacion();
                        juego.VueltasTablero();

                    } else if (palabrasArray.length == 4 && palabrasArray[1].equals("dados")) { //Dados trucados
                        juego.lanzarDados(Integer.parseInt(palabrasArray[2]), Integer.parseInt(palabrasArray[3]));
                        if(jugadores.get(turno).getAvatar().getConseguirDinero()) {
                            juego.conseguirDinero(FORTUNA_INICIAL - jugadores.get(turno).getFortuna());
                        }
                        System.out.println(tablero.toString());
                        juego.evaluacion();
                        juego.VueltasTablero();


                    } else {
                        System.out.println("El formato correcto es: lanzar dados o lanzar dados (núm primer dado) (núm segundo dado)");
                    }
                    if(jugadores.get(turno).getEnCarcel()) {
                        juego.setDadosDobles(false);
                    }
                    break;

                case "avanzar":
                    juego.avanzar();
                    System.out.println(tablero.toString());
                    juego.evaluacion();
                    juego.VueltasTablero();
                    break;

                case "acabar":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("turno")) {
                        juego.acabarTurno();
                        break;
                    } else {
                        System.out.println("El formato correcto es: acabar turno");
                        break;
                    }

                case "listar":
                    if (palabrasArray.length == 2) {
                        switch (palabrasArray[1]){
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
                                System.out.println("Comando no válido");
                                break;
                        }
                    } else if(palabrasArray.length == 3) {
                        if(palabrasArray[1].equals("edificios")) {
                            juego.listarEdificiosGrupo(palabrasArray[2]);
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
                        juego.salirCarcel();
                        juego.evaluacion();
                        juego.VueltasTablero();
                    } else {
                        System.out.println("Comando no válido.");
                    }
                    break;

                case "describir":
                    if(palabrasArray.length == 3) {
                        switch (palabrasArray[1]) {
                            case "jugador":
                                juego.descJugador(palabrasArray[2]);
                                break;
                            case "avatar":
                                juego.descAvatar(palabrasArray[2]);
                                break;
                            default:
                                System.out.println("Comando no válido");
                                break;
                        }
                    }
                    else {
                        juego.descCasilla(palabrasArray[1]);
                    }
                    break;

                case "comprar":
                    if (palabrasArray.length == 2) {
                        juego.comprar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: comprar nombrePropiedad");
                    }
                    break;

                case "hipotecar":
                    if (palabrasArray.length == 2) {
                        juego.hipotecar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: hipotecar nombrePropiedad");
                    }
                    break;

                case "deshipotecar":
                    if (palabrasArray.length == 2) {
                        juego.deshipotecar(palabrasArray[1]);
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

                case "edificar":
                    if (palabrasArray.length == 2) {
                        juego.edificar(palabrasArray[1]);
                    } else {
                        System.out.println("El formato correcto es: edificar [Casa, Hotel, Piscina, PistaDeporte]");
                    }
                    break;

                case "cambiar":
                    if (palabrasArray.length == 2 && palabrasArray[1].equals("modo")){
                        juego.modoAvanzado();
                    } else {
                        System.out.println("El formato correcto es: cambiar modo");
                    }
                    break;

                case "vender":
                    if(palabrasArray.length == 4) {
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
                    System.out.println("Comando no válido");
                    break;
            }
        }
    }




}

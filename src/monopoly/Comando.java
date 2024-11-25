package monopoly;

import partida.Jugador;

import java.util.ArrayList;
import java.util.List;


public interface Comando {

    //Funciones que se llaman al reconocer un comando
    void crearJugadores();
    void lanzarDados(int tirada1, int tirada2);
    void lanzarDados(Jugador jugador);
    void evaluacion();
    void VueltasTablero();
    void avanzar();
    void acabarTurno();
    void listarVenta();
    void listarJugadores();
    void listarAvatares();
    void listarEdificios();
    void listarEdificiosGrupo(String color);
    void salirCarcel();
    void descJugador(String nombre);
    void descAvatar(String ID);
    void descCasilla(String NombreCasilla);
    void comprar(String nombre);
    void hipotecar(String nombreCasilla);
    void deshipotecar(String nombreCasilla);
    void edificar(String palabra);
    void modoAvanzado();
    void ventaEdificio(String tipo, String nombreCasilla, String cantidad);
    void estadisticas();
    void estadisticasJugador(String jugadorStr);
    void bancarrota(boolean voluntario);


    //Funciones auxiliares que complementan los métodos de los comandos
    void moverJugadorPelota(int valorTirada);
    void moverJugadorCoche(int valorTirada);
    void imprimirLogo();
    boolean esJugadorRepetido(String nombre);
    boolean esAvatarCorrecto(String tipoAvatar);
    void darAltaJugador(String nombre, String tipoAvatar);
    String infoTrasTurno(Jugador jugador);
    void imprimirNombres(List<?> lista);
    void jugadoresEstadisticasGenerales(ArrayList<Jugador> jugadoresMasVueltas,
                                        ArrayList<Jugador> jugadoresMasTiradasDados,
                                        ArrayList<Jugador> jugadoresEnCabeza);
    void casillasEstadisticasGenerales(ArrayList<Casilla> casillasMasRentables,
                                              ArrayList<Casilla> casillasMasFrecuentadas);

    ArrayList<Grupo> calcularGruposMasRentables();
    void conseguirDinero(float dineroAConseguir);


}

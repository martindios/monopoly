package monopoly;

import partida.Jugador;

import java.util.ArrayList;
import java.util.List;

public interface Comando {
    void preIniciarPartida();
    void iniciarPartida();
    void crearJugadores();
    void modoAvanzado();
    void avanzar();
    void moverJugadorPelota(int valorTirada);
    void moverJugadorCoche(int valorTirada);
    void estadisticasJugador(String jugadorStr);
    void edificar(String palabra);
    void imprimirLogo();
    boolean esJugadorRepetido(String nombre);
    boolean esAvatarCorrecto(String tipoAvatar);
    void descJugador(String nombre);
    void descAvatar(String ID);
    void descCasilla(String NombreCasilla);
    void lanzarDados(int tirada1, int tirada2);
    void lanzarDados(Jugador jugador);
    void comprar(String nombre);
    void salirCarcel();
    void listarVenta();
    void listarJugadores();
    void listarAvatares();
    void acabarTurno();
    void darAltaJugador(String nombre, String tipoAvatar);
    String infoTrasTurno(Jugador jugador);
    void evaluacion();
    void VueltasTablero();
    void listarEdificios();
    void listarEdificiosGrupo(String color);
    void ventaEdificio(String tipo, String nombreCasilla, String cantidad);
    void estadisticas();
    void imprimirNombres(List<?> lista);
    void jugadoresEstadisticasGenerales(ArrayList<Jugador> jugadoresMasVueltas,
                                        ArrayList<Jugador> jugadoresMasTiradasDados,
                                        ArrayList<Jugador> jugadoresEnCabeza);
    void casillasEstadisticasGenerales(ArrayList<Casilla> casillasMasRentables,
                                              ArrayList<Casilla> casillasMasFrecuentadas);

    ArrayList<Grupo> calcularGruposMasRentables();
    void hipotecar(String nombreCasilla);
    void deshipotecar(String nombreCasilla);
    void conseguirDinero(float dineroAConseguir);
    void bancarrota(boolean voluntario);


}

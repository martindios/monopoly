package monopoly;

import partida.Jugador;

import java.util.ArrayList;

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
    void MoverAux(String pos);
    void ventaEdificio(String tipo, String nombreCasilla, String cantidad);
    void estadisticas();
    void imprimirNombresJugadores(ArrayList<Jugador> jugadores);
    void imprimirNombresCasillas(ArrayList<Casilla> casillas);
    void imprimirNombresGrupos(ArrayList<Grupo> grupos);
    ArrayList<Jugador> jugadoresConMasVueltas();
    ArrayList<Jugador> jugadoresConMasTiradasDados();
    ArrayList<Jugador> jugadoresEnCabeza();
    ArrayList<Casilla> casillasMasRentables();
    ArrayList<Grupo> calcularGruposMasRentables();
    ArrayList<Casilla> casillasMasFrecuentadas();
    void hipotecar(String nombreCasilla);
    void deshipotecar(String nombreCasilla);
    void conseguirDinero(float dineroAConseguir);
    int introducirNum(int min, int max);
    void bancarrota();


}

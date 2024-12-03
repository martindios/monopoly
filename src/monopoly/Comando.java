package monopoly;

import monopoly.casilla.Casilla;
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



}

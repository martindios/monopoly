package monopoly;

import monopoly.excepcion.ExcepcionEntidadNoExistente;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import monopoly.excepcion.excepcionCarcel.ExcepcionJugadorEnCarcel;
import monopoly.excepcion.ExcepcionMovimientosAvanzados;
import monopoly.excepcion.ExcepcionNoHayPropiedadesVenta;
import monopoly.excepcion.excepcionEntradaUsuario.ExcepcionEntradaUsuario;
import partida.Jugador;


public interface Comando {

    //Funciones que se llaman al reconocer un comando
    void crearJugadores() throws ExcepcionEntradaUsuario;
    void lanzarDados(int tirada1, int tirada2) throws Exception;
    void lanzarDados(Jugador jugador);
    void evaluacion() throws ExcepcionEntidadNoExistente;
    void VueltasTablero();
    void avanzar() throws ExcepcionMovimientosAvanzados;
    void acabarTurno();
    void listarVenta() throws ExcepcionNoHayPropiedadesVenta;
    void listarJugadores();
    void listarAvatares();
    void listarEdificios() throws Exception;
    void listarEdificiosGrupo(String color);
    void salirCarcel() throws ExcepcionEntidadNoExistente;
    void descJugador(String nombre) throws ExcepcionEntidadNoExistente;
    void descAvatar(String ID) throws ExcepcionEntidadNoExistente;
    void descCasilla(String NombreCasilla) throws ExcepcionEntidadNoExistente;
    void comprar(String nombre);
    void hipotecar(String nombreCasilla);
    void deshipotecar(String nombreCasilla);
    void edificar(String palabra) throws Exception;
    void modoAvanzado() throws ExcepcionMovimientosAvanzados;
    void ventaEdificio(String tipo, String nombreCasilla, String cantidad) throws ExcepcionEntidadNoExistente;
    void estadisticas();
    void estadisticasJugador(String jugadorStr) throws ExcepcionEntidadNoExistente;
    void bancarrota(boolean voluntario);
    void clasificarTrato(String jugadorOfertado, String objeto1, String objeto2, String objeto3);



}

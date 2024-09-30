package partida;

import monopoly.*;

import java.util.ArrayList;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    //El ArrayList se encarga de almacenar todos los avatares creados. Con esto, creamos la variable id para generar un ID ÚNICO con la función generarID,
    //pasando el ArrayList para que la función evite la repetición
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        //Generamos o id do avatar co metodo e asígnase dentro deste, pasándolle o array de avatares xa creados, para asi dsp coller o seu id e non repetilos
        generarId(avCreados);
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
    }

    /*
    GETTERS
     */

    //getter para devolver el id de un jugador
    public String getId() {
        return id;
    }

    //getter para devolver el tipo de avatar de un jugador
    public String getTipo() {
        return tipo;
    }

    //getter para devolver el jugador asociado al avatar
    public Jugador getJugador() {
        return jugador;
    }

    //getters para devolver el lugar en el que se encuentra el avatar
    public Casilla getLugar() {
        return lugar;
    }
    public String infoAvatar() {
        String toString = """
                {
                Id: %s
                Tipo: %s
                Casilla: %s
                Jugador: %s
                }""".formatted(id, tipo, lugar, jugador);
        return toString;
    }
}

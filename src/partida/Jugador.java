package partida;

import java.util.ArrayList;

import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    private ArrayList<String> hipotecas;

    private ArrayList<String> edificios; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        //Como temos que crear aquí o avatar, usamos o constructor del con args: Tipo do avatar, Xogador que o ten, Casilla de inicio e o array de avatares para nn repetilo
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        // Añadimos o avatar á lista de avatares creados
        avCreados.add(this.avatar);
        this.fortuna = 1500; //Fortuna inicial de ejemplo, dsp cambiamolo
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
    }



    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
    }

    public String getNombre() {
        return nombre;
    }

    private String listaPropiedades() {
        String listaPropiedades = "";
        if (!propiedades.isEmpty()) {
            listaPropiedades = listaPropiedades + "[" + propiedades.get(0);

            for(int i = 1; i < propiedades.size(); ++i) {
                listaPropiedades = listaPropiedades + ", " + propiedades.get(i);
            }

            listaPropiedades = listaPropiedades + "]";
        }
        else {
            listaPropiedades = "[]";
        }
        return listaPropiedades;
    }

    private String listaHipotecas() {
        String listaHipotecas = "";
        if (!hipotecas.isEmpty()) {
            listaHipotecas = listaHipotecas + "[" + hipotecas.get(0);

            for(int i = 1; i < hipotecas.size(); ++i) {
                listaHipotecas = listaHipotecas + ", " + hipotecas.get(i);
            }

            listaHipotecas = listaHipotecas + "]";
        }
        else {
            listaHipotecas = "[]";
        }
        return listaHipotecas;
    }

    public String info() {
        String listaPropiedades = listaPropiedades();
        String listaHipotecas = listaHipotecas();

        String info = """
                {
                Nombre: %s
                Avatar: %s
                Fortuna: %f
                Propiedades: %s
                Hipotecas: %s
                Edificios: %s
                }""".formatted(nombre, avatar, fortuna, listaPropiedades, listaHipotecas, edificios);
        return info;
    }
}

package monopoly;

import partida.*;
import java.util.ArrayList;
import java.lang.String;
import java.util.Objects;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Impuestos).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Constructores:
    //Parámetros vacíos
    public Casilla() {
    }

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */

    //Aquí falta o grupo, que sinceramente, nin puta idea de como facelo, pq mo imagino como algo circular
    //Generamos o tablero, que genera as casillas e dsp genera os grupos metendo as casillas, pero se se generan antes as casillas
    //Como coño inicializo un grupo se nin existen. O que se me ocurre é generar as casillas sin grupos, generar dsp os grupos e dsp crear un
    //metodo que asigne a cada casilla o grupo no que está
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.valor = valor;
        if(tipo.equals("SOLAR")) {
            this.impuesto = valor*0.1f;
            //String Color = ClasificarSolar(posicion);
            //this.grupo =
        }
        else if(tipo.equals("SERVICIOS")) {
            this.impuesto = Valor.SUMA_VUELTA * 0.75f;
        }
        else if(tipo.equals("TRANSPORTE")) {
            this.impuesto = Valor.SUMA_VUELTA;
        }
        this.hipoteca = valor*0.5f;
        avatares = new ArrayList<Avatar>();
    }
    

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    //DUDA: En atribs que nn teñen moito sentido, como aquí valor ou hipoteca, non os inicializamos? Inicializanse a 0? a null?
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "IMPUESTOS";
        //valor non ten, non se poden comprar
        this.posicion = posicion;
        this.duenho = duenho; //Será a banca, hai q poñerllo cnd se crea
        //Grupo nada que nn é solar
        /**
         * Impuestos: A casilla IMPUESTOS1 terá unha tasa = igual a la cantidad que recibirá el jugador cada vez q completa una vuelta (SUMA_VUELTA)
         * A casilla IMPUESTOS2 terá unha tasa = mitad da anterior (SUMA_VUELTA * 0.5f)
         */
        this.impuesto = impuesto;
        //Hipoteca tmp ten un valor determinado, non se pode comprar/vender etc
        this.avatares = new ArrayList<Avatar>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales[carcel, parking, salida, IrCarcel]):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    //public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
    //}
    //temporal
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        //Salida e IrCarcel non teñen valor, pero Parking o valor é o bote e en Carcel (entendo) que é o precio para salir dela. Ademais, este 25% págase á BANCA
        //As casillas suerte están relacionadas con pagos/cobros, pero no guion1 non pon nada, entonces entenco que de momento deso nada
        //As casillas de comunidad tmp din moito, solo que principalmente consisten en movimientos entre casillas
        if(nombre.equals("CARCEL")) {
            this.valor = Valor.SUMA_VUELTA * 0.25f;
        }
        else if(nombre.equals("PARKING")) {
            this.valor = 0; //O valor do parking ven sendo o bote que recibe o xogador que cae na casilla. Entonces, empeza en 0 de valor.
        }
        this.posicion = posicion;
        this.duenho = duenho; //Será a banca, ten que poñerse á man
        //Grupo nada, non son solares
        //IMpuesto non teñen nada
        //Hipoteca tmp ten nada
        this.avatares = new ArrayList<Avatar>();
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {

    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        return false;
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        return null;
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */

    public String casaEnVenta() {
        return null;
    }

    /*
    GETTERS
    */

    //getter para devolver el nombre de la casilla
    public String getNombre() {
        return nombre;
    }

    //getter para devolver el tipo de la casilla
    public String getTipo() {
        return tipo;
    }

    //getter para devolver el valor de la casilla
    public float getValor() {
        return valor;
    }

    //getter para devolver la posición de la casilla
    public int getPosicion() {
        return posicion;
    }

    //getter para devolver el dueño de la casilla
    public Jugador getDuenho() {
        return duenho;
    }

    //getter para devolver el grupo de la casilla
    public Grupo getGrupo() {
        return grupo;
    }

    //getter para devolver la cantidad que hay que pagar al caer en la casilla
    public float getImpuesto() {
        return impuesto;
    }

    //getter para devolver el valor otorgado por hipotecar una casilla
    public float getHipoteca() {
        return hipoteca;
    }

    //getter para devolver la lista de los avatares en la casilla
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    /*
    SETTERS
    */
    /**
     * Metodo para clasificar solares

    public String ClasificarSolar (int posicion) {
        if(posicion == 2 || posicion == 4) return "BLUE";
        else if(posicion == 7 || posicion == 9 || posicion == 10) return "GREEN";
        else if(posicion == 12 || posicion == 14 || posicion == 15) return "PURPLE";
        else if(posicion == 17 || posicion == 19 || posicion == 20) return "CYAN";
        else if(posicion == 22 || posicion == 24 || posicion == 25) return "YELLOW";
        else if(posicion == 27 || posicion == 28 || posicion == 30) return "RED";
        else if(posicion == 32 || posicion == 33 || posicion == 35) return "BLACK";
        else if(posicion == 38 || posicion == 40) return "IRED";
        else return null;
    }*/
}

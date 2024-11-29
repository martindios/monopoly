package monopoly.casilla;

import monopoly.Edificio.Edificio;
import monopoly.Grupo;
import monopoly.casilla.propiedad.*;
import partida.*;

import java.util.ArrayList;
import java.lang.String;
import java.util.Iterator;

//Cando se vende un solar por bancarrota, o precio reiníciase. Sin embargo, o precio incrementa cando non compras, entonces simplemente se da que o solar volve ao precio
//No que se compru, dado que nn se debería actualizar
public abstract class Casilla {

    /**********Atributos**********/
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Impuestos).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float impuestoInicial;
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private int totalVecesFrecuentada;

    /**********Constructores**********/

    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.totalVecesFrecuentada = 0;
        this.avatares = new ArrayList<Avatar>();
    }

    /**********Getters**********/

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public int getTotalVecesFrecuentada() {
        return totalVecesFrecuentada;
    }

    public float getImpuestoInicial() {
        return impuestoInicial;
    }

    /**********Setters**********/

    public void setValor(float valor) {
        if(valor < 0) this.valor = 0;
        else this.valor = valor;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setImpuesto(float impuesto) {
        if(impuesto < 0) this.impuesto = 0;
        else this.impuesto = impuesto;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    public void setImpuestoInicial(float impuestoInicial) {
        this.impuestoInicial = impuestoInicial;
    }


    /**********Métodos**********/

    /*Método abstracto para evaluar la casilla pertinente*/
    public abstract boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada);

    /**
     * Método que devuelve true si el avatar pasado por parámetro se encuentra
     * en la casilla, false si no está.
     */
    boolean estaAvatar(Avatar avatar) {
        return avatares.contains(avatar);
    }

    /*Método utilizado para añadir un avatar al array de avatares en casilla*/
    public void anhadirAvatar(Avatar av) {
        av.setLugar(this);
        avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        ArrayList<Avatar> avatares = this.getAvatares();
        avatares.remove(av);
    }

    /*Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /**
     * Incrementa el contador de veces que la casilla ha sido frecuentada.
     */
    public void sumarVecesFrecuentada() {
        this.totalVecesFrecuentada++;
    }

    /**
     * Calcula el dinero necesario para que el jugador actual pueda pagar el alquiler o impuesto
     * en la casilla en la que se encuentra. (cuando no tiene dinero suficiente)
     *
     * @param jugadorActual El jugador que está en la casilla.
     * @param banca La banca del juego.
     * @param tirada El valor de la tirada de dados, utilizado para calcular el alquiler en casillas de servicios.
     * @return La cantidad de dinero necesaria para pagar el alquiler o impuesto.
     */
    public float calcularDineroNecesarioCasilla(Jugador jugadorActual, Jugador banca, int tirada) {
        if (jugadorActual.equals(this.getDuenho())) {
            return 0;
        }
        switch (this) {
            case Solar solar -> {
                if (this.getDuenho().equals(banca)) {
                    return 0;
                } else {
                    return (solar.calcularAlquiler(jugadorActual, banca, tirada) - jugadorActual.getFortuna());
                }
            }
            case Servicio servicio -> {
                if (this.getDuenho().equals(banca)) {
                    return 0;
                } else {
                    return (servicio.calcularAlquiler(jugadorActual, banca, tirada) - jugadorActual.getFortuna());
                }
            }
            case Transporte transporte -> {
                if(this.getDuenho().equals(banca)) {
                    return 0;
                } else {
                    return (transporte.calcularAlquiler(jugadorActual, banca, tirada) - jugadorActual.getFortuna());
                }
            }
            case Especial especial -> {
                if (this.getNombre().equals("Cárcel")) {
                    return (this.getImpuesto() - jugadorActual.getFortuna());
                } else {
                    return 0;
                }
            }
            case Impuesto _ -> {
                return (this.getImpuesto() - jugadorActual.getFortuna());
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Proporciona información detallada sobre la casilla actual.
     *
     * @return Una cadena con la descripción de la casilla.
     */
    public String infoCasilla() {
        return("Descripción de la casilla: " + this.getNombre() + ". Posición " + this.getPosicion() + ".");
    }
}

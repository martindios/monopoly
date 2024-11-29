package monopoly.casilla.propiedad;

import monopoly.casilla.Casilla;
import partida.Jugador;

public abstract class Propiedad extends Casilla {

    /**********Atributos**********/
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private boolean hipotecado; //Indica si la casilla está hipotecada o no
    private float totalAlquileresPagados;
    private int contador; //Contador de veces que el dueño ha caído en la casilla

    /**********Constructor**********/
    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        super(nombre, tipo, posicion, duenho);
        this.setValor(valor);
        this.totalAlquileresPagados = 0;
        this.hipoteca = valor * 0.5f;
        this.hipotecado = false;
    }

    /**********Getters**********/

    public float getHipoteca() {
        return hipoteca;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public float getTotalAlquileresPagados() {
        return totalAlquileresPagados;
    }

    public int getContador() {
        return contador;
    }

    /**********Setters**********/

    public void setHipoteca(float hipoteca) {
        if(hipoteca < 0) this.hipoteca = 0;
        else this.hipoteca = hipoteca;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    public void setTotalAlquileresPagados(float totalAlquileresPagados) {
        this.totalAlquileresPagados = totalAlquileresPagados;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    /**********Métodos**********/

    /**
     * Método abstracto que proporciona información sobre una casilla está en venta.
     *
     * @return Una cadena que indica si la casilla está en venta.
     */
    public abstract String casillaEnVenta();

    public abstract float calcularAlquiler(Jugador jugadorActual, Jugador banca, int tirada);

    /**
     * Incrementa el contador del dueño de la propiedad.
     */
    public void sumarContadorDuenho() {
        this.contador++;
    }

    /**
     * Verifica si la propiedad pertenece al jugador especificado.
     *
     * @param jugador El jugador a verificar.
     * @return true si la propiedad pertenece al jugador, false en caso contrario.
     */
    public boolean perteneceAJugador(Jugador jugador) {
        return this.getDuenho().equals(jugador);
    }

    public void pagarAlquiler(Jugador jugadorActual, Jugador banca, int tirada) {
        if (this.getDuenho().equals(banca)) {
            return;
        }
        if (this.isHipotecado()) {
            System.out.println("La casilla está hipotecada, no se paga alquiler.");
            return;
        }
        if (!jugadorActual.equals(this.getDuenho())) {
            float alquiler = calcularAlquiler(jugadorActual, banca, tirada);

            jugadorActual.sumarFortuna(-alquiler);
            jugadorActual.sumarGastos(alquiler);
            jugadorActual.sumarPagoDeAlquileres(alquiler);
            this.getDuenho().sumarFortuna(alquiler);
            this.getDuenho().sumarCobroDeAlquileres(alquiler);
            this.totalAlquileresPagados += alquiler;
        }
    }



    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca  (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        float valorCasilla = this.getValor();
        if(this.getPosicion() != solicitante.getAvatar().getLugar().getPosicion()) {
            System.out.println("No estás situado en la casilla deseada.");
            return;
        }
        if(this.getDuenho() != banca) {
            System.out.println("La casilla ya pertenece a un jugador.");
            return;
        }

        if (this instanceof Transporte) {
            solicitante.setNumTransportes(solicitante.getNumTransportes() + 1);
        } else if (this instanceof Servicio) {
            solicitante.setNumServicios(solicitante.getNumServicios() + 1);
        }

        solicitante.sumarFortuna(-valorCasilla);
        solicitante.sumarGastos(valorCasilla);
        solicitante.sumarDineroInvertido(valorCasilla);
        banca.sumarFortuna(valorCasilla);
        this.setDuenho(solicitante);
        solicitante.anhadirPropiedad(this);

        System.out.println("El jugador ha comprado la casilla por " + valorCasilla + ".");
    }

    /**
     * Proporciona información detallada sobre la casilla.
     *
     * @return Una cadena con el tipo, dueño, valor y hipoteca de la casilla.
     */
    @Override
    public String infoCasilla() {
        System.out.println(super.infoCasilla());
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
                "dueño: " + this.getDuenho().getNombre() + ",\n" +
                "valor: " + this.getValor() + ",\n" +
                "hipoteca: " + this.getHipoteca();
    }

}

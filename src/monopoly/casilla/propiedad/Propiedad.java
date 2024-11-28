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


    /**
     * Método para pagar el alquiler de una propiedad.
     *
     * @param jugadorActual El jugador que debe pagar el alquiler.
     * @param banca El jugador que representa la banca.
     * @param tirada El valor de la tirada de dados.
     */
    public void pagarAlquiler(Jugador jugadorActual, Jugador banca, int tirada) {
        float alquiler = 0;
        if(this.getDuenho().equals(banca)) {
            return;
        }
        /*Comprobar que el dueño de la casilla no es él mismo*/
        if (!jugadorActual.equals(this.getDuenho())) {
            if(this.hipotecado) { // Comprobar si la casilla está hipotecada
                System.out.println("La casilla está hipotecada, no se paga alquiler.");
                return;
            }
            
            switch (this) {
                case Solar solar -> {
                    alquiler = solar.pagarAlquilerSolar(jugadorActual, banca);
                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha pagado a " + this.getDuenho().getNombre()
                            + " " + alquiler + " por el alquiler de " + this.getNombre());
                }
                case Servicio servicio -> {
                    alquiler = servicio.pagarAlquilerServicio(jugadorActual, banca, tirada);
                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha pagado a " + this.getDuenho().getNombre()
                            + " " + alquiler + " por el servicio de " + this.getNombre());
                }
                case Transporte transporte -> {
                    alquiler = transporte.pagarAlquilerTransporte(jugadorActual, banca);
                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha pagado a " + this.getDuenho().getNombre()
                            + " " + alquiler + " por el transporte de " + this.getNombre());
                }
                default -> System.out.println("Error pagando el alquiler, no es solar, servicio o transporte");
            }

            jugadorActual.sumarFortuna(-alquiler);
            jugadorActual.sumarGastos(alquiler);
            jugadorActual.sumarPagoDeAlquileres(alquiler);
            this.getDuenho().sumarFortuna(alquiler);
            this.getDuenho().sumarCobroDeAlquileres(alquiler);
            this.totalAlquileresPagados += alquiler;

        }
    }

    /**
     * Método abstracto que proporciona información sobre una casilla está en venta.
     *
     * @return Una cadena que indica si la casilla está en venta.
     */
    public abstract String casillaEnVenta();

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

    @Override
    public String infoCasilla() {
        System.out.println(super.infoCasilla());
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
                "dueño: " + this.getDuenho().getNombre() + ",\n" +
                "valor: " + this.getValor() + ",\n" +
                "hipoteca: " + this.getHipoteca();
    }

}

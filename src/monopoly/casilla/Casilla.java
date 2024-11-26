package monopoly.casilla;

import monopoly.Edificio;
import monopoly.Grupo;
import monopoly.casilla.propiedad.*;
import partida.*;

import java.util.ArrayList;
import java.lang.String;
import java.util.Iterator;
import java.util.Objects;

import static monopoly.Valor.SUMA_VUELTA;

//Cando se vende un solar por bancarrota, o precio reiníciase. Sin embargo, o precio incrementa cando non compras, entonces simplemente se da que o solar volve ao precio
//No que se compru, dado que nn se debería actualizar
public abstract class Casilla {

    /**********Atributos**********/
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Impuestos).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float impuestoInicial;
    //private float hipoteca; //Valor otorgado por hipotecar una casilla
    //private boolean hipotecado; //Indica si la casilla está hipotecada o no
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private int contador; //Contador de veces que el dueño ha caído en la casilla
    //private ArrayList<Edificio> edificios; //Edificios construidos en la casilla

    //private float totalAlquileresPagados;
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

    public Grupo getGrupo() {
        return grupo;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public int getContador() {
        return contador;
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

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public void setImpuestoInicial(float impuestoInicial) {
        this.impuestoInicial = impuestoInicial;
    }


    /**********Métodos**********/

    /**
     * Método que devuelve true si el avatar pasado por parámetro se encuentra
     * en la casilla, false si no está.
     */
    boolean estaAvatar(Avatar avatar) {
        return avatares.contains(avatar);
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
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
     * Método para evaluar si el jugador es solvente en la casilla en la que se encuentra.
     *
     * @param jugador Jugador cuyo avatar está en esa casilla.
     * @param banca La banca (para ciertas comprobaciones).
     * @param tirada El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
     * @return true en caso de ser solvente (es decir, de cumplir las deudas), y false en caso de no cumplirlas.
     */
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        if (jugador.equals(this.getDuenho())) {
            return true;
        }

        switch (this) {
            case Solar solar -> {
                return solar.evaluarSolar(jugador, banca);
            }
            case Servicio servicio -> {
                return servicio.evaluarServicio(jugador, banca, tirada);
            }
            case Transporte transporte -> {
                return transporte.evaluarTransporte(jugador, banca);
            }
            case Especial especial -> {
                return especial.evaluarEspecial(jugador, banca);
            }
            case Impuesto impuestoClase -> {
                return impuestoClase.evaluarImpuesto(jugador, banca);
            }
            default -> {
                return true;
            }
        }
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
                    return (solar.pagarAlquilerSolar(jugadorActual, banca) - jugadorActual.getFortuna());
                }
            }
            case Servicio servicio -> {
                if (this.getDuenho().equals(banca)) {
                    return 0;
                } else {
                    return (servicio.pagarAlquilerServicio(jugadorActual, banca, tirada) - jugadorActual.getFortuna());
                }
            }
            case Transporte transporte -> {
                if(this.getDuenho().equals(banca)) {
                    return 0;
                } else {
                    return (transporte.pagarAlquilerTransporte(jugadorActual, banca) - jugadorActual.getFortuna());
                }
            }
            case Especial especial -> {
                if (this.getNombre().equals("Cárcel")) {
                    return (this.getImpuesto() - jugadorActual.getFortuna());
                } else {
                    return 0;
                }
            }
            case Impuesto impuestoClase -> {
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
        System.out.println("Descripción de la casilla: " + this.getNombre() + ". Posición " + this.getPosicion() + ".");

        switch (this) {
            case Propiedad propiedad -> {
                return propiedad.infoCasillaPropiedad();
            }
            case Impuesto impuestoClase -> {
                return impuestoClase.infoCasillaImpuesto();
            }
            case Especial especial -> {
                return especial.infoCasillaEspecial();
            }
            default -> {
                return "";
            }
        }
    }









    /*public void quitarCasas(int numCasas, Jugador jugador) {
        this.getEdificios().removeIf(casa -> casa.getIdEdificio().contains("Casa") && numCasas != 0);
        jugador.getEdificios().removeIf(casa -> casa.getIdEdificio().contains("Casa") && numCasas != 0);
        Iterator<Edificio> iterator = edificios.iterator();
        while (iterator.hasNext()) {
            Edificio casa = iterator.next();
            if (casa.getIdEdificio().contains("Casa") && numCasas != 0) {
                iterator.remove(); // Usar iterator.remove() para evitar ConcurrentModificationException
            }
        }
    }*/



    /**
     * Método para mostrar información de una casilla en venta.
     *
     * @return Un texto con la información de la casilla en venta:
     *         - Para casillas de tipo "Solar", se devuelve información específica de la casilla solar.
     *         - Para casillas de tipo "Transporte" y "Servicios", se devuelve información de transporte o servicios.
     *         - Si la casilla no está en venta, se devuelve el mensaje "Casilla no en venta."
     */
    public String casEnVenta() {
        return switch (this.tipo) {
            case "Solar" -> this.infoSolar();
            case "Transporte", "Servicios" -> this.infoTransServ();
            default -> "Casilla no en venta.";
        };
    }

    /**
     * Proporciona información detallada sobre una casilla de tipo "Solar".
     *
     * @return Una cadena formateada que contiene el nombre, tipo,
     *         grupo y valor de la casilla.
     */
    private String infoSolar() {
        return """
                {
                    Nombre: %s,
                    Tipo: %s,
                    grupo: %s,
                    valor: %.2f.
                }""".formatted(nombre, tipo, grupo.getNombreGrupo(), valor);
    }

    /**
     * Proporciona información detallada sobre una casilla de tipo "Transporte" o "Servicios".
     *
     * @return Una cadena formateada que contiene el nombre, tipo,
     *         y valor de la casilla.
     */
    private String infoTransServ() {
        return """
                {
                    Nombre: %s
                    Tipo: %s,
                    valor: %.2f.
                }""".formatted(nombre, tipo, valor);
    }

    public void sumarContadorDuenho() {
        this.contador++;
    }
    /**
     * Metodo que convierte una lista de elementos en una representación de cadena.
     * Si la lista contiene cadenas, se devuelven como una lista de cadenas.
     * Si la lista contiene objetos de tipo Casilla, se devuelven sus nombres.
     * Si la lista contiene objetos de tipo Edificios, se devuelven sus id's.
     *
     * @param array La lista de elementos a convertir en cadena. Puede contener
     *              objetos de tipo String o Casilla.
     * @return Una representación de cadena de la lista, con los elementos
     *         separados por comas y encerrados entre corchetes. Si la lista está
     *         vacía, se devuelve un guion ("-").
     */
    protected static String listaArray(ArrayList<?> array) {
        StringBuilder listaArray = new StringBuilder();

        if (!array.isEmpty()) {
            Object firstElement = array.getFirst();

            if (firstElement instanceof String) {
                listaArray.append("[").append((String) firstElement);
            } else if (firstElement instanceof Casilla) {
                listaArray.append("[").append(((Casilla) firstElement).getNombre());
            } else if (firstElement instanceof Edificio) {
                listaArray.append("[").append(((Edificio) firstElement).getIdEdificio());
            }

            for (int i = 1; i < array.size(); ++i) {
                Object element = array.get(i);
                if (element instanceof String) {
                    listaArray.append(", ").append((String) element);
                } else if (element instanceof Casilla) {
                    listaArray.append(", ").append(((Casilla) element).getNombre());
                } else if (element instanceof Edificio) {
                    listaArray.append(", ").append(((Edificio) element).getIdEdificio());
                }
            }

            listaArray.append("]");
        } else {
            listaArray = new StringBuilder("-");
        }
        return listaArray.toString();
    }

    public String edificiosGrupo() {
        ArrayList<String> Casas = new ArrayList<>();
        ArrayList<String> Hoteles = new ArrayList<>();
        ArrayList<String> Piscinas = new ArrayList<>();
        ArrayList<String> Deporte = new ArrayList<>();
        for(Edificio edificio : edificios){
            switch (edificio.getTipo()) {
                case "Casa":
                    Casas.add(edificio.getIdEdificio());
                    break;
                case "Hotel":
                    Hoteles.add(edificio.getIdEdificio());
                    break;
                case "Piscina":
                    Piscinas.add(edificio.getIdEdificio());
                    break;
                case "Deporte":
                    Deporte.add(edificio.getIdEdificio());
                    break;
            }
        }
        return """
                {
                Propiedad: %s,
                Casas: %s,
                Hoteles: %s,
                Piscinas: %s,
                Pistas de deporte: %s,
                Alquiler: %.2f
                }
                """.formatted(this.getNombre(), Casas.isEmpty() ? '-' : Casas, Hoteles.isEmpty() ? '-' : Hoteles, Piscinas.isEmpty() ? '-' : Piscinas,
                Deporte.isEmpty() ? '-' : Deporte, this.getImpuesto());
    }

    public void venderEdificios(String tipo, int cantidad) {
        Jugador propietario = this.getDuenho();
        Iterator<Edificio> iterator = edificios.iterator();
        while (iterator.hasNext() && cantidad > 0) {
            Edificio edificio = iterator.next();
            if (edificio.getTipo().equals(tipo)) {
                iterator.remove();
                ArrayList<Edificio> aux = propietario.getEdificios();
                aux.remove(edificio);
                propietario.setEdificios(aux);
                propietario.sumarFortuna(edificio.getValor() / 2);
                edificio.setCasilla(null);
                System.out.println("Se ha vendido un/una " + tipo + " por " + edificio.getValor() / 2 + "€.");
                cantidad--;
            }
        }
        this.modificarAlquiler();
    }





    public void modificarAlquiler() {
        int numCasas = getNumEdificios(edificios, "Casa");
        int numHoteles = getNumEdificios(edificios, "Hotel");
        int numPiscinas = getNumEdificios(edificios, "Piscina");
        int numPistaDeporte = getNumEdificios(edificios, "Deporte");
        float factorAlquiler = 0;
        // Cálculo para Casas
        if (numCasas > 0) {
            if (numCasas == 1) factorAlquiler += 5;
            else if (numCasas == 2) factorAlquiler += 15;
            else if (numCasas == 3) factorAlquiler += 35;
            else if (numCasas == 4) factorAlquiler += 50;
        }
        // Cálculo para Hoteles
        factorAlquiler += 70 * numHoteles;
        // Cálculo para Piscinas
        factorAlquiler += 25 * numPiscinas;
        // Cálculo para Pistas de Deporte
        factorAlquiler += 25 * numPistaDeporte;

        // Si no hay edificios, el factor es 0 (no se modifica el alquiler)
        // Calculamos el impuesto actualizado
        impuesto = impuestoInicial + (impuestoInicial * factorAlquiler); //QUE MIERDA ES ESTA
    }
}

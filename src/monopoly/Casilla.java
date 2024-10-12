package monopoly;

import partida.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.lang.String;
import java.util.Objects;

import static monopoly.Valor.SUMA_VUELTA;


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
        if(tipo.equals("Solar")) {
            this.impuesto = valor*0.1f;
        }
        else if(tipo.equals("Servicios")) {
            this.impuesto = SUMA_VUELTA / 200;
        }
        else if(tipo.equals("Transporte")) {
            this.impuesto = SUMA_VUELTA;
        }
        this.hipoteca = valor*0.5f;
        avatares = new ArrayList<Avatar>();
    }

    public Casilla(String nombre, String tipo, int posicion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;

    }


    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuestos";
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
        if(nombre.equals("Cárcel")) {
            this.impuesto = SUMA_VUELTA * 0.25f;
        }
        else if(nombre.equals("Parking")) {
            this.valor = 0; //O valor do parking ven sendo o bote que recibe o xogador que cae na casilla. Entonces, empeza en 0 de valor.
        }
        this.posicion = posicion;
        this.duenho = duenho; //Será a banca, ten que poñerse á man
        //Grupo nada, non son solares
        //IMpuesto non teñen nada
        //Hipoteca tmp ten nada
        this.avatares = new ArrayList<Avatar>();
    }



    //SETTERS
    public void setValor(float valor) {
        if(valor < 0) this.valor = 0;
        else this.valor = valor;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    //Setter de Impuesto. Cnd s edifica, o alquiler do solar aumenta
    public void setImpuesto(float impuesto) {
        if(impuesto < 0) this.impuesto = 0;
        else this.impuesto = impuesto;
    }

    public void setHipoteca(float hipoteca) {
        if(hipoteca < 0) this.hipoteca = 0;
        else this.hipoteca = hipoteca;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
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

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        if (actual.equals(this.getDuenho())) {
            return true;
        } else {
            switch (this.getTipo()) {
                case "Solar":
                    if (this.getDuenho().equals(banca)) {
                        return true;
                    } else {
                        return (actual.getFortuna() > this.getImpuesto());
                    }
                case "Servicios":
                    switch (this.getDuenho().getNumServicios()) {
                        case 1:
                            return actual.getFortuna() > (this.getImpuesto() * 4 * tirada);
                        case 2:
                            return actual.getFortuna() > (this.getImpuesto() * 10 * tirada);
                    }

                case "Especiales":
                    if (this.getNombre().equals("Cárcel")) {
                        return (actual.getFortuna() > this.getImpuesto());
                    } else {
                        return true;
                    }
                case "Transporte":
                    switch (this.getDuenho().getNumTransportes()) {
                        case 1:
                            return actual.getFortuna() > this.getImpuesto() * 0.25f;
                        case 2:
                            return actual.getFortuna() > this.getImpuesto() * 0.5f;
                        case 3:
                            return actual.getFortuna() > this.getImpuesto() * 0.75f;
                        case 4:
                            return actual.getFortuna() > this.getImpuesto();
                    }
                default:
                    return true;
            }
        }
    }

    public void pagarAlquiler(Jugador actual, Jugador banca, int tirada) {
        if (!actual.equals(this.getDuenho())) {
            switch (this.getTipo()) {
                case "Solar":
                    Jugador duenhoSolar = this.getDuenho();

                    //Comprobar si el dueño del solar es dueño de todo el grupo de color
                    if (this.getGrupo().esDuenhoGrupo(duenhoSolar)) {
                        actual.setFortuna(actual.getFortuna() - 2*this.getImpuesto());
                        duenhoSolar.setFortuna(duenhoSolar.getFortuna() + 2*this.getImpuesto());
                        actual.setGastos(actual.getGastos() + 2*this.getImpuesto());
                    } else {
                        actual.setFortuna(actual.getFortuna() - this.getImpuesto());
                        duenhoSolar.setFortuna(duenhoSolar.getFortuna() + this.getImpuesto());
                        actual.setGastos(actual.getGastos() + this.getImpuesto());
                    }
                    break;

                case "Servicios":
                    Jugador duenhoServicios = this.getDuenho();
                    switch (duenhoServicios.getNumServicios()) {
                        case 1:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto() * 4 * tirada);
                            duenhoServicios.setFortuna(duenhoServicios.getFortuna() + this.getImpuesto() * 4 * tirada);
                            actual.setGastos(actual.getGastos() + this.getImpuesto() * 4 * tirada);
                            break;

                        case 2:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto() * 10 * tirada);
                            duenhoServicios.setFortuna(duenhoServicios.getFortuna() + this.getImpuesto() * 10 * tirada);
                            actual.setGastos(actual.getGastos() + this.getImpuesto() * 10 * tirada);
                            break;
                    }
                    break;

                case "Transporte":
                    Jugador duenhoTransporte = this.getDuenho();
                    switch (duenhoTransporte.getNumTransportes()) {
                        case 1:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto() * 0.25f);
                            duenhoTransporte.setFortuna(duenhoTransporte.getFortuna() + this.getImpuesto() * 0.25f);
                            actual.setGastos(actual.getGastos() + this.getImpuesto() * 0.25f);
                            break;
                        case 2:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto() * 0.5f);
                            duenhoTransporte.setFortuna(duenhoTransporte.getFortuna() + this.getImpuesto() * 0.5f);
                            actual.setGastos(actual.getGastos() + this.getImpuesto() * 0.5f);
                            break;
                        case 3:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto() * 0.75f);
                            duenhoTransporte.setFortuna(duenhoTransporte.getFortuna() + this.getImpuesto() * 0.75f);
                            actual.setGastos(actual.getGastos() + this.getImpuesto() * 0.75f);
                            break;
                        case 4:
                            actual.setFortuna(actual.getFortuna() - this.getImpuesto());
                            duenhoTransporte.setFortuna(duenhoTransporte.getFortuna() + this.getImpuesto());
                            actual.setGastos(actual.getGastos() + this.getImpuesto());
                            break;
                    }
                    break;
            }
        }
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if(this.posicion != solicitante.getAvatar().getLugar().getPosicion()) {
            System.out.println("No estás situado en la casilla deseada.");
            return;
        }
        if(this.duenho != banca) {
            System.out.println("La casilla ya pertenece a un jugador.");
            return;
        }
        System.out.println("El jugador ha comprado la casilla.");
        solicitante.sumarFortuna(-this.valor);
        solicitante.sumarGastos(this.valor);
        banca.sumarFortuna(this.valor);
        this.setDuenho(solicitante);
        solicitante.anhadirPropiedad(this);
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
        System.out.println("Descripción de la casilla: " + this.getNombre() + ". Posición " + this.getPosicion() + ".");
        switch (this.tipo) {
            case "Solar":
                StringBuilder solar  = new StringBuilder();
                solar.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                solar.append("grupo: ").append(this.grupo.getNombreGrupo() + ",\n");
                solar.append("propietario: ").append(this.duenho.getNombre() + ",\n");
                solar.append("valor: ").append(this.valor + ",\n");
                solar.append("alquiler: ").append(this.impuesto + ",\n");
                solar.append("Hipoteca: ").append(this.hipoteca + ",\n");
                solar.append("valor hotel: ").append(this.valor*0.6 + ",\n");
                solar.append("valor casa: ").append(this.valor*0.6 + ",\n");
                solar.append("valor piscina: ").append(this.valor*0.4 + ",\n");
                solar.append("pista de deporte: ").append(this.valor*1.25 + ",\n");
                solar.append("alquiler una casa: ").append(this.impuesto*5 + ",\n");
                solar.append("alquiler dos casas: ").append(this.impuesto*15 + ",\n");
                solar.append("alquiler tres casas: ").append(this.impuesto*35 + ",\n");
                solar.append("alquiler cuatro casas: ").append(this.impuesto*50 + ",\n");
                solar.append("alquiler hotel: ").append(this.impuesto*70 + ",\n");
                solar.append("alquiler piscina: ").append(this.impuesto*25 + ",\n");
                solar.append("alquiler pista de deporte: ").append(this.impuesto*25);
                return solar.toString();

            case "Servicios":
                StringBuilder servicios = new StringBuilder();
                servicios.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                servicios.append("dueño: ").append(duenho.getNombre() + ",\n");
                servicios.append("valor: ").append(this.valor + ",\n");
                servicios.append("hipoteca: ").append(this.hipoteca);
                return servicios.toString();

            case "Transporte":
                StringBuilder transporte = new StringBuilder();
                transporte.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                transporte.append("dueño: ").append(duenho.getNombre() + ",\n");
                transporte.append("valor: ").append(this.valor + ",\n");
                transporte.append("hipoteca: ").append(this.impuesto);
                return transporte.toString();

            case "Impuestos":
                StringBuilder impuestos = new StringBuilder();
                impuestos.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                impuestos.append("impuesto: ").append(this.impuesto);
                return impuestos.toString();

            case "Especiales":
                if (Objects.equals(this.nombre, "Cárcel")) {
                    StringBuilder carcel = new StringBuilder();
                    carcel.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                    carcel.append("salir: ").append(this.impuesto + ",\n");
                    carcel.append("jugadores: ");
                    for(Avatar avatar : this.getAvatares()) {
                        carcel.append("[").append(avatar.getJugador().getNombre())
                                .append(", ").append(avatar.getJugador().getTiradasCarcel()).append("] ");
                    }
                    return carcel.toString();
                } else if (Objects.equals(this.nombre, "Parking")) {
                    StringBuilder parking = new StringBuilder();
                    parking.append("Tipo: ").append(this.tipo.toLowerCase() + ",\n");
                    parking.append("bote: ").append(this.valor + ",\n");
                    parking.append("jugadores: [");
                    for(Avatar avatar : this.getAvatares()) {
                        parking.append(avatar.getJugador().getNombre()).append(", ");
                    }

                    if(this.getAvatares().size() > 0) {
                        parking.setLength(parking.length() - 2);
                    }
                    parking.append("]\n");
                    return parking.toString();
                } else {
                    System.out.println("Esta casilla no necesita descripción");
                }
            default:
                return "Tipo de casilla incorrecto";
        }

    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        String resultado;
        switch (this.tipo) {
            case "Solar":
                resultado = this.infoSolar();
                break;
            case "Transporte":
                resultado = this.infoTransServ();
                break;
            case "Servicios":
                resultado = infoTransServ();
                break;
            default:
                resultado = "Casilla no en venta.";
        }
        return resultado;
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

    private String infoSolar() {
        return """
                {
                    Nombre: %s,
                    Tipo: %s,
                    grupo: %s,
                    valor: %.2f.
                }""".formatted(nombre, tipo, grupo.getNombreGrupo(), valor);
    }
    private String infoTransServ() {
        return """
                {
                    Nombre: %s
                    Tipo: %s,
                    valor: %.2f.
                }""".formatted(nombre, tipo, valor);
    }

}

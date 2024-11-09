package monopoly;

import partida.*;

import java.util.ArrayList;
import java.lang.String;
import java.util.Objects;
import java.util.Random;

import static monopoly.Valor.SUMA_VUELTA;

//Cando se vende un solar por bancarrota, o precio reiníciase. Sin embargo, o precio incrementa cando non compras, entonces simplemente se da que o solar volve ao precio
//No que se compru, dado que nn se debería actualizar
public class Casilla {

    /**********Atributos**********/
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Impuestos).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private int contador = 0; //Contador de veces que el dueño ha caído en la casilla
    private ArrayList<Edificio> edificios; //Avatares que están situados en la casilla.


    /**********Constructores**********/

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.valor = valor;
        switch (tipo) {
            case "Solar" -> this.impuesto = valor * 0.1f;
            case "Servicios" -> this.impuesto = SUMA_VUELTA / 200;
            case "Transporte" -> this.impuesto = SUMA_VUELTA;
        }
        this.hipoteca = valor*0.5f;
        avatares = new ArrayList<Avatar>();
        this.edificios = new ArrayList<Edificio>();
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
        this.edificios = new ArrayList<Edificio>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales[carcel, parking, salida, IrCarcel]):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
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
        this.edificios = new ArrayList<Edificio>();
    }

    /**********Getters**********/

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

    public int getContador() {
        return contador;
    }

    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    //getter para devolver el valor otorgado por hipotecar una casilla
    public float getHipoteca() {
        return hipoteca;
    }

    //getter para devolver la lista de los avatares en la casilla
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    //getter para devolver la lista de los edificios construídos en la casilla
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    /**********Setters**********/

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

    /**********Métodos**********/

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
                    if(this.getDuenho().equals(banca)) {
                        return true;
                    }
                    switch (this.getDuenho().getNumServicios()) {
                        case 1:
                            return actual.getFortuna() > (this.getImpuesto() * 4 * tirada);
                        case 2:
                            return actual.getFortuna() > (this.getImpuesto() * 10 * tirada);
                    }

                case "Especiales":
                    if (this.getNombre().equals("Cárcel")) {
                        return (actual.getFortuna() > this.getImpuesto()) || (actual.getTiradasCarcel() < 3);
                    } else {
                        return true;
                    }
                case "Transporte":
                    if(this.getDuenho().equals(banca)) {
                        return true;
                    }
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
                case "Impuestos":
                    return actual.getFortuna() > this.getImpuesto();
                default:
                    return true;
            }
        }
    }

    public void pagarAlquiler(Jugador actual, Jugador banca, int tirada) {
        if(this.getDuenho().equals(banca)) {
            return;
        }
        if (!actual.equals(this.getDuenho())) {
            switch (this.getTipo()) {
                case "Solar":
                    Jugador duenhoSolar = this.getDuenho();
                    // Comprobar si el dueño del solar es dueño de tdo el grupo de color
                    if (this.getGrupo().esDuenhoGrupo(duenhoSolar)) {
                        actual.sumarFortuna(-2 * this.getImpuesto());
                        duenhoSolar.sumarFortuna(2 * this.getImpuesto());
                        actual.sumarGastos(2 * this.getImpuesto());
                    } else {
                        actual.sumarFortuna(-this.getImpuesto());
                        duenhoSolar.sumarFortuna(this.getImpuesto());
                        actual.sumarGastos(this.getImpuesto());
                    }
                    System.out.println("El jugador " + actual.getNombre() + " ha pagado a " + duenhoSolar.getNombre()
                            + "por el alquiler de " + this.getNombre());
                    break;
                case "Servicios":
                    Jugador duenhoServicios = this.getDuenho();
                    switch (duenhoServicios.getNumServicios()) {
                        case 1:
                            actual.sumarFortuna(-this.getImpuesto() * 4 * tirada);
                            duenhoServicios.sumarFortuna(this.getImpuesto() * 4 * tirada);
                            actual.sumarGastos(this.getImpuesto() * 4 * tirada);
                            break;

                        case 2:
                            actual.sumarFortuna(-this.getImpuesto() * 10 * tirada);
                            duenhoServicios.sumarFortuna(this.getImpuesto() * 10 * tirada);
                            actual.sumarGastos(this.getImpuesto() * 10 * tirada);
                            break;
                    }
                    System.out.println("El jugador " + actual.getNombre() + " ha pagado a " + duenhoServicios.getNombre()
                            + "por el servicio de " + this.getNombre());
                    break;
                case "Transporte":
                    Jugador duenhoTransporte = this.getDuenho();
                    switch (duenhoTransporte.getNumTransportes()) {
                        case 1:
                            actual.sumarFortuna(-this.getImpuesto() * 0.25f);
                            duenhoTransporte.sumarFortuna(this.getImpuesto() * 0.25f);
                            actual.sumarGastos(this.getImpuesto() * 0.25f);
                            break;
                        case 2:
                            actual.sumarFortuna(-this.getImpuesto() * 0.5f);
                            duenhoTransporte.sumarFortuna(this.getImpuesto() * 0.5f);
                            actual.sumarGastos(this.getImpuesto() * 0.5f);
                            break;
                        case 3:
                            actual.sumarFortuna(-this.getImpuesto() * 0.75f);
                            duenhoTransporte.sumarFortuna(this.getImpuesto() * 0.75f);
                            actual.sumarGastos(this.getImpuesto() * 0.75f);
                            break;
                        case 4:
                            actual.sumarFortuna(-this.getImpuesto());
                            duenhoTransporte.sumarFortuna(this.getImpuesto());
                            actual.sumarGastos(this.getImpuesto());
                            break;
                    }
                    System.out.println("El jugador " + actual.getNombre() + " ha pagado a " + duenhoTransporte.getNombre()
                            + "por el transporte de " + this.getNombre());
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
        switch (this.getTipo()) {
            case "Transporte":
                solicitante.setNumTransportes(solicitante.getNumTransportes() + 1);
                break;
            case "Servicios":
                solicitante.setNumServicios(solicitante.getNumServicios() + 1);
                break;
        }
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
        this.valor += suma;
    }

    /**
     * Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.
     *
     * @return Una cadena con la descripción de la casilla que puede incluir:
     *         - Para solares: tipo, grupo, propietario, valor, alquiler, hipoteca y valores relacionados con edificios y alquileres.
     *         - Para servicios: tipo, dueño, valor y hipoteca.
     *         - Para transporte: tipo, dueño, valor y hipoteca.
     *         - Para impuestos: tipo e impuesto.
     *         - Para casillas especiales (como Cárcel o Parking): tipo, información específica de la casilla, y jugadores involucrados.
     *         - En caso de tipo incorrecto, se devuelve un mensaje de error.
     */
    public String infoCasilla() {
        System.out.println("Descripción de la casilla: " + this.getNombre() + ". Posición " + this.getPosicion() + ".");
        switch (this.tipo) {
            case "Solar":
                return "Tipo: " + this.tipo.toLowerCase() + ",\n" +
                        "grupo: " + this.grupo.getNombreGrupo() + ",\n" +
                        "propietario: " + this.duenho.getNombre() + ",\n" +
                        "valor: " + this.valor + ",\n" +
                        "alquiler: " + this.impuesto + ",\n" +
                        "Hipoteca: " + this.hipoteca + ",\n" +
                        "valor hotel: " + this.valor * 0.6 + ",\n" +
                        "valor casa: " + this.valor * 0.6 + ",\n" +
                        "valor piscina: " + this.valor * 0.4 + ",\n" +
                        "pista de deporte: " + this.valor * 1.25 + ",\n" +
                        "alquiler una casa: " + this.impuesto * 5 + ",\n" +
                        "alquiler dos casas: " + this.impuesto * 15 + ",\n" +
                        "alquiler tres casas: " + this.impuesto * 35 + ",\n" +
                        "alquiler cuatro casas: " + this.impuesto * 50 + ",\n" +
                        "alquiler hotel: " + this.impuesto * 70 + ",\n" +
                        "alquiler piscina: " + this.impuesto * 25 + ",\n" +
                        "alquiler pista de deporte: " + this.impuesto * 25 + ",\n" +
                        "Edificios construídos: " + this.listaArray(edificios);

            case "Servicios":
                return "Tipo: " + this.tipo.toLowerCase() + ",\n" +
                        "dueño: " + duenho.getNombre() + ",\n" +
                        "valor: " + this.valor + ",\n" +
                        "hipoteca: " + this.hipoteca;

            case "Transporte":
                return "Tipo: " + this.tipo.toLowerCase() + ",\n" +
                        "dueño: " + duenho.getNombre() + ",\n" +
                        "valor: " + this.valor + ",\n" +
                        "hipoteca: " + this.impuesto;

            case "Impuestos":
                return "Tipo: " + this.tipo.toLowerCase() + ",\n" +
                        "impuesto: " + this.impuesto;

            case "Especiales":
                if (Objects.equals(this.nombre, "Cárcel")) {
                    StringBuilder carcel = new StringBuilder();
                    carcel.append("Tipo: ").append(this.tipo.toLowerCase()).append(",\n");
                    carcel.append("salir: ").append(this.impuesto).append(",\n");
                    carcel.append("jugadores: ");
                    for(Avatar avatar : this.getAvatares()) {
                        if(avatar.getJugador().getEnCarcel()) {
                            carcel.append("[").append(avatar.getJugador().getNombre())
                                    .append(", ").append(avatar.getJugador().getTiradasCarcel()).append("] ");
                        }
                        else {
                            carcel.append("[").append(avatar.getJugador().getNombre()).append("] ");
                        }
                    }
                    return carcel.toString();
                } else if (Objects.equals(this.nombre, "Parking")) {
                    StringBuilder parking = new StringBuilder();
                    parking.append("Tipo: ").append(this.tipo.toLowerCase()).append(",\n");
                    parking.append("bote: ").append(this.valor).append(",\n");
                    parking.append("jugadores: [");
                    for(Avatar avatar : this.getAvatares()) {
                        parking.append(avatar.getJugador().getNombre()).append(", ");
                    }

                    if(!this.getAvatares().isEmpty()) {
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


    public int getNumCasas(ArrayList<Edificio> edificios){
        edificios = this.edificios;
        int numCasas = 0;
        for(Edificio edificio : edificios){
            if(edificio.getTipo().contains("casa")){
                numCasas ++;
            }
        }
        return numCasas;
    }
    public void edificarCasa(Jugador jugador, ArrayList<Jugador> jugadores) {
        if(!this.getDuenho().equals(jugador)){
            System.out.println("El jugador no puede edificar, ya que no es el propietario de la casilla.");
        }
        if(this.getNumCasas(this.getEdificios()) < this.getGrupo().getNumCasillas()){
            System.out.println("El número de casas que se pueden edificar en la casilla ya es el máximo.");
        }
        if(!(this.getContador() > 2 || this.getGrupo().esDuenhoGrupo(this.getDuenho()))){
            System.out.println("El jugador no ha caído en la casilla más de dos veces o no posee el grupo de casillas a la que pertenece dicha casilla.");
        }
        //this.getDuenho().getEdificios().add(this.generarIdEdificacion(jugadores, "casa"));
    }

    public void edificarHotel(Jugador jugador, ArrayList<Jugador> jugadores) {
        if(this.getDuenho().equals(jugador) && this.getDuenho().getEdificios().size() >= 4){
            //Quitar las 4 casas y poner un hotel
        }
    }

    public void edificarPiscina(Jugador jugador, ArrayList<Jugador> jugadores){

    }

    private String generarIdEdificacion(ArrayList<Jugador> jugadores, String nombre) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(999);
        StringBuilder id = new StringBuilder();
        id.append(nombre).append(numeroAleatorio);

        //Recorre la lista de avatares para comprobar si el id ya existe. Si existe, se llama recursivamente a la función para que cree uno nuevo
        for (Jugador jugador : jugadores){
            for (Edificio edificio : jugador.getEdificios()) {
                if (id.toString().equals(edificio.getIdEdificio())) {
                    generarIdEdificacion(jugadores, nombre);
                    return generarIdEdificacion(jugadores, nombre);
                }
            }
        }
        return id.toString();

    }

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
    private String listaArray(ArrayList<?> array) {
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
                Propiedad: %s,
                Casas: %s,
                Hoteles: %s,
                Piscinas: %s,
                Pistas de deporte: %s,
                Alquiler: %.2f
                """.formatted(this.getNombre(), Casas.isEmpty() ? '-' : Casas, Hoteles.isEmpty() ? '-' : Hoteles, Piscinas.isEmpty() ? '-' : Piscinas,
                Deporte.isEmpty() ? '-' : Deporte, this.getImpuesto());
    }

    public void venderEdificios(String tipo, int cantidad) {
        Jugador propietario = this.getDuenho();
        for(Edificio edificio : edificios) {
            if(edificio.getTipo().equals(tipo) && cantidad > 0) {
                edificios.remove(edificio);
                ArrayList<Edificio> aux = propietario.getEdificios();
                aux.remove(edificio);
                propietario.setEdificios(aux);
                propietario.sumarFortuna(edificio.getValor() / 2);
                edificio.setCasilla(null);
            }
        }
    }
}

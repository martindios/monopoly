package partida;

import java.util.ArrayList;
import java.util.Scanner;

import monopoly.*;
import static monopoly.Valor.FORTUNA_BANCA;
import static monopoly.Valor.FORTUNA_INICIAL;

public class Jugador {

    /**********Atributos**********/
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private int numTransportes; //Cuenta la cantidad de transportes que tiene el jugador
    private int numServicios; //Cuenta la cantidad de servicios que tiene el jugador
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.
    private ArrayList<String> hipotecas;
    private ArrayList<Edificio> edificios; //Propiedades que posee el jugador.

    /*Atributos designados para las estadísticas*/
    private float dineroInvertido;
    private float pagoTasasEImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasarPorCasillaDeSalida;
    private float premiosInversionesOBote;
    private int vecesEnLaCarcel;
    private int vecesTiradasDados;


    /**********Constructores**********/

    /*Constructor vacío, se usará para crear la banca*/
    public Jugador() {
        this.nombre = "banca";
        this.fortuna = FORTUNA_BANCA;
    }

    /*Constructor principal
     * Parámetros:
     * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
     * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
     * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        //Como temos que crear aquí o avatar, usamos o constructor del con args: Tipo do avatar, Xogador que o ten, Casilla de inicio e o array de avatares para nn repetilo
        this.fortuna = FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.numTransportes = 0;
        this.numServicios = 0;

        this.dineroInvertido = 0;
        this.pagoTasasEImpuestos = 0;
        this.pagoDeAlquileres = 0;
        this.cobroDeAlquileres = 0;
        this.pasarPorCasillaDeSalida = 0;
        this.premiosInversionesOBote = 0;
        this.vecesEnLaCarcel = 0;
        this.vecesTiradasDados = 0;

        this.propiedades = new ArrayList<>();
        this.hipotecas = new ArrayList<>();
        this.edificios = new ArrayList<>();
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        // Añadimos o avatar á lista de avatares creados
        avCreados.add(this.avatar);
    }

    /**********Getters**********/

    public String getNombre() {
        return nombre;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public float getFortuna() {
        return fortuna;
    }

    public float getGastos() {
        return gastos;
    }

    public int getNumTransportes() {
        return numTransportes;
    }

    public int getNumServicios() {
        return numServicios;
    }

    public boolean getEnCarcel() {
        return enCarcel;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public int getVueltas() {
        return vueltas;
    }

    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public float getDineroInvertido() {
        return dineroInvertido;
    }

    public float getPagoTasasEImpuestos() {
        return pagoTasasEImpuestos;
    }

    public float getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }

    public float getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }

    public float getPasarPorCasillaDeSalida() {
        return pasarPorCasillaDeSalida;
    }

    public float getPremiosInversionesOBote() {
        return premiosInversionesOBote;
    }

    public int getVecesEnLaCarcel() {
        return vecesEnLaCarcel;
    }

    public int getVecesTiradasDados() {
        return vecesTiradasDados;
    }

    /**********Setters**********/

    public void setFortuna(float fortuna) {
        if (fortuna < 0) {
            this.fortuna = 0;
        } else {
            this.fortuna = fortuna;
        }
    }

    public void setGastos(float gastos) {
        if (gastos < 0) {
            this.gastos = 0;
        } else {
            this.gastos = gastos;
        }
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel) {
        this.tiradasCarcel = tiradasCarcel;
    }

    public void setNumTransportes(int numTransportes) {
        this.numTransportes = numTransportes;
    }

    public void setNumServicios(int numServicios) {
        this.numServicios = numServicios;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }


    /**********Métodos**********/

    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    public void sumarTasasEImpuestos(float valor) {
        this.pagoTasasEImpuestos += valor;
    }

    public void sumarDineroInvertido(float valor) {
        this.dineroInvertido += valor;
    }

    public void sumarPagoDeAlquileres(float valor) {
        this.pagoDeAlquileres += valor;
    }

    public void sumarCobroDeAlquileres(float valor) {
        this.cobroDeAlquileres += valor;
    }

    public void sumarPasarPorCasillaDeSalida(float valor) {
        this.pasarPorCasillaDeSalida += valor;
    }

    public void sumarPremiosInversionesOBote(float valor) {
        this.premiosInversionesOBote += valor;
    }

    public void sumarVecesEnLaCarcel(int valor) {
        this.vecesEnLaCarcel += valor;
    }

    public void sumarVecesTiradasDados() {
        this.vecesTiradasDados += 1;
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /**
     * Método para establecer al jugador en la cárcel.
     * Este método utiliza las casillas del tablero proporcionadas como parámetro
     * para mover el avatar del jugador a la casilla de la cárcel.
     *
     * @param pos El conjunto de casillas que representa el tablero,
     *            necesaria para localizar la casilla de la cárcel.
     */
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        sumarVecesEnLaCarcel(getVecesEnLaCarcel() + 1);
        this.enCarcel = true;
        this.tiradasCarcel = 0;
        Avatar av = this.avatar;
        Casilla casillaOld = av.getLugar();
        casillaOld.eliminarAvatar(av);
        for (ArrayList<Casilla> casillas : pos) {
            for (Casilla casilla : casillas) {
                if (casilla.getNombre().equalsIgnoreCase("Cárcel")) {
                    casilla.anhadirAvatar(av);
                    System.out.println("El jugador ha sido encarcelado.");
                }
            }
        }
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
     * separados por comas y encerrados entre corchetes. Si la lista está
     * vacía, se devuelve un guion ("-").
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

    /**
     * Método que devuelve una representación en cadena de la información del jugador.
     * Incluye el nombre, el avatar, la fortuna y las listas de propiedades, hipotecas
     * y edificios del jugador
     *
     * @return Una cadena que representa la información del jugador, incluyendo:
     * - Nombre del jugador
     * - ID del avatar
     * - Fortuna del jugador
     * - Lista de propiedades del jugador
     * - Lista de hipotecas del jugador
     * - Lista de edificios del jugador
     */
    public String info() {
        String listaPropiedades = listaArray(propiedades);
        String listaHipotecas = listaArray(hipotecas);
        String listaEdificios = listaArray(edificios);

        return """
                {
                    Nombre: %s,
                    Avatar: %s,
                    Fortuna: %.2f,
                    Propiedades: %s
                    Hipotecas: %s
                    Edificios: %s
                }""".formatted(nombre, getAvatar().getId(), fortuna, listaPropiedades, listaHipotecas, listaEdificios);
    }

    public float calcularFortunaTotal() {
        float fortunaTotal = this.fortuna;
        for (Casilla casilla : propiedades) {
            fortunaTotal += casilla.getValor();
        }
        for (Edificio edificio : edificios) {
            fortunaTotal += edificio.getValor();
        }
        return fortunaTotal;
    }

    /*Método que se llama cuando el jugador tiene que conseguir dinero vendiendo edificios, hipotecando propiedades y sino
     *    debe declararse en bancarrota*/
    public void conseguirDinero(float dineroAConseguir) {
        Scanner scanner = new Scanner(System.in);
        if (this.getFortuna() < dineroAConseguir) {
            System.out.println("El jugador no tiene suficiente dinero para pagar. Debe vender edificios y/o hipotecar propiedades.");
            //Si el jugador no tiene suficiente dinero, se le pide que venda edificios y/o hipoteque propiedades
            //Si no puede hacer ninguna de las dos, se declara en bancarrota
            if (this.getEdificios().isEmpty() && this.getPropiedades().isEmpty()) {
                System.out.println("El jugador no tiene propiedades ni edificios para vender. Se declara en bancarrota.");
                //HAY QUE DECLARARSE EN BANCARROTA
            } else {
                System.out.println("El jugador tiene propiedades y/o edificios. ¿Qué desea vender/hipotecar? (propiedades[1]/edificios[2]) ");
                int opcion;
                do {
                    opcion = scanner.nextInt();
                    if (opcion != 1 && opcion != 2) {
                        System.out.println("Opción no válida. Introduzca 1 para propiedades o 2 para edificios.");
                    }
                } while (opcion != 1 && opcion != 2);
                if (opcion == 1) { //Hipotecar propiedades
                    //Si el jugador tiene propiedades, se le pide que las hipoteque
                    System.out.println("Propiedades:");
                    for (Casilla propiedad : this.getPropiedades()) {
                        System.out.println(propiedad.getNombre());
                    }
                    //Se le pide que introduzca el nombre de la propiedad que quiere hipotecar
                    System.out.println("Introduce el nombre de la propiedad que quieres hipotecar:");
                    String nombrePropiedad = scanner.next();
                    //Se busca la propiedad con el nombre introducido
                    Casilla propiedadHipotecar = null;
                    for (Casilla propiedad : this.getPropiedades()) {
                        if (propiedad.getNombre().equalsIgnoreCase(nombrePropiedad)) {
                            propiedadHipotecar = propiedad;
                        }
                    }
                    //Si la propiedad no existe, se le pide que introduzca un nombre válido
                    while (propiedadHipotecar == null) {
                        System.out.println("El nombre introducido no es válido. Introduce un nombre válido:");
                        nombrePropiedad = scanner.next();
                        for (Casilla propiedad : this.getPropiedades()) {
                            if (propiedad.getNombre().equalsIgnoreCase(nombrePropiedad)) {
                                propiedadHipotecar = propiedad;
                            }
                        }
                    }


                    //propiedadHipotecar.hipotecar();

                    //Se añade el valor de la propiedad a la fortuna del jugador y se elimina de la lista de propiedades
                    this.sumarFortuna(propiedadHipotecar.getValor());
                    this.eliminarPropiedad(propiedadHipotecar);
                    this.hipotecas.add(nombrePropiedad);


                }

            }
            /*
                //Si el jugador tiene edificios, se le pide que los venda
                if (!this.getEdificios().isEmpty()) {
                    System.out.println("El jugador tiene edificios. Debe vender edificios.");
                    //Se le pide que introduzca el id del edificio que quiere vender
                    System.out.println("Introduce el id del edificio que quieres vender:");
                    for (Edificio edificio : this.getEdificios()) {
                        System.out.println(edificio.getIdEdificio());
                    }
                    //Se le pide que introduzca el id del edificio que quiere vender
                    System.out.println("Introduce el id del edificio que quieres vender:");
                    Scanner scanner = new Scanner(System.in);
                    int idEdificio = scanner.nextInt();
                    //Se busca el edificio con el id introducido
                    Edificio edificioVender = null;
                    for (Edificio edificio : this.getEdificios()) {
                        if (edificio.getIdEdificio() == idEdificio) {
                            edificioVender = edificio;
                        }
                    }
                    //Si el edificio no existe, se le pide que introduzca un id válido
                    while (edificioVender == null) {
                        System.out.println("El id introducido no es válido. Introduce un id válido:");
                        idEdificio = scanner.nextInt();
                        for (Edificio edificio : this.getEdificios()) {
                            if (edificio.getIdEdificio() == idEdificio) {
                                edificioVender = edificio;
                            }
                        }
                    }
                    //Se añade el valor del edificio a la fortuna del jugador y se elimina de la lista de edificios
                }
*/
        } else {
            return;
        }
    }




}

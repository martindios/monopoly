package partida;

import java.util.ArrayList;

import monopoly.Juego;
import monopoly.Trato;
import monopoly.ConsolaNormal;
import monopoly.edificio.*;
import monopoly.casilla.Casilla;
import monopoly.casilla.propiedad.Propiedad;
import monopoly.casilla.propiedad.Solar;
import monopoly.excepcion.excepcionCarcel.ExcepcionCarcel;
import partida.avatar.*;

import static monopoly.Valor.FORTUNA_BANCA;
import static monopoly.Valor.FORTUNA_INICIAL;

public class Jugador{

    /**********Atributos**********/
    private final String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el número de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private int numTransportes; //Cuenta la cantidad de transportes que tiene el jugador
    private int numServicios; //Cuenta la cantidad de servicios que tiene el jugador
    private ArrayList<Propiedad> propiedades; //Propiedades que posee el jugador.
    private ArrayList<String> hipotecas;
    private ArrayList<Edificio> edificios; //Propiedades que posee el jugador.
    private ArrayList<Trato> tratos; //Tratos que ha recibido el jugador.

    /*Atributos designados para las estadísticas*/
    private float dineroInvertido;
    private float pagoTasasEImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasarPorCasillaDeSalida;
    private float premiosInversionesOBote;
    private int vecesEnLaCarcel;
    private int vecesTiradasDados;
    private int noPuedeTirarDados; //Variable para controlar en movimiento Coche si sale < 4 no puede tirar dados durante los 2 siguientes turnos
    private static final ConsolaNormal consolaNormal = new ConsolaNormal();

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

        this.noPuedeTirarDados = 0;

        this.propiedades = new ArrayList<>();
        this.hipotecas = new ArrayList<>();
        this.edificios = new ArrayList<>();
        /*Creamos aquí el avatar, por lo que usamos el constructor con sus respectivos argumentos*/
        switch (tipoAvatar) {
            case "Pelota":
                this.avatar = new Pelota(this, inicio, avCreados);
                break;
            case "Coche":
                this.avatar = new Coche(this, inicio, avCreados);
                break;
            case "Sombrero":
                this.avatar = new Sombrero(this, inicio, avCreados);
                break;
            case "Esfinge":
                this.avatar = new Esfinge(this, inicio, avCreados);
                break;
        }
        // Añadimos o avatar á lista de avatares creados
        avCreados.add(this.avatar);
        this.tratos = new ArrayList<>();
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

    public ArrayList<Propiedad> getPropiedades() {
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

    public int getNoPuedeTirarDados() {
        return noPuedeTirarDados;
    }

    public ArrayList<Trato> getTratos() {
        return tratos;
    }

    public ArrayList<String> getHipotecas() {
        return hipotecas;
    }

    /**********Setters**********/

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

    public void setNoPuedeTirarDados(int noPuedeTirarDados) {
        this.noPuedeTirarDados = noPuedeTirarDados;
    }


    /**********Métodos**********/

    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Propiedad casilla) {
        propiedades.add(casilla);
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

    //Métodos para añadir o eliminar tratos
    public void addTrato(Trato trato) {
        this.tratos.add(trato);
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
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) throws ExcepcionCarcel {
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
                    throw new ExcepcionCarcel("El jugador ha sido encarcelado.");
                }
            }
        }
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
        String listaPropiedades = Juego.listaArray(propiedades);
        String listaHipotecas = Juego.listaArray(hipotecas);
        String listaEdificios = Juego.listaArray(edificios);

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

    public void bancarrota(String motivo, Jugador banca) {
        // Vender todos los edificios del jugador this
        for (Propiedad propiedad : propiedades) {
            if (propiedad instanceof Solar solar) {
                solar.venderEdificios(Casa.class, solar.getNumEdificios(solar.getEdificios(), Casa.class));
                solar.venderEdificios(Hotel.class, solar.getNumEdificios(solar.getEdificios(), Hotel.class));
                solar.venderEdificios(Piscina.class, solar.getNumEdificios(solar.getEdificios(), Piscina.class));
                solar.venderEdificios(PistaDeporte.class, solar.getNumEdificios(solar.getEdificios(), PistaDeporte.class));
            }
        }
        switch (motivo) {
            case "Alquiler" -> {
                consolaNormal.imprimir("El jugador " + nombre + " se declara en bancarrota por no poder pagar un alquiler de la casilla " + avatar.getLugar().getNombre() + ". Con propietario " +
                        avatar.getLugar().getDuenho().getNombre() + ".");
                consolaNormal.imprimir("Se transfieren la fortuna y las propiedades");
                Jugador jugBeneficiado = avatar.getLugar().getDuenho();

                // Transferir todas las propiedades al jugador beneficiado
                for (Propiedad propiedad : propiedades) {
                    propiedad.setHipotecado(false);
                    propiedad.setContador(0);
                    propiedad.setDuenho(jugBeneficiado);
                    jugBeneficiado.anhadirPropiedad(propiedad);
                }
                propiedades.clear();

                // Transferir la fortuna del jugador this al jugador beneficiado
                jugBeneficiado.sumarFortuna(this.fortuna);
                this.fortuna = 0;
            }
            case "Banca" -> {
                consolaNormal.imprimir("El jugador " + nombre + " se declara en bancarrota por no poder pagar a la banca.");
                consolaNormal.imprimir("Se transfieren la fortuna y las propiedades a la banca");

                // Transferir todas las propiedades a la banca
                for (Propiedad propiedad : propiedades) {
                    propiedad.setDuenho(banca);
                }
                propiedades.clear();

                // Transferir la fortuna del jugador this a la banca
                banca.sumarFortuna(this.fortuna);
                this.fortuna = 0;
            }
            case "Voluntario" -> {
                consolaNormal.imprimir("El jugador " + nombre + " se ha declarado en bancarrota voluntariamente.");
                consolaNormal.imprimir("Se transfieren la fortuna y las propiedades a la banca");

                // Transferir todas las propiedades a la banca
                for (Propiedad propiedad : propiedades) {
                    propiedad.setDuenho(banca);
                }
                propiedades.clear();

                // Transferir la fortuna del jugador this a la banca
                banca.sumarFortuna(this.fortuna);
                this.fortuna = 0;
            }
        }
    }
}
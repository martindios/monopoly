package monopoly.casilla.propiedad;

import monopoly.edificio.*;
import monopoly.Grupo;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Iterator;

import static monopoly.Juego.listaArray;


public class Solar extends Propiedad{

    /**********Atributos**********/
    private ArrayList<Edificio> edificios; //Edificios construidos en la casilla
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).

    /**********Constructor**********/
    public Solar(String nombre, int posicion, float valor, Jugador duenho){
        super(nombre, posicion, valor, duenho);
        this.setImpuesto(valor * 0.1f);
        this.setImpuestoInicial(this.getImpuesto());
        this.edificios = new ArrayList<Edificio>();
    }

    /**********Getters**********/

    //getter para devolver la lista de los edificios construídos en la casilla
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    /**********Setters**********/

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**********Métodos**********/

    /**
     * Obtiene el número de edificios de un tipo específico en la lista de edificios.
     *
     * @param edificios La lista de edificios a verificar.
     * @param tipoEdificio El tipo de edificio a contar.
     * @return El número de edificios del tipo especificado.
     */
    public int getNumEdificios(ArrayList<Edificio> edificios, Class<? extends Edificio> tipoEdificio) {
        int numEdificios = 0;
        for (Edificio edificio : edificios) {
            //Comproba se o objeto "edificio" (De tipo Edificio) é instancia de tipo "tipoEdificio"
            if (tipoEdificio.isInstance(edificio)) {
                numEdificios++;
            }
        }
        return numEdificios;
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        if (this.getDuenho().equals(banca) || jugador.equals(this.getDuenho())) {
            return true;
        } else {
            return (jugador.getFortuna() > this.getImpuesto());
        }
    }

    @Override
    public float calcularAlquiler(Jugador jugadorActual, Jugador banca, int tirada) {
        float alquiler = 0;
        Jugador duenhoSolar = this.getDuenho();
        if (this.getGrupo().esDuenhoGrupo(duenhoSolar)) { // Comprobar si el dueño del solar es dueño de todo el grupo de color
            alquiler = 2 * this.getImpuesto();
        } else {
            alquiler = this.getImpuesto();
        }
        return alquiler;
    }

    @Override
    public String infoCasilla() {
        float valor = this.getValor();
        float impuestoInicial = this.getImpuestoInicial();
        return "Tipo: Solar,\n" +
                "grupo: " + this.getGrupo().getNombreGrupo() + ",\n" +
                "propietario: " + this.getDuenho().getNombre() + ",\n" +
                "valor: " + valor + ",\n" +
                "alquiler: " + this.getImpuesto() + ",\n" +
                "Hipoteca: " + this.getHipoteca() + ",\n" +
                "valor hotel: " + valor * 0.6 + ",\n" +
                "valor casa: " + valor * 0.6 + ",\n" +
                "valor piscina: " + valor * 0.4 + ",\n" +
                "pista de deporte: " + valor * 1.25 + ",\n" +
                "alquiler una casa: " + impuestoInicial * 5 + ",\n" +
                "alquiler dos casas: " + impuestoInicial * 15 + ",\n" +
                "alquiler tres casas: " + impuestoInicial * 35 + ",\n" +
                "alquiler cuatro casas: " + impuestoInicial * 50 + ",\n" +
                "alquiler hotel: " + impuestoInicial * 70 + ",\n" +
                "alquiler piscina: " + impuestoInicial * 25 + ",\n" +
                "alquiler pista de deporte: " + impuestoInicial * 25 + ",\n" +
                "Edificios construídos: " + listaArray(this.getEdificios());
    }

    @Override
    public String casillaEnVenta() {
        return """
                {
                    Nombre: %s,
                    Tipo: Solar,
                    Grupo: %s,
                    Valor: %.2f.
                }""".formatted(getNombre(), getGrupo().getNombreGrupo(), getValor());
    }

    /**************************/
    /*SECCIÓN DE EDIFICACIONES*/
    /**************************/

    public boolean edificarCasa(Jugador jugador, int contadorCasa) {
        if(!(jugador.getAvatar().getLugar() instanceof Solar)) {
            System.out.println("El jugador no está en una casilla edificable.");
            return false;
        }
        if(!this.getDuenho().equals(jugador)){
            System.out.println("El jugador no puede edificar, ya que no es el propietario de la casilla.");
            return false;
        }
        if(jugador.getFortuna() < this.getValor() *0.6) {
            System.out.println("El jugador no tiene dinero suficiente para edificar una casa.");
            return false;
        }
        if(!(this.getContador() > 2 || this.getGrupo().esDuenhoGrupo(this.getDuenho()))){
            System.out.println("El jugador no ha caído en la casilla más de dos veces o no posee el grupo de casillas a la que pertenece dicha casilla.");
            return false;
        }
        //Si el número de hoteles no llega al máximo, se pueden construír 4 casas por solar
        if(this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), Hotel.class) < this.getGrupo().getNumCasillas()) {
            System.out.println("No tienes el número máximo de hoteles construídos en el grupo, puedes construír hasta 4 casas por solar.");
            if(!(this.getNumEdificios(edificios, Casa.class) < 4)) {
                System.out.println("Has alcanzado el número máximo de casas en este solar (sin el máximo de hoteles).");
                return false;
            }
        }
        else {
            System.out.println("Tienes el número máximo de hoteles construídos en el grupo (" + this.getGrupo().getNumCasillas()
                    + "), solo puedes construír " + this.getGrupo().getNumCasillas() + " casas en el grupo.");
            if(!(this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), Casa.class) < this.getGrupo().getNumCasillas())) {
                System.out.println("Tienes el número máximo de casas construídas en el grupo (" + this.getGrupo().getNumCasillas() + "), con el máximo de hoteles.");
                return false;
            }
        }
        crearEdificio("Casa", jugador, contadorCasa);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarHotel(Jugador jugador, int contadorHotel) {
        if(!(jugador.getAvatar().getLugar() instanceof Solar)) {
            System.out.println("El jugador no está en una casilla edificable.");
            return false;
        }
        if (!this.getDuenho().equals(jugador)) {
            System.out.println("El jugador no puede edificar, ya que no es el propietario de la casilla.");
            return false;
        }
        if (jugador.getFortuna() < this.getValor() * 0.6) {
            System.out.println("El jugador no tiene dinero suficiente para edificar un hotel.");
            return false;
        }
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), Hotel.class) == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de hoteles construídos en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if((this.getNumEdificios(edificios, Casa.class)) != 4){
            System.out.println("No tienes el mínimo de casas en la casilla para poder edificar un hotel (4 casas).");
            return false;
        }
        quitarCasas(4, jugador);
        crearEdificio("Hotel", jugador, contadorHotel);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarPiscina(Jugador jugador, int contadorPiscina){
        if(!(jugador.getAvatar().getLugar() instanceof Solar)) {
            System.out.println("El jugador no está en una casilla edificable.");
            return false;
        }
        if (!this.getDuenho().equals(jugador)) {
            System.out.println("El jugador no puede edificar, ya que no es el propietario de la casilla.");
            return false;
        }
        if (jugador.getFortuna() < this.getValor() * 0.4) {
            System.out.println("El jugador no tiene dinero suficiente para edificar una casa.");
            return false;
        }
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), Piscina.class) == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de piscinas construídas en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if(!((this.getNumEdificios(edificios, Hotel.class) >= 1) && (this.getNumEdificios(edificios, Casa.class) >= 2))){
            System.out.println("En el solar no se han construido al menos 1 hotel y 2 casas.");
            return false;
        }
        crearEdificio("Piscina", jugador, contadorPiscina);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarPistaDeporte(Jugador jugador, int contadorPistaDeporte){
        if(!(jugador.getAvatar().getLugar() instanceof Solar)) {
            System.out.println("El jugador no está en una casilla edificable.");
            return false;
        }
        if (!this.getDuenho().equals(jugador)) {
            System.out.println("El jugador no puede edificar, ya que no es el propietario de la casilla.");
            return false;
        }
        if (jugador.getFortuna() < this.getValor() * 1.25) {
            System.out.println("El jugador no tiene dinero suficiente para edificar una casa.");
            return false;
        }
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), PistaDeporte.class) == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de pistas de deporte construídas en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if(!(this.getNumEdificios(edificios, Hotel.class) >= 2)){
            System.out.println("En el solar no se han construido al menos 2 hoteles.");
            return false;
        }
        crearEdificio("PistaDeporte", jugador, contadorPistaDeporte);
        infoTrasEdificiar();
        return true;
    }

    public void crearEdificio(String tipoEdificio, Jugador jugador, int contador){
        Edificio edificio;
        switch(tipoEdificio) {
            case "Casa":
                Casa casa = new Casa(this, contador);
                edificio = (Edificio) casa;
                break;
            case "Hotel":
                Hotel hotel = new Hotel(this, contador);
                edificio = (Edificio) hotel;
                break;
            case "Piscina":
                Piscina piscina = new Piscina(this, contador);
                edificio = (Edificio) piscina;
                break;
            case "PistaDeporte":
                PistaDeporte pistaDeporte = new PistaDeporte(this, contador);
                edificio = (Edificio) pistaDeporte;
                break;
            default:
                System.out.println("Tipo de edificio no válido.");
                return;
        }
        edificios.add(edificio);
        jugador.getEdificios().add(edificio);
        this.getGrupo().getEdificiosGrupo().add(edificio);
        float precio = edificio.getValor();
        jugador.sumarDineroInvertido(precio);
        jugador.sumarFortuna(-precio);
        jugador.sumarGastos(precio);
        System.out.println("El jugador " + jugador.getNombre() + " ha edificado un/una " + tipoEdificio + " en " + this.getNombre() + " por un precio de " + precio + ".");
    }

    public void quitarCasas(int numCasas, Jugador jugador){
        ArrayList<Edificio> edificiosBorrar = new ArrayList<>();
        for(Edificio casa: this.getEdificios()){
            if(casa.getIdEdificio().contains("Casa") && numCasas != 0){
                edificiosBorrar.add(casa);
                numCasas --;
            }
        }
        int tamano = edificiosBorrar.size();
        for (int i = 0; i < tamano; i++) {
            edificios.remove(edificiosBorrar.get(i));
        }
        for (int i = 0; i < tamano; i++) {
            jugador.getEdificios().remove(edificiosBorrar.get(i));
        }
        for (int i = 0; i < tamano; i++) {
            this.getGrupo().getEdificiosGrupo().remove(edificiosBorrar.get(i));
        }

    }

    private String infoTrasEdificiar() {
        return """
                El %s tiene los siguientes edificios:
                - Casas: %d,
                - Hoteles: %d,
                - Piscinas: %d,
                - Deporte: %d.
                Máximo de cada edificio en el grupo: %d.
                """.formatted(this.getNombre(), getNumEdificios(edificios, Casa.class),
                getNumEdificios(edificios, Hotel.class), getNumEdificios(edificios, Piscina.class),
                getNumEdificios(edificios, PistaDeporte.class), this.getGrupo().getNumCasillas());
    }

    public void venderEdificios(Class<? extends Edificio> tipoEdificio, int cantidad) {
        Jugador propietario = this.getDuenho();
        Iterator<Edificio> iterator = edificios.iterator();
        while (iterator.hasNext() && cantidad > 0) {
            Edificio edificio = iterator.next();
            if (tipoEdificio.isInstance(edificio)) {
                iterator.remove();
                ArrayList<Edificio> aux = propietario.getEdificios();
                aux.remove(edificio);
                propietario.setEdificios(aux);
                propietario.sumarFortuna(edificio.getValor() / 2);
                edificio.setCasilla(null);
                String tipo = edificio.getClass().getSimpleName();
                System.out.println("Se ha vendido un/una " + tipo + " por " + edificio.getValor() / 2 + "€.");
                cantidad--;
            }
        }
        this.modificarAlquiler();
    }

    public void modificarAlquiler() {
        int numCasas = getNumEdificios(edificios, Casa.class);
        int numHoteles = getNumEdificios(edificios, Hotel.class);
        int numPiscinas = getNumEdificios(edificios, Piscina.class);
        int numPistaDeporte = getNumEdificios(edificios, PistaDeporte.class);
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
        this.setImpuesto(this.getImpuestoInicial() + (this.getImpuestoInicial() * factorAlquiler));
    }
}

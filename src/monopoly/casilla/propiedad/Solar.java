package monopoly.casilla.propiedad;

import monopoly.Edificio.Edificio;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Iterator;

public class Solar extends Propiedad{
    private ArrayList<Edificio> edificios; //Edificios construidos en la casilla

    public Solar(String nombre, int posicion, float valor, Jugador duenho){
        super(nombre, "Solar", posicion, valor, duenho);
        this.setImpuesto(valor * 0.1f);
        this.setImpuestoInicial(this.getImpuesto());
        this.edificios = new ArrayList<Edificio>();
    }

    //getter para devolver la lista de los edificios construídos en la casilla
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    /**
     * Obtiene el número de edificios de un tipo específico en la lista de edificios.
     *
     * @param edificios La lista de edificios a verificar.
     * @param tipoEdificio El tipo de edificio a contar.
     * @return El número de edificios del tipo especificado.
     */
    public int getNumEdificios(ArrayList<Edificio> edificios, String tipoEdificio) {
        int numEdificios = 0;
        for (Edificio edificio : edificios) {
            if (edificio.getTipo().contains(tipoEdificio)) {
                numEdificios++;
            }
        }
        return numEdificios;
    }

    public boolean evaluarSolar(Jugador jugador, Jugador banca) {
        if (this.getDuenho().equals(banca)) {
            return true;
        } else {
            return (jugador.getFortuna() > this.getImpuesto());
        }
    }

    /**
     * Calcula y devuelve el alquiler que debe pagar el jugador actual por el solar.
     * Si el dueño del solar posee todo el grupo de color, el alquiler se duplica.
     *
     * @param jugadorActual El jugador que debe pagar el alquiler.
     * @param banca El jugador que representa a la banca.
     * @return El alquiler que debe pagar el jugador actual.
     */
    public float pagarAlquilerSolar(Jugador jugadorActual, Jugador banca) {
        float alquiler = 0;
        Jugador duenhoSolar = this.getDuenho();
        if (this.getGrupo().esDuenhoGrupo(duenhoSolar)) { // Comprobar si el dueño del solar es dueño de todo el grupo de color
            alquiler = 2 * this.getImpuesto();
        } else {
            alquiler = this.getImpuesto();
        }
        return alquiler;
    }

    public String infoCasillaPropiedad() {
        float valor = this.getValor();
        float impuestoInicial = this.getImpuestoInicial();
        return "Tipo: " + this.getTipo().toLowerCase() + ",\n" +
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

    public boolean edificarCasa(Jugador jugador, int contadorCasa) {
        if(!jugador.getAvatar().getLugar().getTipo().equals("Solar")) {
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
        if(this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), "Hotel") < this.getGrupo().getNumCasillas()) {
            System.out.println("No tienes el número máximo de hoteles construídos en el grupo, puedes construír hasta 4 casas por solar.");
            if(!(this.getNumEdificios(edificios, "Casa") < 4)) {
                System.out.println("Has alcanzado el número máximo de casas en este solar (sin el máximo de hoteles).");
                return false;
            }
        }
        else {
            System.out.println("Tienes el número máximo de hoteles construídos en el grupo (" + this.getGrupo().getNumCasillas()
                    + "), solo puedes construír " + this.getGrupo().getNumCasillas() + " casas en el grupo.");
            if(!(this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), "Casa") < this.getGrupo().getNumCasillas())) {
                System.out.println("Tienes el número máximo de casas construídas en el grupo (" + this.getGrupo().getNumCasillas() + "), con el máximo de hoteles.");
                return false;
            }
        }
        crearEdificio("Casa", jugador, contadorCasa, 0.6f);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarHotel(Jugador jugador, int contadorHotel) {
        if(!jugador.getAvatar().getLugar().getTipo().equals("Solar")) {
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
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), "Hotel") == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de hoteles construídos en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if((this.getNumEdificios(edificios, "Casa")) != 4){
            System.out.println("No tienes el mínimo de casas en la casilla para poder edificar un hotel (4 casas).");
            return false;
        }
        quitarCasas(4, jugador);
        crearEdificio("Hotel", jugador, contadorHotel, 0.6f);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarPiscina(Jugador jugador, int contadorPiscina){
        if(!jugador.getAvatar().getLugar().getTipo().equals("Solar")) {
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
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), "Piscina") == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de piscinas construídas en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if(!((this.getNumEdificios(edificios, "Hotel") >= 1) && (this.getNumEdificios(edificios, "Casa") >= 2))){
            System.out.println("En el solar no se han construido al menos 1 hotel y 2 casas.");
            return false;
        }
        crearEdificio("Piscina", jugador, contadorPiscina, 0.4f);
        infoTrasEdificiar();
        return true;
    }

    public boolean edificarPistaDeporte(Jugador jugador, int contadorPistaDeporte){
        if(!jugador.getAvatar().getLugar().getTipo().equals("Solar")) {
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
        if((this.getGrupo().getNumEdificios(this.getGrupo().getEdificiosGrupo(), "PistaDeporte") == this.getGrupo().getNumCasillas())) {
            System.out.println("Tienes el número máximo de pistas de deporte construídas en el grupo (" + this.getGrupo().getNumCasillas() + ").");
            return false;
        }
        if(!(this.getNumEdificios(edificios, "Hotel") >= 2)){
            System.out.println("En el solar no se han construido al menos 2 hoteles.");
            return false;
        }
        crearEdificio("PistaDeporte", jugador, contadorPistaDeporte, 1.25f);
        infoTrasEdificiar();
        return true;
    }

    public void crearEdificio(String tipoEdificio, Jugador jugador, int contador, float v){
        Edificio edificio = new Edificio(tipoEdificio, this, contador);
        edificios.add(edificio);
        jugador.getEdificios().add(edificio);
        this.getGrupo().getEdificiosGrupo().add(edificio);
        float precio = this.getValor() * v;
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
                """.formatted(this.getNombre(), getNumEdificios(edificios, "Casa"),
                getNumEdificios(edificios, "Hotel"), getNumEdificios(edificios, "Piscina"),
                getNumEdificios(edificios, "Deporte"), this.getGrupo().getNumCasillas());
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

}
